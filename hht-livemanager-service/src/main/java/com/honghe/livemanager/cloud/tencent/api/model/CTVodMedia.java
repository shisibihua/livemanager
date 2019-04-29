package com.honghe.livemanager.cloud.tencent.api.model;

/**
 * 点播实体
 * @author caoqian
 * @date 20190311
 */
public class CTVodMedia {
    //接口名称
    private String action;
    //版本号
    private String version;
    //搜索文本，模糊匹配媒体文件名称或描述信息，匹配项越多，匹配度越高，排序越优先。长度限制：64 个字符
    private String text;
    //标签集合
    private String[] tags;
    //分类 ID 集合
    private int[] classIds;
    //创建时间的开始时间
    private String startTime;
    //创建时间的结束时间
    private String endTime;
    //媒体文件来源
    private String sourceType;
    //推流直播码
    private String streamId;
    //直播录制文件的唯一标识
    private String vid;
    //排序字段
    private String field;
    //排序方式，可选值：Asc（升序）、Desc（降序）
    private String order;
    //偏移量,默认为0
    private int offSet;
    //返回记录条数，默认值：10
    private int limit;
    //点播子应用 ID
    private int subAppId;

    public CTVodMedia() {
    }

    public CTVodMedia(String action, String version, String text, String[] tags, int[] classIds,
                      String startTime, String endTime, String sourceType, String streamId,
                      String vid, String field, String order, int offSet, int limit, int subAppId) {
        this.action = action;
        this.version = version;
        this.text = text;
        this.tags = tags;
        this.classIds = classIds;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sourceType = sourceType;
        this.streamId = streamId;
        this.vid = vid;
        this.field = field;
        this.order = order;
        this.offSet = offSet;
        this.limit = limit;
        this.subAppId = subAppId;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public int[] getClassIds() {
        return classIds;
    }

    public void setClassIds(int[] classIds) {
        this.classIds = classIds;
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

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getOffSet() {
        return offSet;
    }

    public void setOffSet(int offSet) {
        this.offSet = offSet;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getSubAppId() {
        return subAppId;
    }

    public void setSubAppId(int subAppId) {
        this.subAppId = subAppId;
    }
}
