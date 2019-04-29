package com.honghe.livemanager.controller;


import com.honghe.livemanager.common.pojo.model.Result;
import com.honghe.livemanager.common.util.ParamUtil;
import com.honghe.livemanager.common.util.TipsMessage;
import com.honghe.livemanager.entity.LiveLicense;
import com.honghe.livemanager.service.LiveLicenseService;
import com.honghe.livemanager.util.Constants;
import com.honghe.livemanager.util.ConvertResult;
import com.honghe.livemanager.util.MyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * 配置业务接口类
 * @author libing
 * @version 1.0
 * @created 08-9月-2018 13:16:46
 */
@CrossOrigin
@RestController("licenseController")
@RequestMapping("license")
public class LiveLicenseController {

	@Autowired
	LiveLicenseService licenseService;
	private static String NOT_DELETE="授权开启状态不能删除!";

	/**
	 * 添加授权
	 * @param license 授权信息
	 * @return
	 */
	@MyLog("服务接入-添加授权")
	@PostMapping("add")
	public Result add(@RequestBody LiveLicense license){

		if(null==license|| ParamUtil.isOneEmpty(license.getName(),license.getBeginTime(),
				license.getEndTime())){
			return ConvertResult.getParamErrorResult();
		}

		if (licenseService.add(license)) {
			return ConvertResult.getSuccessResult(true);
		} else {
			return getRepeatNameResult();
		}
	}

	/**
	 * 修改授权
	 * @param license 授权信息
	 * @return
	 */
	@MyLog("服务接入-修改授权")
	@PostMapping("update")
	public Result update(@RequestBody LiveLicense license){
		if(null==license|| ParamUtil.isOneEmpty(license.getName(),license.getBeginTime(),
				license.getEndTime())){
			return ConvertResult.getParamErrorResult();
		}
		if (licenseService.update(license)) {
			return ConvertResult.getSuccessResult(true);
		} else {
			return getRepeatNameResult();
		}
	}

	/**
	 * 禁用/启用授权
	 * @param license 授权信息
	 * @return
	 */
	@MyLog("服务接入-禁用/启用授权")
	@PostMapping("toggleStatus")
	public Result toggleStatus(@RequestBody LiveLicense license){
		if(null==license|| ParamUtil.isOneEmpty(license.getName(),license.getStatus(),
				license.getId())){
			return ConvertResult.getParamErrorResult();
		}
		if (licenseService.toggleStatus(license)) {
			return ConvertResult.getSuccessResult(true);
		} else {
			return ConvertResult.getSuccessResult(false);
		}
	}

	/**
	 * 删除授权
	 * @param map 授权信息
	 * @return
	 */
	@MyLog("服务接入-删除授权")
	@PostMapping("delete")
	public Result delete(@RequestBody Map map){
		Integer id = (Integer) map.get("id");
		if(null==id){
			return ConvertResult.getParamErrorResult();
		}

		if (licenseService.delete(id)) {
			return ConvertResult.getSuccessResult(true);
		} else {
			return ConvertResult.getSuccessResult(false);

		}
	}

	/**
	 * 查询授权信息
	 * @param page        当前页
	 * @param pageSize    每页条数
	 * @param key         学校名关键字
	 * @param beginTime   开始时间
	 * @param endTime     结束时间
	 * @return
	 */
	@GetMapping("selectByPage")
	public Result selectByPage(int page,int pageSize,String key,String beginTime,String endTime){
		return ConvertResult.getSuccessResult(licenseService.selectByPage(page,pageSize,key,beginTime,endTime));

	}

	/**
	 * 查询授权详情
	 * @param id 授权id
	 * @return
	 */
	@GetMapping("selectById")
	public Result selectById(int id){
		if(0==id){
			return ConvertResult.getParamErrorResult();
		}
		return ConvertResult.getSuccessResult(licenseService.selectById(id));

	}

	/**
	 * 删除授权附件
	 * @param map
	 * @return
	 */
	@MyLog("服务接入-删除授权附件")
	@PostMapping("deleteLicenseAttById")
	public Result deleteLicenseAttById(@RequestBody Map map){
		Integer id;
		if(null==map.get("id")){
			id= 0;
		}else{
			id = (Integer)map.get("id");
		}
		String[]  path  =String.valueOf(map.get("path")).split("/");
		String  fileAutoName = path[path.length-1];
		return ConvertResult.getSuccessResult(licenseService.deleteLicenseATTById(id,fileAutoName));

	}

	/**
	 * 重名校验
	 * @param id 如果是修改 传入id 和name  如果是新增 只传入name
	 * @param name 名称
	 * @return true 不重名 false重名
	 */
	@GetMapping("checkName")
	public Result checkName(Integer id,String name){
		if(ParamUtil.isOneEmpty(name)){
			return ConvertResult.getParamErrorResult();
		}
		LiveLicense license = new LiveLicense();
		if(id!=null) {
			license.setId(id);
		}
		license.setName(name);
		if (licenseService.checkName(license)) {
			return ConvertResult.getSuccessResult(true);
		} else {
			return ConvertResult.getSuccessResult(false);
		}
	}

	private Result getRepeatNameResult(){
		Result result = new Result();
		result.setCode(TipsMessage.SUCCESS_CODE);
		result.setResult(false);
		result.setMsg(TipsMessage.LICENSE_NAME_ERROR);
		return ConvertResult.getSuccessResult(result);
	}
}