package com.example.service.system;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.domain.system.SysRole;

@Service
public interface SysRoleService{

	List<SysRole> selectUserRoleList(Long userId);
	
	

	List<Long> listMenuIdByRoleId(Long roleId);

	List<Long> listRoleIdsByUserId(Long userId);
	
	List<String> findRoleDesc(List<Long> roleIds);
}
