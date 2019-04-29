package com.honghe.livemanager.entity;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author libing
 * @version 1.0
 * @created 08-9月-2018 13:16:46
 */
public class LiveLicense {
	private Integer id;
	//开始时间
	private String beginTime;
	//联系人
	private String contact;
	//联系方式
	private String contactNumber;
	//创建时间
	private Date createTime;
	//结束时间
	private String endTime;
	//授权码
	private String licenseCode;
	//名称
	private String name;
	//状态 0 禁用，1 启用
	private Integer status;
	//市id
	private Integer cityId;
	//区id
	private Integer countyId;
	//省id
	private Integer provinceId;
	//详细地区信息
	private String address;
	//备注
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private List<LiveLicenseATT> liveLicenseATTs;



	public List<LiveLicenseATT> getLiveLicenseATTs() {
		return liveLicenseATTs;
	}

	public void setLiveLicenseATTs(List<LiveLicenseATT> liveLicenseATTs) {
		this.liveLicenseATTs = liveLicenseATTs;
	}


	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getEndTime() {
		if(endTime!=null && endTime.endsWith(".0")){
			endTime=endTime.substring(0,endTime.lastIndexOf("."));
		}
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getBeginTime() {
		if(beginTime!=null && beginTime.endsWith(".0")){
			beginTime=beginTime.substring(0,beginTime.lastIndexOf("."));
		}
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getLicenseCode() {
		return licenseCode;
	}

	public void setLicenseCode(String licenseCode) {
		this.licenseCode = licenseCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getCountyId() {
		return countyId;
	}

	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
