package com.honghe.livemanager.cloud.tencent.api.model;

/**
 * 录制规则实体
 * @author caoqian
 * @date 20190311
 */
public class CTLiveRecordRule {
    //接口名称
    private String action;
    //版本号
    private String version;
    //推流域名
    private String domainName;
    //推流App名
    private String appName;
    //流id
    private String streamId;
    //模板id
    private int templateId;

    public CTLiveRecordRule() {
    }

    public CTLiveRecordRule(String action, String version, String domainName,
                            String appName, String streamId, int templateId) {
        this.action = action;
        this.version = version;
        this.domainName = domainName;
        this.appName = appName;
        this.streamId = streamId;
        this.templateId = templateId;
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

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }
}
