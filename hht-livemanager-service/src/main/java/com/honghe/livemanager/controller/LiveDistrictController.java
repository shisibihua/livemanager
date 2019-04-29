package com.honghe.livemanager.controller;


import com.alibaba.fastjson.JSONArray;
import com.honghe.livemanager.common.pojo.model.Result;
import com.honghe.livemanager.common.util.HttpSearchAreaUtil;
import com.honghe.livemanager.common.util.TipsMessage;
import com.honghe.livemanager.util.ConvertResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author libing
 * @version 1.0
 * @created 08-9月-2018 13:16:45
 * @Description 行政区controller
 */
@CrossOrigin
@RestController("districtController")
@RequestMapping("district")
public class LiveDistrictController {

	/**
	 * 查询地区
	 * @param parentId  父级id
	 * @param level     级别
	 * @param schoolName 学校名称
	 * @return
	 */
	@GetMapping("getByParentId")
	public Result getByParentId(int parentId,int level,String schoolName){
		if(0>parentId || 0>level){
			return ConvertResult.getParamErrorResult();
		}
		JSONArray result=new JSONArray();
		if(TipsMessage.SCHOOL_LEVEL==level){
			result=HttpSearchAreaUtil.getInstance().searchSchools(parentId,schoolName);
		}else{
			result=HttpSearchAreaUtil.getInstance().searchArea(parentId,level);
		}
		return ConvertResult.getSuccessResult(result);
	}


}