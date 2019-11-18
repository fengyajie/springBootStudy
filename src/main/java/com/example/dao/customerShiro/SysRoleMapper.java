package com.example.dao.customerShiro;



import java.util.List;

import com.example.domain.system.SysRole;


public interface SysRoleMapper{

	List<String> findRoleDesc(List<Long> roleIds);
	
	List<SysRole> selectList();
	
	
}