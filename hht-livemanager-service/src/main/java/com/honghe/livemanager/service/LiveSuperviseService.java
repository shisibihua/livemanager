package com.honghe.livemanager.service;

import com.honghe.livemanager.common.pojo.model.Page;
import com.honghe.livemanager.entity.LiveSupervise;

import java.util.List;

/**
 * 直播监黄
 * @author caoqian
 */
public interface LiveSuperviseService {
    /**
     * 添加监黄信息
     * @param liveSupervise 监黄信息
     * @return
     */
    int add(LiveSupervise liveSupervise);

    /**
     * 分页获取列表
     * @param currentPage  当前页
     * @param pageSize     每页条数
     * @return
     */
    Page getLiveSuperviseListByPage(int currentPage,int pageSize);

    /**
     * 获取所有列表
     * @return
     */
    List<LiveSupervise> getLiveSuperviseList();

    /**
     * 获取图片数量
     * @return
     */
    int getPicCount();
}
