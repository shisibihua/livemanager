package com.honghe.livemanager.cloud.tencent.api.model;

/**
 * 录制模板实体
 * @author caoqian
 * @date 20190311
 */
public class CTLiveRecordTemplate {
    //接口名称
    private String action;
    //版本号
    private String version;
    //模板名称，非空的字符串
    private String templateName;
    //描述信息
    private String description;
    /**
        录制间隔。
        单位秒，默认值1800。
        取值范围:300-7200。
        此参数对 HLS 无效，当录制 HLS 时从推流到断流生成一个文件。
     */
    private int recordInterval;
    /**
         录制存储时长。
         单位秒，取值范围： 0-5184000。
         0表示永久存储
     */
    private int storageTime;
    /**
     * 是否开启当前格式录制，0 否 1是。默认值0。
     */
    private int enable;
    //录制参数，1：Flv录制；2：Hls录制;3:Mp4录制；4:Aac录制
    private int paramType;

    public CTLiveRecordTemplate() {
    }

    public CTLiveRecordTemplate(String action, String version, String templateName,
                                String description, int recordInterval, int storageTime,
                                int enable,int paramType) {
        this.action = action;
        this.version = version;
        this.templateName = templateName;
        this.description = description;
        this.recordInterval = recordInterval;
        this.storageTime = storageTime;
        this.enable = enable;
        this.paramType=paramType;
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

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public int getParamType() {
        return paramType;
    }

    public void setParamType(int paramType) {
        this.paramType = paramType;
    }
}
