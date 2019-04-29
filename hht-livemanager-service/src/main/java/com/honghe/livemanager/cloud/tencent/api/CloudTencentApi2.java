package com.honghe.livemanager.cloud.tencent.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.honghe.livemanager.cloud.tencent.api.model.CTLiveRecord;
import com.honghe.livemanager.cloud.tencent.api.model.CTLiveRecordRule;
import com.honghe.livemanager.cloud.tencent.api.model.CTLiveRecordTemplate;
import com.honghe.livemanager.cloud.tencent.api.model.CTVodMedia;
import com.honghe.livemanager.common.util.DateUtil;
import com.honghe.livemanager.util.TencetLiveUtil;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.live.v20180801.LiveClient;
import com.tencentcloudapi.live.v20180801.models.*;
import com.tencentcloudapi.vod.v20180717.VodClient;
import com.tencentcloudapi.vod.v20180717.models.SearchMediaRequest;
import com.tencentcloudapi.vod.v20180717.models.SearchMediaResponse;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * 腾讯云直播录制、点播api接口
 * @author caoqian
 * @date 20190307
 */
@Component("cloudTencentApi2")
public final class CloudTencentApi2 {
    private static Logger logger = LoggerFactory.getLogger(CloudTencentApi.class);
    private final static String VERSION_LIVE="2018-08-01";
    private final static String VERSION_VOD="2018-07-17";
    private final static String APPNAME="";
    //默认分片保存时长，单位秒
    private final int DEFAULT_RECORD_INTERVAL=1800;

    /**
     * api说明：
        1.使用前提
        录制文件存放于点播平台，所以用户如需使用录制功能，需首先自行开通点播服务。
        录制文件存放后相关费用（含存储以及下行播放流量）按照点播平台计费方式收取，具体请参考 对应文档。
        2.模式说明 该接口支持两种录制模式：
             i.定时录制模式【默认模式】。 需要传入开始时间与结束时间，录制任务根据时间自动开始与结束。
             ii.实时视频录制模式。 忽略传入的开始时间，在录制任务创建后立即开始录制，录制时长支持最大为30分钟，
             如果传入的结束时间与当前时间差大于30分钟，则按30分钟计算，实时视频录制主要用于录制精彩视频场景，时长建议控制在5分钟以内。
        3.注意事项
        调用接口超时设置应大于3秒，小于3秒重试以及频繁调用都有可能产生重复录制任务。
        默认接口请求频率限制：100次/秒。
     * 创建录制任务
     * @param liveRecord
     * @return
    */
    public int createLiveRecord(CTLiveRecord liveRecord){
        JSONObject paramsJson=new JSONObject();
        if(!StringUtil.isEmpty(liveRecord.getAction())){
            paramsJson.put("Action",liveRecord.getAction());
        }else {
            paramsJson.put("Action", GetCTIntefaceName.CREATE_LIVE_RECORD.value());
        }
        if(!StringUtil.isEmpty(liveRecord.getVersion())){
            paramsJson.put("Version",liveRecord.getVersion());
        }else {
            paramsJson.put("Version", VERSION_LIVE);
        }
        //流id
        paramsJson.put("StreamName",liveRecord.getStreamId());
        //非必需，推流app名
        if(!StringUtil.isEmpty(liveRecord.getAppName())) {
            paramsJson.put("AppName", liveRecord.getAppName());
        }else{
            paramsJson.put("AppName", APPNAME);
        }
        //非必需，推流域名，多域名推流必须设置。
        if(!StringUtil.isEmpty(liveRecord.getDomainName())) {
            paramsJson.put("DomainName",liveRecord.getDomainName());
        }else {
            paramsJson.put("DomainName", TencetLiveUtil.CLOUD_TENCENT_PUSHURL);
        }
        //非必需，录制开始时间，需要URLEncode编码
        if(!StringUtil.isEmpty(liveRecord.getStartTime())){
            try {
                paramsJson.put("StartTime", URLEncoder.encode(liveRecord.getStartTime(), "utf-8"));
            }catch (UnsupportedEncodingException e){
                ;
            }catch (Exception e){
                ;
            }
        }
        //非必需，录制结束时间，需要URLEncode编码
        if(!StringUtil.isEmpty(liveRecord.getEndTime())){
            try {
                paramsJson.put("EndTime", URLEncoder.encode(liveRecord.getEndTime(),"utf-8"));
            } catch (UnsupportedEncodingException e) {
                ;
            }catch (Exception e){
                ;
            }
        }
        /**
            非必需，录制类型。
            “video” : 音视频录制【默认】。
            “audio” : 纯音频录制。
            在定时录制模式或实时视频录制模式下，该参数均有效，不区分大小写。
         */
        if(!StringUtil.isEmpty(liveRecord.getRecordType())) {
            paramsJson.put("RecordType", liveRecord.getRecordType());
        }
        /**
             非必需，录制文件格式。其值为：
             “flv”,“hls”,”mp4”,“aac”,”mp3”，默认“flv”。
             在定时录制模式或实时视频录制模式下，该参数均有效，不区分大小写。
         */
        if(!StringUtil.isEmpty(liveRecord.getFileFormat())) {
            paramsJson.put("FileFormat", liveRecord.getFileFormat());
        }
        /**
         *  非必需，开启实时视频录制模式标志。0：不开启实时视频录制模式，即采用定时录制模式【默认】；1：开启实时视频录制模式。
         */
        paramsJson.put("Highlight",liveRecord.getHighLight());
        /**
            非必需，开启A+B=C混流C流录制标志。0：不开启A+B=C混流C流录制【默认】；1：开启A+B=C混流C流录制。
            在定时录制模式或实时视频录制模式下，该参数均有效。
         */
        paramsJson.put("MixStream",liveRecord.getMixStream());
        /**
         *   非必需，录制流参数。当前支持以下参数：
             record_interval - 录制分片时长，单位 秒，1800 - 7200
             storage_time - 录制文件存储时长，单位 秒，为0时永久保存
             eg. record_interval=3600&storage_time=2592000
             注：参数需要url encode。
             在定时录制模式或实时视频录制模式下，该参数均有效。
         */

        int recordInterval = liveRecord.getRecordInterval()==0?DEFAULT_RECORD_INTERVAL:liveRecord.getRecordInterval();
        int storageTime = liveRecord.getStorageTime();
        try {
            paramsJson.put("StreamParam", URLEncoder.encode("record_interval="+recordInterval+"&storage_time="+storageTime,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            ;
        }catch (Exception e){
            ;
        }
        JSONObject value=getLiveRecordClientJson(paramsJson.toString());
        int result=0;
        if(value!=null && !value.isEmpty()){
            result = value.get("TaskId")==null ? 0 : value.getIntValue("TaskId");
        }
        return result;
    }

    /**
     * api说明：
         创建录制规则，需要先调用CreateLiveRecordTemplate接口创建录制模板，将返回的模板id绑定到流使用。
         默认接口请求频率限制：200次/秒。
     * 创建录制规则
     * @param liveRecordRule
     * @return
     */
    public boolean createLiveRecordRule(CTLiveRecordRule liveRecordRule){
        JSONObject paramsJson=new JSONObject();
        if(!StringUtil.isEmpty(liveRecordRule.getAction())){
            paramsJson.put("Action",liveRecordRule.getAction());
        }else{
            paramsJson.put("Action",GetCTIntefaceName.CREATE_LIVE_RECORD_RULE.value());
        }
        if(!StringUtil.isEmpty(liveRecordRule.getVersion())){
            paramsJson.put("Version",liveRecordRule.getVersion());
        }else{
            paramsJson.put("Version",VERSION_LIVE);
        }
        if(!StringUtil.isEmpty(liveRecordRule.getDomainName())){
            paramsJson.put("DomainName",liveRecordRule.getDomainName());
        }else{
            paramsJson.put("DomainName",TencetLiveUtil.CLOUD_TENCENT_PUSHURL);
        }
        if(!StringUtil.isEmpty(liveRecordRule.getAppName())){
            paramsJson.put("AppName",liveRecordRule.getAppName());
        }else{
            paramsJson.put("AppName",APPNAME);
        }
        //流id
        if(!StringUtil.isEmpty(liveRecordRule.getStreamId())){
            paramsJson.put("StreamName",liveRecordRule.getStreamId());
        }else{
            paramsJson.put("StreamName","");
        }
        if(liveRecordRule.getTemplateId()!=0){
            paramsJson.put("TemplateId",liveRecordRule.getTemplateId());
        }else{
            logger.info("创建录制规则，模板id为0,创建失败。");
            return false;
        }
        JSONObject value=getLiveRecordRuleClientJson(paramsJson.toString());
        if(value!=null && !value.isEmpty()){
            String requestId=value.get("RequestId")==null ? "":value.getString("RequestId");
            if(!StringUtil.isEmpty(requestId)){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    /**
     * api说明：
          默认接口请求频率限制：200次/秒。
     * 创建录制模板
     * @param liveRecordTemplate
     * @return
     */
    public int createLiveRecordTemplate(CTLiveRecordTemplate liveRecordTemplate){
        JSONObject paramsJson=new JSONObject();
        if(!StringUtil.isEmpty(liveRecordTemplate.getAction())){
            paramsJson.put("Action",liveRecordTemplate.getAction());
        }else{
            paramsJson.put("Action",GetCTIntefaceName.CREATE_LIVE_RECORD_TEMPLATE.value());
        }
        if(!StringUtil.isEmpty(liveRecordTemplate.getVersion())){
            paramsJson.put("Version",liveRecordTemplate.getVersion());
        }else{
            paramsJson.put("Version",VERSION_LIVE);
        }
        if(!StringUtil.isEmpty(liveRecordTemplate.getTemplateName())){
            paramsJson.put("TemplateName",liveRecordTemplate.getTemplateName());
        }else{
            logger.info("创建录制模板，模板名为空，创建失败。");
            return 0;
        }
        if(!StringUtil.isEmpty(liveRecordTemplate.getDescription())){
            paramsJson.put("Description",liveRecordTemplate.getDescription());
        }
        //设置录制参数
        JSONObject typeJson=new JSONObject();
        if(liveRecordTemplate.getRecordInterval()!=0) {
            typeJson.put("RecordInterval",liveRecordTemplate.getRecordInterval());
        }else{
            typeJson.put("RecordInterval",1800);
        }
        if(liveRecordTemplate.getStorageTime()!=0) {
            typeJson.put("StorageTime", liveRecordTemplate.getStorageTime());
        }else{
            typeJson.put("StorageTime",0);
        }
        if(liveRecordTemplate.getEnable()!=0){
            typeJson.put("Enable",liveRecordTemplate.getEnable());
        }else{
            typeJson.put("Enable",0);
        }
        switch (liveRecordTemplate.getParamType()){
            case 1:
                paramsJson.put("FlvParam",typeJson);
                break;
            case 2:
                paramsJson.put("HlsParam",typeJson);
                break;
            case 3:
                paramsJson.put("Mp4Param",typeJson);
                break;
            case 4:
                paramsJson.put("AacParam",typeJson);
                break;
            default:
                paramsJson.put("Mp4Param",typeJson);
        }
        int templateId=0;
        JSONObject value=getLiveRecordTemplateClientJson(paramsJson.toString());
        if(value!=null && !value.isEmpty()){
            templateId=value.get("TemplateId") == null ? 0:value.getIntValue("TemplateId");
        }
        return templateId;
    }

    /**
     * api说明：
            录制后的文件存放于点播平台。用户如需使用录制功能，需首先自行开通点播账号并确保账号可用。录制文件存放后，
            相关费用（含存储以及下行播放流量）按照点播平台计费方式收取，请参考对应文档。
            默认接口请求频率限制：200次/秒
     * 停止录制任务
     * @param liveRecord
     * @return
     */
    public boolean stopLiveRecord(CTLiveRecord liveRecord){
        JSONObject paramsJson=new JSONObject();
        if(!StringUtil.isEmpty(liveRecord.getAction())){
            paramsJson.put("Action",liveRecord.getAction());
        }else{
            paramsJson.put("Action",GetCTIntefaceName.STOP_LIVE_RECORD.value());
        }
        if(!StringUtil.isEmpty(liveRecord.getVersion())){
            paramsJson.put("Version",liveRecord.getVersion());
        }else{
            paramsJson.put("Version",VERSION_LIVE);
        }
        //流id
        if(!StringUtil.isEmpty(liveRecord.getStreamId())){
            paramsJson.put("StreamName",liveRecord.getStreamId());
        }else{
            paramsJson.put("StreamName","");
        }
        if(liveRecord.getTaskId()!=0){
            paramsJson.put("TaskId",liveRecord.getTaskId());
        }else{
            logger.info("任务id不能为0，终止录制任务失败");
            return false;
        }
        JSONObject value=stopLiveRecordClientJson(paramsJson.toString());
        if(value!=null && !value.isEmpty()){
            String requestId=value.getString("RequestId");
            if(!StringUtil.isEmpty(requestId)){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    /**
     * 默认接口请求频率限制：200次/秒。
     * 删除录制任务
     * @param liveRecord
     * @return
     */
    public boolean deleteLiveRecord(CTLiveRecord liveRecord){
        JSONObject paramsJson=new JSONObject();
        if(!StringUtil.isEmpty(liveRecord.getAction())){
            paramsJson.put("Action",liveRecord.getAction());
        }else{
            paramsJson.put("Action",GetCTIntefaceName.DELETE_LIVE_RECORD.value());
        }
        if(!StringUtil.isEmpty(liveRecord.getVersion())){
            paramsJson.put("Version",liveRecord.getVersion());
        }else{
            paramsJson.put("Version",VERSION_LIVE);
        }
        //流id
        if(!StringUtil.isEmpty(liveRecord.getStreamId())){
            paramsJson.put("StreamName",liveRecord.getStreamId());
        }else{
            paramsJson.put("StreamName","");
        }
        if(liveRecord.getTaskId()!=0){
            paramsJson.put("TaskId",liveRecord.getTaskId());
        }else{
            logger.info("任务id不能为0，删除录制任务失败");
            return false;
        }
        JSONObject value=deleteLiveRecordClientJson(paramsJson.toString());
        if(value!=null && !value.isEmpty()){
            String requestId=value.getString("RequestId");
            if(!StringUtil.isEmpty(requestId)){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    /**
     * api说明：
             搜索媒体信息，支持各种条件筛选，以及对返回结果进行排序、过滤等功能，具体包括：
             根据媒体文件名或描述信息进行文本模糊搜索。
             根据媒体分类、标签进行检索。
             指定分类集合 ClassIds（见输入参数），返回满足集合中任意分类的媒体。例如：假设媒体分类有电影、电视剧、综艺，
             其中电影又有子分类历史片、动作片、言情片。如果 ClassIds 指定了电影、电视剧，那么电影和电视剧下的所有子分类都会返回；
             而如果 ClassIds 指定的是历史片、动作片，那么只有这 2 个子分类下的媒体才会返回。
             指定标签集合 Tags（见输入参数），返回满足集合中任意标签的媒体。例如：假设媒体标签有二次元、宫斗、鬼畜，如果 Tags
             指定了二次元、鬼畜 2 个标签，那么只要符合这 2 个标签中任意一个的媒体都会被检索出来。
             允许指定筛选某一来源 Source（见输入参数）的媒体。
             允许根据直播推流码、Vid（见输入参数）筛选直播录制的媒体。
             允许根据媒体的创建范围筛选媒体。
             允许对上述条件进行任意组合，检索同时满足以上条件的媒体。例如可以筛选从 2018 年 12 月 1 日到 2018 年 12 月 8 日
             创建的电影、电视剧分类下带有宫斗、鬼畜标签的媒体。
             允许对结果进行排序，允许通过 Offset 和 Limit 实现只返回部分结果。
             接口搜索限制：
             搜索结果超过5000条，不再支持分页查询超过 5000 部分的数据。
             默认接口请求频率限制：100次/秒。
     */
    public Map<String,Object> searchMedia(CTVodMedia vodMedia){
        JSONObject paramsJson=new JSONObject();
        if (!StringUtil.isEmpty(vodMedia.getAction())){
            paramsJson.put("Action",vodMedia.getAction());
        }else{
            paramsJson.put("Action",GetCTIntefaceName.SEARCH_MEDIA.value());
        }
        if(!StringUtil.isEmpty(vodMedia.getVersion())){
            paramsJson.put("Version",vodMedia.getVersion());
        }else{
            paramsJson.put("Version",VERSION_VOD);
        }
        if(!StringUtil.isEmpty(vodMedia.getText())){
            paramsJson.put("Text",vodMedia.getText());
        }
        if(vodMedia.getTags()!=null && vodMedia.getTags().length>0){
            paramsJson.put("Tags.N",vodMedia.getTags());
        }
        if(vodMedia.getClassIds()!=null && vodMedia.getClassIds().length>0){
            paramsJson.put("ClassIds.N",vodMedia.getClassIds());
        }
       /* if(!StringUtil.isEmpty(vodMedia.getStartTime())){
            paramsJson.put("StartTime", convertTimeToUTC(vodMedia.getStartTime()));
        }
        if(!StringUtil.isEmpty(vodMedia.getEndTime())){
            paramsJson.put("EndTime", convertTimeToUTC(vodMedia.getEndTime()));
        }*/
        /**
             媒体文件的来源类别：
                 Record：来自录制。如直播录制、直播时移录制等。
                 Upload：来自上传。如拉取上传、服务端上传、客户端 UGC 上传等。
                 VideoProcessing：来自视频处理。如视频拼接、视频剪辑等。
                 Unknown：未知来源。
         */
        if(!StringUtil.isEmpty(vodMedia.getSourceType())){
            paramsJson.put("SourceType",vodMedia.getSourceType());
        }
        if(!StringUtil.isEmpty(vodMedia.getStreamId())){
            paramsJson.put("StreamId",vodMedia.getStreamId());
        }
        if(!StringUtil.isEmpty(vodMedia.getVid())){
            paramsJson.put("Vid",vodMedia.getVid());
        }
        /**
             排序方式。
             Sort.Field 可选值：CreateTime
             指定 Text 搜索时，将根据匹配度排序，该字段无效
         */
        if(!StringUtil.isEmpty(vodMedia.getField()) && !StringUtil.isEmpty(vodMedia.getOrder())){
            JSONObject sortJson=new JSONObject();
            sortJson.put("Field",vodMedia.getField());
            sortJson.put("Order",vodMedia.getOrder());
            paramsJson.put("Sort",sortJson);
        }
        if(vodMedia.getLimit()!=0){
            paramsJson.put("Limit",vodMedia.getLimit());
        }
        if(vodMedia.getSubAppId()!=0){
            paramsJson.put("SubAppId",vodMedia.getSubAppId());
        }
        Map<String,Object> result=new HashMap<>();
        JSONObject value=getVodClientJson(paramsJson.toString());
        if(value!=null && !value.isEmpty()){
            /**
             * 返回值说明
             * TotalCount:  符合搜索条件的记录总数
             *              最大值：5000，即，当命中记录数超过 5000，该字段将返回 5000，而非实际命中总数。
             * MediaInfoSet: 媒体文件信息列表，只包含基础信息（BasicInfo）
             *               注意：此字段可能返回 null，表示取不到有效值。
             */
            result.put("totalCount",value.getIntValue("TotalCount"));
            result.put("mediaInfoSet", JSONArray.parseArray(value.getString("MediaInfoSet")));
        }else{
            result.put("totalCount",0);
            result.put("mediaInfoSet","");
        }
        return result;
    }

    /**
     * 将日期转换成ISO格式
     * @param dateTime
     * @return
     */
    private String convertTimeToUTC(String dateTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        //获取时区
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date dt =  DateUtil.parseDatetime(dateTime);
//            System.out.println(sdf.format(dt));
            return sdf.format(dt);
        } catch (ParseException e) {
            logger.error("日期格式转换成ISO日期格式异常",e);
        }
        return "";
    }
    /**
     * 获取点播api数据
     * @param params
     * @return
     */
    private JSONObject getVodClientJson(String params){
        try {
            SearchMediaRequest req = SearchMediaRequest.fromJsonString(params, SearchMediaRequest.class);
            SearchMediaResponse resp = ((VodClient)CloudTencentApi2Factory.getClient("vodClient")).SearchMedia(req);
            String jsonStr=SearchMediaRequest.toJsonString(resp);
//            System.out.println("getVodClientJson============="+jsonStr);
            if(!StringUtil.isEmpty(jsonStr)){
                return JSONObject.parseObject(jsonStr);
            }
        } catch (TencentCloudSDKException e) {
            logger.error("调用腾讯api获取点播数据异常",e);
        }catch (Exception e){
            logger.error("转换点播数据为json异常",e);
        }
        return null;
    }

    /**
     * 获取创建录制任务api数据
     * @param params
     * @return
     */
    private JSONObject getLiveRecordClientJson(String params){
        try {
            CreateLiveRecordRequest req = CreateLiveRecordRequest.fromJsonString(params, CreateLiveRecordRequest.class);
            CreateLiveRecordResponse resp = ((LiveClient)CloudTencentApi2Factory.getClient("liveClient")).CreateLiveRecord(req);
            String jsonStr = CreateLiveRecordRequest.toJsonString(resp);
            if(!StringUtil.isEmpty(jsonStr)){
                return JSONObject.parseObject(jsonStr);
            }
        } catch (TencentCloudSDKException e) {
            logger.error("调用腾讯api获取创建录制任务数据异常",e);
        }catch (Exception e){
            logger.error("转换创建录制任务数据为json异常",e);
        }
        return null;
    }

    /**
     * 终止录制任务api数据
     * @param params
     * @return
     */
    private JSONObject stopLiveRecordClientJson(String params){
        try {
            StopLiveRecordRequest req = StopLiveRecordRequest.fromJsonString(params, StopLiveRecordRequest.class);
            StopLiveRecordResponse resp = ((LiveClient)CloudTencentApi2Factory.getClient("liveClient")).StopLiveRecord(req);
            String jsonStr = StopLiveRecordRequest.toJsonString(resp);
            if(!StringUtil.isEmpty(jsonStr)){
                return JSONObject.parseObject(jsonStr);
            }
        } catch (TencentCloudSDKException e) {
            logger.error("调用腾讯api终止录制任务数据异常",e);
        }catch (Exception e){
            logger.error("转换终止录制任务数据为json异常",e);
        }
        return null;
    }

    /**
     * 删除录制任务api数据
     * @param params
     * @return
     */
    private JSONObject deleteLiveRecordClientJson(String params){
        try {
            DeleteLiveRecordRequest req = DeleteLiveRecordRequest.fromJsonString(params, DeleteLiveRecordRequest.class);
            DeleteLiveRecordResponse resp = ((LiveClient)CloudTencentApi2Factory.getClient("liveClient")).DeleteLiveRecord(req);
            String jsonStr = DeleteLiveRecordRequest.toJsonString(resp);
            if(!StringUtil.isEmpty(jsonStr)){
                return JSONObject.parseObject(jsonStr);
            }
        } catch (TencentCloudSDKException e) {
            logger.error("调用腾讯api删除录制任务数据异常",e);
        }catch (Exception e){
            logger.error("转换删除录制任务数据为json异常",e);
        }
        return null;
    }

    /**
     * 获取录制规则api数据
     * @param params
     * @return
     */
    private JSONObject getLiveRecordRuleClientJson(String params){
        try {
            CreateLiveRecordRuleRequest  req = CreateLiveRecordRuleRequest.fromJsonString(params, CreateLiveRecordRuleRequest.class);
            CreateLiveRecordRuleResponse  resp = ((LiveClient)CloudTencentApi2Factory.getClient("liveClient")).CreateLiveRecordRule(req);
            String jsonStr = CreateLiveRecordRuleRequest.toJsonString(resp);
            if(!StringUtil.isEmpty(jsonStr)){
                return JSONObject.parseObject(jsonStr);
            }
        } catch (TencentCloudSDKException e) {
            logger.error("调用腾讯api获取创建录制规则数据异常",e);
        }catch (Exception e){
            logger.error("转换创建录制规则数据为json异常",e);
        }
        return null;
    }

    /**
     * 获取录制模板api数据
     * @param params
     * @return
     */
    private JSONObject getLiveRecordTemplateClientJson(String params){
        try {
            CreateLiveRecordTemplateRequest  req = CreateLiveRecordTemplateRequest.fromJsonString(params, CreateLiveRecordTemplateRequest.class);
            CreateLiveRecordTemplateResponse resp = ((LiveClient)CloudTencentApi2Factory.getClient("liveClient")).CreateLiveRecordTemplate(req);
            String jsonStr = CreateLiveRecordTemplateRequest.toJsonString(resp);
            if(!StringUtil.isEmpty(jsonStr)){
                return JSONObject.parseObject(jsonStr);
            }
        } catch (TencentCloudSDKException e) {
            logger.error("调用腾讯api获取创建录制模板数据异常",e);
        }catch (Exception e){
            logger.error("转换创建录制规则模板为json异常",e);
        }
        return null;
    }

    /**
     * 获取腾讯云api接口方法名
     */
    private enum GetCTIntefaceName{
        //创建录制任务
        CREATE_LIVE_RECORD("CreateLiveRecord"),
        //创建录制规则
        CREATE_LIVE_RECORD_RULE("CreateLiveRecordRule"),
        //创建录制模板
        CREATE_LIVE_RECORD_TEMPLATE("CreateLiveRecordTemplate"),
        //终止录制任务
        STOP_LIVE_RECORD("StopLiveRecord"),
        //删除录制任务
        DELETE_LIVE_RECORD("DeleteLiveRecord"),
        //搜索媒体信息
        SEARCH_MEDIA("SearchMedia");


        private String intefaceName = "";

        GetCTIntefaceName(String intefaceName){
            this.intefaceName = intefaceName;
        }

        public String value(){
            return this.intefaceName;
        }
    }
}
