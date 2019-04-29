package com.honghe.livemanager.service;

import com.honghe.livemanager.cloud.tencent.api.model.CTLiveRecord;
import com.honghe.livemanager.cloud.tencent.api.model.CTLiveRecordRule;
import com.honghe.livemanager.cloud.tencent.api.model.CTLiveRecordTemplate;
import com.honghe.livemanager.common.pojo.model.Result;

/**
 * 直播录制业务处理层
 * @author caoqian
 * @date 20190311
 */
public interface LiveRecordService {
    /**
     * 创建录制任务
     * @param liveRecord 录制任务
     * @return Result
     */
    Result createLiveRecord(CTLiveRecord liveRecord);

    /**
     * 创建录制规则
     * @param liveRecordRule 录制规则
     * @return
     */
    Result createLiveRecordRule(CTLiveRecordRule liveRecordRule);

    /**
     * 创建录制模板
     * @param liveRecordTemplate 录制模板
     * @return
     */
    Result createLiveRecordTemplate(CTLiveRecordTemplate liveRecordTemplate);

    /**
     * 终止录制任务
     * @param liveRecord 录制任务
     * @return
     */
    Result stopLiveRecord(CTLiveRecord liveRecord);

    /**
     * 删除录制任务
     * @param liveRecord 录制任务
     * @return
     */
    Result delLiveRecord(CTLiveRecord liveRecord);
}
