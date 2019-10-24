package com.example.domain;

import java.io.Serializable;
import java.util.Set;

import com.google.common.base.Joiner;

public class UserInfo implements Serializable {

	private Long userId;
	private String username;
	private String realName;
	private String password;
	private String openId;
	private String picurl;
	private int status;
	private Integer loginType;
	private Long organId;
	private Long storeId;
	private Long deptId;

private String businessIds;
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public Integer getLoginType() {
		return loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}
	public Long getOrganId() {
		return organId;
	}
	public void setOrganId(Long organId) {
		this.organId = organId;
	}
	public Long getStoreId() {
		return storeId;
	}
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	/**
	 * @return the businessIds
	 */
	public String getBusinessIds() {
		return businessIds;
	}

	/**
	 * @param businessIds the businessIds to set
	 */
	public void setBusinessIds(Set<String> listString) { 
		Joiner joiner = Joiner.on(",").skipNulls();
		this.businessIds =  joiner.join(listString) ; 
	}
	
}
