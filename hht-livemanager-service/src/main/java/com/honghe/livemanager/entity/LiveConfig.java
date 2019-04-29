package com.honghe.livemanager.entity;


import java.util.Date;

/**
 * @author libing
 * @version 1.0
 * @created 08-9月-2018 13:16:45
 */
public class LiveConfig {

	private Integer id;
	//鉴权防盗链key
	private String apiAuthenticationKey;
	//推流防盗链Key
	private String pushSecretKey;
	//应用id
	private String appid;
	//直播id
	private String bizid;
	//创建时间
	private Date createTime;
	//名称
	private String name;
	//监黄防盗链id
	private String secretKey;
	//监黄防盗链key
	private String secretId;

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getSecretId() {
		return secretId;
	}

	public void setSecretId(String secretId) {
		this.secretId = secretId;
	}

	public String getApiAuthenticationKey() {
		return apiAuthenticationKey;
	}

	public void setApiAuthenticationKey(String apiAuthenticationKey) {
		this.apiAuthenticationKey = apiAuthenticationKey;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getBizid() {
		return bizid;
	}

	public void setBizid(String bizid) {
		this.bizid = bizid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getPushSecretKey() {
		return pushSecretKey;
	}

	public void setPushSecretKey(String pushSecretKey) {
		this.pushSecretKey = pushSecretKey;
	}
}