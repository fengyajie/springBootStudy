package com.example.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.example.dao.system.SysRoleMenuMapper;
import com.example.domain.system.SysRoleMenu;
import com.example.service.system.SysRoleMenuService;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

	@Autowired
    private SysRoleMenuMapper roleMenuMapper;
	
	@Override
	public List<Long> listMenuIdByRoleId(Long roleId) {
		return roleMenuMapper.listMenuIdByRoleId(roleId);
	}

	@Override
	@CacheEvict
	public int removeByRoleId(Long roleId) {
		return roleMenuMapper.removeByRoleId(roleId);
	}

	@Override
	@CacheEvict
	public int removeByMenuId(Long menuId) {
		return roleMenuMapper.removeByMenuId(menuId);
	}

	@Override
	public int batchSaveRoleMenu(List<SysRoleMenu> list) {
		return roleMenuMapper.batchSaveRoleMenu(list);
	}

}
