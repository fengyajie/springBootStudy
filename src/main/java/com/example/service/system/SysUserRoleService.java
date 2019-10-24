package com.example.service.system;

import java.util.List;

import com.example.domain.system.SysUserRole;

public interface SysUserRoleService{

	void batchSaveUserRole(List<SysUserRole> list);
	List<Long> listRoleIdsByUserId(Long userId);

}
