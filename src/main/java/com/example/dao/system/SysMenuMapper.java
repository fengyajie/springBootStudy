package com.example.dao.system;


import java.util.List;

import com.example.domain.system.SysMenu;


public interface SysMenuMapper {
	
	List<SysMenu> listMenuByUserId(Long userId);
	
	List<String> listUserPerms(Long userId);
}