package com.example.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.customerShiro.SysUserRoleMapper;
import com.example.domain.system.SysUserRole;
import com.example.service.system.SysUserRoleService;


@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

	@Autowired
    private SysUserRoleMapper sysUserRoleMapper;
	
	@Override
	public void batchSaveUserRole(List<SysUserRole> list) {
		sysUserRoleMapper.batchSaveUserRole(list);
	}

	@Override
	public List<Long> listRoleIdsByUserId(Long userId) {
		return sysUserRoleMapper.listRoleIdsByUserId(userId);
	}

}
