package com.honghe.livemanager.timmer;

import com.alibaba.fastjson.JSONObject;
import com.honghe.livemanager.cloud.tencent.api.CloudTencentApi;
import com.honghe.livemanager.common.util.DateUtil;
import com.honghe.livemanager.common.util.ParamUtil;
import com.honghe.livemanager.common.util.TipsMessage;
import com.honghe.livemanager.dao.*;
import com.honghe.livemanager.entity.Live;
import com.honghe.livemanager.entity.LiveHistory;
import com.honghe.livemanager.entity.LiveLicense;
import com.honghe.livemanager.entity.LiveSysLog;
import com.honghe.livemanager.service.LiveLicenseService;
import com.honghe.livemanager.service.LiveOperationLogService;
import com.honghe.livemanager.service.LiveSysLogService;
import com.honghe.livemanager.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定时器
 *
 * @Author libing
 * @Date: 2018-09-26 10:02
 * @Mender:
 */
@Component
@Configuration
public class LiveScheduledTimmer {

    Logger logger = LoggerFactory.getLogger(LiveScheduledTimmer.class);
    @Value("${sys_log_max_count}")
    private String SYS_LOG_MAX_COUNT;
    @Value("${operation_log_max_count}")
    private String OPERATION_LOG_MAX_COUNT;
    @Autowired
    private LiveDao liveDao;

    @Autowired
    private LiveLicenseDao licenseDao;

    @Autowired
    private LiveHistoryDao liveHistoryDao;
    @Autowired
    private LiveSysLogDao liveSysLogDao;
    @Autowired
    private LiveOperationLogDao liveOperationLogDao;
    @Autowired
    private LiveSysLogService liveSysLogService;
    @Autowired
    private LiveOperationLogService liveOperationLogService;
    @Autowired
    private LiveLicenseService licenseService;
    @Autowired
    private CloudTencentApi cloudTencentApi;
    /**
     * 每日直播数量定时器
     *
     * @Author libing
     * @Date: 2018-09-26 10:02
     * @Mender:
     */
    @Scheduled(cron="0 0 2 * * ?")
    public void insertLiveHistory() {
        String lastDate = DateUtil.formatDate(DateUtil.addDay(new Date(), -1));
        String beginTime = lastDate + " 00:00:00";
        String endTime = lastDate + " 23:59:59";
        List<Live> allLiveList = liveDao.selectByDate(beginTime, endTime);
//        Long startTime = System.currentTimeMillis();
//        System.out.println("查询耗时---" + (System.currentTimeMillis() - startTime) + "ms");
        List<LiveHistory> insertList = new ArrayList<>();
        LiveHistory liveHistory;
        if (allLiveList != null && !allLiveList.isEmpty()) {
            liveHistory = new LiveHistory();
            Live live=allLiveList.get(0);
            if(0!=live.getLiveCount()){
                liveHistory.setCreateDate(new Date());
                liveHistory.setTimePoint(new Date());
                liveHistory.setViewersNumber(live.getNumber());
                liveHistory.setTrafficValue(live.getTrafficValue());
                liveHistory.setPicCount(live.getPicCount());
                liveHistory.setLiveCount(live.getLiveCount());
                insertList.add(liveHistory);
            }
        }
        if(!insertList.isEmpty()) {
            liveHistoryDao.insertBatch(insertList);
        }
        //处理过期授权
        List<LiveLicense> list =licenseDao.selectByEndDate();
        LiveSysLog sysLog ;
        List<LiveSysLog> liveSysLogList=new ArrayList<>();
        for(LiveLicense license:list ) {
            license.setStatus(0);
            //失败的存入数据库
            if(!licenseService.toggleStatus(license)){
                sysLog = new LiveSysLog();
                sysLog.setCreateTime(new Date());
                sysLog.setLevel("ERROR");
                sysLog.setDescription("授权到期，禁用授权失败，请手动禁用！学校名称为："+license.getName());
                sysLog.setSource("直播服务");
                liveSysLogList.add(sysLog);
            }
        }
        if(liveSysLogList!=null && !liveSysLogList.isEmpty()) {
            liveSysLogDao.addSysLogBatch(liveSysLogList);
        }
        deleteLog();
    }

    /**
     * 定时清空系统日志与操作日志
     * 定时每天2:00清空
     * @author caoqian
     */
    private void deleteLog() {
        try{
            int operationLogTotalCount=liveOperationLogDao.getOperationLogSum();
            if(!ParamUtil.isEmpty(OPERATION_LOG_MAX_COUNT) && operationLogTotalCount>=Integer.parseInt(OPERATION_LOG_MAX_COUNT)) {
                liveOperationLogService.deleteOperationLog(operationLogTotalCount-Integer.parseInt(OPERATION_LOG_MAX_COUNT));
            }
            int sysLogTotalCount=liveSysLogDao.getSysLogSum();
            if(!ParamUtil.isEmpty(SYS_LOG_MAX_COUNT) && sysLogTotalCount>=Integer.parseInt(SYS_LOG_MAX_COUNT)) {
                liveSysLogService.deleteSysLog(sysLogTotalCount-Integer.parseInt(SYS_LOG_MAX_COUNT));
            }
        }catch (Exception e){
            logger.error("定时删除日志信息异常",e);
        }
    }

    /**
     * 定时(每10分钟)修改直播状态
     * @author caoqian
     */
    @Scheduled(cron = "0 */10 * * * ?")
    public void updateLiveStatus() {
        List<String> overLiveIdList=new ArrayList<>();
        try {
            List<Live> overLiveList = liveDao.getOverLiveList();
            if (overLiveList != null && !overLiveList.isEmpty()) {
                for (Live live : overLiveList) {
                    String streamCode = live.getStreamCode();
                    JSONObject tencentCloudJson = cloudTencentApi.setLiveStatus(streamCode, Constants.LIVE_BAN_PUSH, null, null, null);
                    if (tencentCloudJson != null && !tencentCloudJson.isEmpty() &&
                            TipsMessage.SUCCESS_CODE == Integer.parseInt(String.valueOf(tencentCloudJson.get("code")))) {
                        overLiveIdList.add(String.valueOf(live.getLiveId()));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //禁用直播成功后，修改数据库状态
        if(!overLiveIdList.isEmpty()){
            String[] idsArr=overLiveIdList.toArray(new String[overLiveIdList.size()]);
            liveDao.updateLiveStatus(idsArr);
        }
    }


}
