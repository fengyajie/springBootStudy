package com.example.dao.customerShiro;


import java.util.List;

import com.example.domain.system.SysUserRole;


public interface SysUserRoleMapper{

	List<Long> listRoleIdsByUserId(Long userId);

	int removeByUserId(Long userId);

	int removeByRoleId(Long roleId);

	int batchSaveUserRole(List<SysUserRole> list);

	int batchRemoveByUserId(Long[] ids);
}