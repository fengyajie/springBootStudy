package com.example.dao.system;

import java.util.List;

import com.example.domain.system.SysUser;

public interface SysUserMapper{
	
	Long[] listAllDept();
	
	List<SysUser> selectList(SysUser sysUser);
	
	SysUser selectByKey(Long userId);
}