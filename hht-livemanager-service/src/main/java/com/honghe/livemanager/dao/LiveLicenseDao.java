package com.honghe.livemanager.dao;


import com.honghe.livemanager.entity.LiveLicense;

import java.util.List;
import java.util.Map;

/**
 * 授权
 * @author libing
 * @version 1.0
 * @created 08-9月-2018 13:16:46
 */
public interface LiveLicenseDao {

	/**
	 * 添加授权
	 * @param license 授权信息
	 */
	public int add(LiveLicense license);

	/**
	 * 删除授权
	 * @param id 授权id
	 * @return
	 */
	public int deleteById(int id);

	/**
	 * 查询授权信息
	 * @param id 授权id
	 * @return
	 */
	public LiveLicense selectById(int id);

	/**
	 * 根据名称查询授权列表
	 * @param name  授权名称
	 * @return
	 */
	public List<LiveLicense> selectByName(String name );

	/**
	 * 根据授权码查询授权
	 * @param licenseCode 授权码
	 * @return
	 */
	public LiveLicense selectByLicenseCode(String licenseCode);

	/**
	 * 分页查询授权
	 * @param map
	 */
	public List<LiveLicense> selectByPage(Map map);

	/**
	 * 查询当前日期的授权
	 * @return
	 */
	public List<LiveLicense> selectByEndDate();



	/**
	 * 查询授权总数
	 * @param map
	 */
	public int countSelectByPage(Map map);


	/**
	 * 更新授权
	 * @param license 授权信息
	 */
	public int update(LiveLicense license);



}