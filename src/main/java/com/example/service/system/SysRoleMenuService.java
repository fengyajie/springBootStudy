package com.example.service.system;


import java.util.List;

import com.example.domain.system.SysRoleMenu;

public interface SysRoleMenuService{

	List<Long> listMenuIdByRoleId(Long roleId);
	
	int removeByRoleId(Long roleId);

	int removeByMenuId(Long menuId);
	
	int batchSaveRoleMenu(List<SysRoleMenu> list);
}
