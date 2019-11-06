package com.example.domain.system;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;


import com.google.common.base.Joiner;

public class SysUser implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4301210421374250030L;

	/**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 密码
     */
    private String password;
    
    private String realName;

    /**
     * 部门ID
     */
    private Long deptId;
    
    private String deptName;

    /**
     * 机构Id
     */
    private Long organId;
    
    /**
     * 商户Id
     */
    private Long storeId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 联系电话
     */
    private String mobile;

    /**
     * 微信openId
     */
    private String openId;

    /**
     * 状态 0锁定 1有效
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 修改人
     */
    private Long modifyBy;

    /**
     * 最近访问时间
     */
    private Date lastLoginTime;

    /**
     * 性别 1男 2女
     */
    private String ssex;

    /**
     * 主题
     */
    private String theme;

    /**
     * 头像
     */
    private Long avatar;

    private String picurl;

    /**
     * 描述
     */
    private String descrption;
    
    //角色
    private List<Long> roleIds;
    
    private Long dataRoleId;
    public Long getDataRoleId() {
		return dataRoleId;
	}

	public void setDataRoleId(Long dataRoleId) {
		this.dataRoleId = dataRoleId;
	}

    private String businessIds;
    
    private Date birth;
    
    private String liveAddress;
    private Long sellerSysno;
    private Long organSysno;
    public Long getSellerSysno() {
		return sellerSysno;
	}

	public void setSellerSysno(Long sellerSysno) {
		this.sellerSysno = sellerSysno;
	}

	public Long getOrganSysno() {
		return organSysno;
	}

	public void setOrganSysno(Long organSysno) {
		this.organSysno = organSysno;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getLiveAddress() {
		return liveAddress;
	}

	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress;
	}

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	/**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取用户名称
     *
     * @return username - 用户名称
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名称
     *
     * @param username 用户名称
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取部门ID
     *
     * @return dept_id - 部门ID
     */
    public Long getDeptId() {
        return deptId;
    }

    /**
     * 设置部门ID
     *
     * @param deptId 部门ID
     */
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
     * 获取机构Id
     *
     * @return organ_id - 机构Id
     */
    public Long getOrganId() {
        return organId;
    }

    /**
     * 设置机构Id
     *
     * @param organId 机构Id
     */
    public void setOrganId(Long organId) {
        this.organId = organId;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取联系电话
     *
     * @return mobile - 联系电话
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置联系电话
     *
     * @param mobile 联系电话
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 获取微信openId
     *
     * @return open_id - 微信openId
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置微信openId
     *
     * @param openId 微信openId
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * 获取状态 0锁定 1有效
     *
     * @return status - 状态 0锁定 1有效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 0锁定 1有效
     *
     * @param status 状态 0锁定 1有效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取创建人
     *
     * @return create_by - 创建人
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人
     *
     * @param createBy 创建人
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取修改时间
     *
     * @return modify_time - 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置修改时间
     *
     * @param modifyTime 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 获取修改人
     *
     * @return modify_by - 修改人
     */
    public Long getModifyBy() {
        return modifyBy;
    }

    /**
     * 设置修改人
     *
     * @param modifyBy 修改人
     */
    public void setModifyBy(Long modifyBy) {
        this.modifyBy = modifyBy;
    }

    /**
     * 获取最近访问时间
     *
     * @return last_login_time - 最近访问时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置最近访问时间
     *
     * @param lastLoginTime 最近访问时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 获取性别 0男 1女
     *
     * @return ssex - 性别 0男 1女
     */
    public String getSsex() {
        return ssex;
    }

    /**
     * 设置性别 0男 1女
     *
     * @param ssex 性别 0男 1女
     */
    public void setSsex(String ssex) {
        this.ssex = ssex == null ? null : ssex.trim();
    }

    /**
     * 获取主题
     *
     * @return theme - 主题
     */
    public String getTheme() {
        return theme;
    }

    /**
     * 设置主题
     *
     * @param theme 主题
     */
    public void setTheme(String theme) {
        this.theme = theme == null ? null : theme.trim();
    }

    /**
     * 获取头像
     *
     * @return avatar - 头像
     */
    public Long getAvatar() {
        return avatar;
    }

    /**
     * 设置头像
     *
     * @param avatar 头像
     */
    public void setAvatar(Long avatar) {
        this.avatar = avatar;
    }

    /**
     * 获取描述
     *
     * @return desc - 描述
     */
    public String getDescrption() {
        return descrption;
    }

    /**
     * 设置描述
     *
     * @param desc 描述
     */
    public void setDescrption(String descrption) {
        this.descrption = descrption == null ? null : descrption.trim();
    }

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		  this.realName = realName == null ? null : realName.trim();
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
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