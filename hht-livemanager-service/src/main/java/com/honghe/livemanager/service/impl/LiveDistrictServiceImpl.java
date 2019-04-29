package com.honghe.livemanager.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.honghe.livemanager.common.util.HttpSearchAreaUtil;
import com.honghe.livemanager.entity.LiveDistrict;
import com.honghe.livemanager.service.LiveDistrictService;
import org.springframework.stereotype.Service;

@Service("districtService")
public class LiveDistrictServiceImpl implements LiveDistrictService {

	public LiveDistrict selectById(int areaId,int parentId,int level){
		LiveDistrict liveDistrict=getDistinctByParentId(areaId,parentId,level);
		return liveDistrict;
	}

	private LiveDistrict getDistinctByParentId(int areaId,int parentId,int level) {
		LiveDistrict liveDistrict=new LiveDistrict();
		JSONArray areaData= HttpSearchAreaUtil.getInstance().searchArea(parentId,level);
		if(areaData!=null && !areaData.isEmpty()){
			for(Object o:areaData){
				JSONObject areaJson=JSONObject.parseObject(o.toString());
				if(areaJson!=null && !areaJson.isEmpty() && Integer.parseInt(String.valueOf(areaJson.get("id")))==areaId) {
					liveDistrict.setId(areaId);
					liveDistrict.setCode(String.valueOf(areaJson.get("code")));
					liveDistrict.setLevel(Integer.parseInt(String.valueOf(areaJson.get("level"))));
					liveDistrict.setName(areaJson.get("name").toString());
					liveDistrict.setParentId(String.valueOf(parentId));
					break;
				}
			}
		}
		return liveDistrict;
	}
}