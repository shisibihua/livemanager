package com.honghe.livemanager.service;


import com.honghe.livemanager.common.pojo.model.Result;
import com.honghe.livemanager.entity.LiveHistory;

/**
 * 数据统计
 *
 * @Author libing
 * @Date: 2018-09-26 14:35
 * @Mender:
 */
public interface LiveStatisticService {
    /**
     * 查询直播统计折线数据
     * @param beginTime  开始时间
     * @param endTime    结束时间
     * @param dateType   查询类型
     * @return
     */
    public Result getLiveLineCharts(String beginTime, String endTime, int dateType);

    /**
     * 获取直播统计排行榜
     * @param liveHistory  直播历史信息
     * @return
     */
    public Result getLiveStatisticList(LiveHistory liveHistory);

    /**
     * 获取直播统计详情
     * @param liveHistory 直播历史信息
     * @return
     */
    Result getLiveStatisticDetails(LiveHistory liveHistory);

    /**
     * 导出直播统计列表
     * @param liveHistory 直播历史信息
     * @return
     */
    Result exportStatisticList(LiveHistory liveHistory);
}
