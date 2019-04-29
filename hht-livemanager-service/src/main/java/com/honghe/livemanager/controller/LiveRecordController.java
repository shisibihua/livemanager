package com.honghe.livemanager.controller;

import com.honghe.livemanager.cloud.tencent.api.model.CTLiveRecord;
import com.honghe.livemanager.cloud.tencent.api.model.CTLiveRecordRule;
import com.honghe.livemanager.cloud.tencent.api.model.CTLiveRecordTemplate;
import com.honghe.livemanager.common.pojo.model.Result;
import com.honghe.livemanager.service.LiveRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 直播录制控制层
 * @author caoqian
 * @date 20190311
 */
@CrossOrigin
@RestController("liveRecordController")
@RequestMapping("liveRecord")
public class LiveRecordController {
    @Autowired
    private LiveRecordService liveRecordService;

    /**
     * 创建录制任务
     * @param liveRecord 录制任务信息
     * @return result
     */
    @RequestMapping(value = "createLiveRecord",method = RequestMethod.POST)
    public Result createLiveRecord(@RequestBody CTLiveRecord liveRecord){
        return liveRecordService.createLiveRecord(liveRecord);
    }

    /**
     * 终止录制任务
     * @param liveRecord 录制任务信息
     * @return
     */
    @RequestMapping(value = "stopLiveRecord",method = RequestMethod.POST)
    public Result stopLiveRecord(@RequestBody CTLiveRecord liveRecord){
        return liveRecordService.stopLiveRecord(liveRecord);
    }

    /**
     * 删除录制任务
     * @param liveRecord 录制任务信息
     * @return
     */
    @RequestMapping(value = "delLiveRecord",method = RequestMethod.POST)
    public Result delLiveRecord(@RequestBody CTLiveRecord liveRecord){
        return liveRecordService.delLiveRecord(liveRecord);
    }

    /**
     * 创建录制规则
     * @param liveRecordRule 录制规则信息
     * @return
     */
    @RequestMapping(value = "createLiveRecordRule",method = RequestMethod.POST)
    public Result createLiveRecordRule(@RequestBody CTLiveRecordRule liveRecordRule){
        return liveRecordService.createLiveRecordRule(liveRecordRule);
    }

    /**
     * 创建录制模板
     * @param liveRecordTemplate 录制模板信息
     * @return
     */
    @RequestMapping(value = "createLiveRecordTemplate",method = RequestMethod.POST)
    public Result createLiveRecordTemplate(@RequestBody CTLiveRecordTemplate liveRecordTemplate){
        return liveRecordService.createLiveRecordTemplate(liveRecordTemplate);
    }
}
