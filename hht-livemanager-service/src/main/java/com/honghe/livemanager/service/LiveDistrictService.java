package com.honghe.livemanager.service;


import com.honghe.livemanager.entity.LiveDistrict;

/** 行政区
 * @author caoqian
 * @version 1.0
 * @created 2018/10/29
 */
public interface LiveDistrictService {

	/**
	 * 通过id查询行政区
	 * @param areaId      地点id
	 * @param parentId    父级地点id
	 * @param level   级别,1:省;2:市;3:区/县;4:学校
	 * @return
	 */
	LiveDistrict selectById(int areaId,int parentId,int level);

}