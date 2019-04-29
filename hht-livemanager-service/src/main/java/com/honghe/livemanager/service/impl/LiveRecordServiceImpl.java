package com.honghe.livemanager.service.impl;

import com.honghe.livemanager.cloud.tencent.api.CloudTencentApi2;
import com.honghe.livemanager.cloud.tencent.api.model.CTLiveRecord;
import com.honghe.livemanager.cloud.tencent.api.model.CTLiveRecordRule;
import com.honghe.livemanager.cloud.tencent.api.model.CTLiveRecordTemplate;
import com.honghe.livemanager.common.pojo.model.Result;
import com.honghe.livemanager.service.LiveRecordService;
import com.honghe.livemanager.util.ConvertResult;
import jodd.typeconverter.Convert;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LiveRecordServiceImpl implements LiveRecordService {
    private Logger logger= LoggerFactory.getLogger(LiveRecordServiceImpl.class);

    @Autowired
    private CloudTencentApi2 cloudTencentApi2;

    @Override
    public Result createLiveRecord(CTLiveRecord liveRecord) {
        if(StringUtil.isEmpty(liveRecord.getStreamId())){
            return ConvertResult.getParamErrorResult("流id不能为空");
        }
        try {
            return ConvertResult.getSuccessResult(cloudTencentApi2.createLiveRecord(liveRecord));
        }catch (Exception e){
            logger.error("创建录制任务异常",e);
            return ConvertResult.getErrorResult("创建录制任务失败");
        }
    }

    @Override
    public Result createLiveRecordRule(CTLiveRecordRule liveRecordRule) {
        if(liveRecordRule.getTemplateId()==0){
            return ConvertResult.getParamErrorResult("模板id不能为空");
        }
        try {
            return ConvertResult.getSuccessResult(cloudTencentApi2.createLiveRecordRule(liveRecordRule));
        }catch (Exception e){
            logger.error("创建录制规则异常",e);
            return ConvertResult.getErrorResult("创建录制规则失败");
        }
    }

    @Override
    public Result createLiveRecordTemplate(CTLiveRecordTemplate liveRecordTemplate) {
        if(StringUtil.isEmpty(liveRecordTemplate.getTemplateName())){
            return ConvertResult.getParamErrorResult("模板名称不能为空");
        }
        try {
            return ConvertResult.getSuccessResult(cloudTencentApi2.createLiveRecordTemplate(liveRecordTemplate));
        }catch (Exception e){
            logger.error("创建录制模板异常",e);
            return ConvertResult.getErrorResult("创建录制模板失败");
        }
    }

    @Override
    public Result stopLiveRecord(CTLiveRecord liveRecord) {
        if(StringUtil.isEmpty(liveRecord.getStreamId()) || liveRecord.getTaskId()==0){
            return ConvertResult.getParamErrorResult("流id或任务id不能为空");
        }
        try {
            return ConvertResult.getSuccessResult(cloudTencentApi2.stopLiveRecord(liveRecord));
        }catch (Exception e){
            logger.error("终止录制任务异常",e);
            return ConvertResult.getErrorResult("终止录制任务失败");
        }
    }

    @Override
    public Result delLiveRecord(CTLiveRecord liveRecord) {
        if(StringUtil.isEmpty(liveRecord.getStreamId()) || liveRecord.getTaskId()==0){
            return ConvertResult.getParamErrorResult("流id或任务id不能为空");
        }
        try {
            return ConvertResult.getSuccessResult(cloudTencentApi2.deleteLiveRecord(liveRecord));
        }catch (Exception e){
            logger.error("删除录制任务异常",e);
            return ConvertResult.getErrorResult("删除录制任务失败");
        }
    }
}
