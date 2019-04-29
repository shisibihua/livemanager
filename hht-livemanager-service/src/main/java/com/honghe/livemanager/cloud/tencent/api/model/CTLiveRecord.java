package com.honghe.livemanager.cloud.tencent.api.model;

/**
 * 录制任务实体
 * @author caoqian
 * @date 20190311
 */
public class CTLiveRecord {
    //任务id
    private int taskId;
    //接口名称
    private String action;
    //版本号，2018-08-01
    private String version;
    //流id
    private String streamId;
    //推流App名
    private String appName;
    //推流域名
    private String domainName;
    //录制开始时间
    private String startTime;
    //录制结束时间
    private String endTime;
    //录制类型
    private String recordType;
    //录制文件格式
    private String fileFormat;
    //开启实时视频录制模式标志,0：不开启实时视频录制模式，即采用定时录制模式【默认】；1：开启实时视频录制模式。
    private int highLight;
    //开启A+B=C混流C流录制标志。0：不开启A+B=C混流C流录制【默认】；1：开启A+B=C混流C流录制。
    private int mixStream;
    //录制分片时长，单位 秒，1800 - 7200
    private int recordInterval;
    //录制文件存储时长，单位 秒，默认永久存储
    private int storageTime;

    public CTLiveRecord() {
    }

    public CTLiveRecord(int taskId,String action,String version,String streamId, String appName, String domainName,
                        String startTime, String endTime,String recordType, String fileFormat, int highLight,
                        int mixStream, int recordInterval,int storageTime) {
        this.taskId=taskId;
        this.action=action;
        this.version=version;
        this.streamId = streamId;
        this.appName = appName;
        this.domainName = domainName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.recordType = recordType;
        this.fileFormat = fileFormat;
        this.highLight = highLight;
        this.mixStream = mixStream;
        this.recordInterval = recordInterval;
        this.storageTime = storageTime;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public int getHighLight() {
        return highLight;
    }

    public void setHighLight(int highLight) {
        this.highLight = highLight;
    }

    public int getMixStream() {
        return mixStream;
    }

    public void setMixStream(int mixStream) {
        this.mixStream = mixStream;
    }

    public int getRecordInterval() {
        return recordInterval;
    }

    public void setRecordInterval(int recordInterval) {
        this.recordInterval = recordInterval;
    }

    public int getStorageTime() {
        return storageTime;
    }

    public void setStorageTime(int storageTime) {
        this.storageTime = storageTime;
    }
}
