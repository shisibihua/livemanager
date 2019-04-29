package com.honghe.livemanager.dao;

import com.honghe.livemanager.entity.LiveHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LiveHistoryDao {

    /**
     * 保存直播历史
     * @param record 历史记录
     * @return
     */
    int insert(LiveHistory record);

    /**
     * 保存直播历史
     * @param record 历史记录
     * @return
     */
    int insertSelective(LiveHistory record);

    /**
     * 批量保存直播历史
     * @param list 历史记录
     * @return
     */
    int insertBatch(List<LiveHistory> list);

    /**
     * 按日期查询
     * @param map  开始日期与结束日期
     * @return
     */
    List<LiveHistory> selectCountByDate(Map map);
}