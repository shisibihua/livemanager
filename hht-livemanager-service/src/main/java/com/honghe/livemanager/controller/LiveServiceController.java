package com.honghe.livemanager.controller;

import com.honghe.livemanager.cloud.tencent.api.model.CTLiveRecord;
import com.honghe.livemanager.cloud.tencent.api.model.CTVodMedia;
import com.honghe.livemanager.common.pojo.model.Page;
import com.honghe.livemanager.common.pojo.model.Result;
import com.honghe.livemanager.common.util.ParamUtil;
import com.honghe.livemanager.entity.Live;
import com.honghe.livemanager.entity.LiveHistory;
import com.honghe.livemanager.service.LiveRecordService;
import com.honghe.livemanager.service.LiveService;
import com.honghe.livemanager.service.LiveStatisticService;
import com.honghe.livemanager.service.LiveVodMediaService;
import com.honghe.livemanager.util.ConvertResult;
import com.honghe.livemanager.util.MyLog;
import com.honghe.livemanager.util.RandomCode;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 用于提供外部接口
 */
@CrossOrigin
@RestController("liveServiceController")
@RequestMapping("liveService")
public class LiveServiceController {
    @Autowired
    private LiveService liveService;
    @Autowired
    private LiveStatisticService liveStatisticService;
    @Autowired
    private LiveRecordService liveRecordService;
    @Autowired
    private LiveVodMediaService vodMediaService;

    /**
     * 添加直播,返回推流地址与接流地址
     * @author caoqian
     * @return
     */
    @MyLog("直播管理-添加直播")
    @RequestMapping(value = "addLive",method = RequestMethod.POST)
    public Result addLive(@RequestBody Live live){
        if(live==null){
            return ConvertResult.getParamErrorResult();
        }
        Result result=liveService.checkLiveServiceIsValid(live);
        if(result.getCode()==Result.Code.Success.value()) {
            return liveService.getAddLiveResult(live);
        }else{
            return result;
        }
    }

    /**
     * 添加直播,返回推流地址与接流地址
     * @author caoqian
     * @return
     */
    @MyLog("直播管理-录播添加直播")
    @RequestMapping(value = "addLiveAR",method = RequestMethod.POST)
    public Result addLiveAR(@RequestBody Live live){
        if(live==null){
            return ConvertResult.getParamErrorResult();
        }
        Result result=liveService.checkLiveServiceIsValid(live);
        if(result.getCode()==Result.Code.Success.value()) {
            live.setStreamCodeDevice(RandomCode.code());
            return liveService.getAddLiveResult(live);
        }else{
            return result;
        }
    }

    /**
     * 添加直播,返回推流地址与接流地址
     * @author caoqian
     * @return
     */
    @MyLog("直播管理-录播获取直播信息")
    @RequestMapping(value = "getLiveInfoByCode",method = RequestMethod.GET)
    public Result getLiveInfoByCode(String streamCodeDevice){
        if(streamCodeDevice==null){
            return ConvertResult.getParamErrorResult();
        }
        try {
            Map map = liveService.getLiveInfoByCode(streamCodeDevice);

            if (null == map) {
                Result result = new Result(Result.Code.NoSuchMethod.value());
                result.setMsg("查不到直播信息");
                return result;
            } else {
                return ConvertResult.getSuccessResult(map);
            }
        }catch (Exception e){
            return ConvertResult.getErrorResult();
        }
    }

    /**
     * 根据直播id删除直播信息（逻辑删除）
     * @param live  直播
     * @return
     */
    @MyLog("直播管理-删除直播")
    @RequestMapping(value = "deleteLiveByLiveId",method = RequestMethod.POST)
    public Result deleteLiveByLiveId(@RequestBody Live live){
        if(live==null){
            return ConvertResult.getParamErrorResult();
        }
        Result result=liveService.checkLiveServiceIsValid(live);
        if(result.getCode()==Result.Code.Success.value()) {
            return liveService.deleteLiveByLiveId(live);
        }else{
            return result;
        }
    }
    /**
     * 获取瞬时直播信息
     * 返回在线人数
     * @param live  直播
     * @return
     */
    @MyLog("直播管理-获取瞬时直播信息")
    @RequestMapping(value = "getLiveDescribe",method = RequestMethod.POST)
    public Result getLiveDescribe(@RequestBody Live live){
        if(live==null){
            return ConvertResult.getParamErrorResult();
        }
        Result result=liveService.checkLiveServiceIsValid(live);
        if(result.getCode()==Result.Code.Success.value()) {
            return liveService.getLiveDescribe(live);
        }else{
            return result;
        }
    }
    /**
     * 获取直播历史播放信息，七天内数据有效
     * 返回在线人数
     * @param live  直播
     * @return
     */
    @MyLog("直播管理-获取直播历史播放信息")
    @RequestMapping(value = "getLiveDescribeHistory",method = RequestMethod.POST)
    public Result getLiveDescribeHistory(@RequestBody Live live){
        if(live==null){
            return ConvertResult.getParamErrorResult();
        }
        Result result=liveService.checkLiveServiceIsValid(live);
        if(result.getCode()==Result.Code.Success.value()) {
            return liveService.getLiveDescribeHistory(live);
        }else{
            return result;
        }
    }

    /**
     * 获取直播历史播放信息，七天内数据有效
     * 返回在线人数
     * @param live  直播
     * @return
     */
    @MyLog("直播管理-获取直播历史播放信息")
    @RequestMapping(value = "getLivePlayHistory",method = RequestMethod.POST)
    public Result getLivePlayHistory(@RequestBody Live live){
        if(ParamUtil.isOneEmpty(live,live.getStreamCode(),live.getBeginTime(),live.getEndTime(),live.getSchoolName())){
            return ConvertResult.getParamErrorResult();
        }
        Result result=liveService.checkLiveServiceIsValid(live);
        if(result.getCode()==Result.Code.Success.value()) {
            return liveService.getLivePlayHistory(live);
        }else{
            return result;
        }
    }
    /**
     * 根据直播码查询直播在线状态  0：断流；1：开启；3：关闭
     * @param live  直播
     * @return
     */
    @MyLog("直播管理-查询直播在线状态")
    @RequestMapping(value = "getLiveStatus",method = RequestMethod.POST)
    public Result getLiveStatus(@RequestBody Live live){
        if(live==null||null==live.getStreamCode()){
            return ConvertResult.getParamErrorResult();
        }
        Result result=liveService.checkLiveServiceIsValid(live);
        if(result.getCode()==Result.Code.Success.value()) {
            return liveService.getLiveStatus(live.getStreamCode());
        }else{
            return result;
        }
    }

    /**
     * 验证学校是否有预约权限
     * @param schoolName   学校名称
     * step1、验证授权表是否有该学校,没有则直接返回提示信息
     * step2、验证该学校授权是否过期，过期则直接返回提示信息
     * @author caoqian
     * @date 2018/11/15
     * @return
     */
    @MyLog("直播授权-验证学校是否有预约权限")
    @RequestMapping(value = "checkSchoolIsValid",method = RequestMethod.GET)
    public Result checkSchoolIsValid(String schoolName){
        if(ParamUtil.isEmpty(schoolName)){
            return ConvertResult.getParamErrorResult();
        }
        Live live=new Live();
        live.setSchoolName(schoolName);
        Result result=liveService.checkLiveServiceIsValid(live);
        if(result!=null && result.getCode()==Result.Code.Success.value()) {
            result.setResult(true);
        }else{
            result.setResult(false);
        }
        return result;
    }

    /**
     * 添加直播,返回推流地址与接流地址(大屏)
     * @author caoqian
     * @return
     */
    @MyLog("直播管理-添加直播")
    @RequestMapping(value = "addLiveScreen",method = RequestMethod.POST)
    public Result addLiveScreen(@RequestBody Live live){
        if(live==null){
            return ConvertResult.getParamErrorResult();
        }
//        Result result=liveService.checkLiveServiceIsValid(live);
        return liveService.getAddLiveScreenResult(live);
    }

    /**
     * 根据直播id删除直播信息（逻辑删除）(大屏)
     * @param live  直播
     * @return
     */
    @MyLog("直播管理-删除直播")
    @RequestMapping(value = "delByStreamCode",method = RequestMethod.POST)
    public Result delByStreamCode(@RequestBody Live live){
        if(live==null){
            return ConvertResult.getParamErrorResult();
        }
//        Result result=liveService.checkLiveServiceIsValid(live);
        return liveService.delByStreamCode(live);
    }

    /**
     * 获取直播排行详情
     * @param liveHistory
     * @return
     * @author caoqian
     */
    @MyLog("直播管理-获取直播排行详情")
    @RequestMapping(value="getLiveStatisticDetails",method = RequestMethod.POST)
    public Result getLiveStatisticDetails(@RequestBody LiveHistory liveHistory){
        return liveStatisticService.getLiveStatisticDetails(liveHistory);
    }

    /**
     * 创建录制任务
     * @param liveRecord 录制任务信息
     * @return result
     */
    @MyLog("直播管理-创建录制任务")
    @RequestMapping(value = "createLiveRecord",method = RequestMethod.POST)
    public Result createLiveRecord(@RequestBody CTLiveRecord liveRecord){
        return liveRecordService.createLiveRecord(liveRecord);
    }

    /**
     * 终止录制任务
     * @param liveRecord 录制任务信息
     * @return
     */
    @MyLog("直播管理-终止录制任务")
    @RequestMapping(value = "stopLiveRecord",method = RequestMethod.POST)
    public Result stopLiveRecord(@RequestBody CTLiveRecord liveRecord){
        return liveRecordService.stopLiveRecord(liveRecord);
    }

    /**
     * 删除录制任务
     * @param liveRecord 录制任务信息
     * @return
     */
    @MyLog("直播管理-删除录制任务")
    @RequestMapping(value = "delLiveRecord",method = RequestMethod.POST)
    public Result delLiveRecord(@RequestBody CTLiveRecord liveRecord){
        return liveRecordService.delLiveRecord(liveRecord);
    }

    /**
     * 搜索媒体信息
     * @param vodMedia 点播信息
     * @return
     */
    @MyLog("直播管理-搜索媒体信息")
    @RequestMapping(value = "searchMedia",method = RequestMethod.POST)
    public Result searchMedia(@RequestBody CTVodMedia vodMedia){
        return vodMediaService.searchMedia(vodMedia);
    }


    /**
     * 根据学校名称分页获取直播信息，倒序排列
     * @param schoolName      学校名
     * @param title           直播名称
     * @param currentPage     当前页
     * @param pageSize        分页大小
     * @return
     * @author caoqian
     * @date 20190411
     */
    @RequestMapping(value = "getLiveListByPage",method = RequestMethod.GET)
    public Result getLiveListByPage(String schoolName,String title,int currentPage,int pageSize){
        return liveService.getLiveListPageBySName(schoolName,title,currentPage,pageSize);
    }

    /**
     * 根据直播码查询直播信息
     * @param streamCode  直播码
     * @return
     * @author caoqian
     * @date 20190411
     */
    @RequestMapping(value = "getLiveByCode",method = RequestMethod.GET)
    public Result getLiveByCode(String streamCode){
        if(StringUtil.isEmpty(streamCode)){
            return ConvertResult.getParamErrorResult();
        }
        return liveService.getLivePlayDataByCode(streamCode);
    }
}
