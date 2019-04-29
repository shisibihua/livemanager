package com.honghe.livemanager.service;


import com.honghe.livemanager.entity.LiveConfig;
import com.honghe.livemanager.entity.LiveMaxLimit;

import java.util.List;

/**
 * 服务配置
 * @author libing
 * @version 1.0
 * @created 08-9月-2018 13:16:45
 */

public interface LiveConfigService {


	/**
	 * 添加
	 * @param liveConfig 授权信息
	 * @return
	 */
	public boolean add(LiveConfig liveConfig);


	/**
	 * 删除
	 * @param id 授权id
	 * @return
	 */
	public boolean delete(int id);


	/**
	 * 更新
	 * @param liveConfig 授权信息
	 * @return
	 */
	public boolean update(LiveConfig liveConfig);


	/**
	 * 查询所有的
	 * @return
	 */
	public List<LiveConfig> selectAll();

	/**
	 * 通过id查询
	 * @param id 授权id
	 * @return
	 */
	public LiveConfig selectById(int id);

	/**
	 * 获取直播数量配置
	 * @return
	 */
	public int getMaxCount();

	/**
	 * 修改直播数量配置
	 * @param liveMaxLimit
	 * @return
	 */
	public boolean updateMaxCount(LiveMaxLimit liveMaxLimit);

}