package com.example.service.system.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.example.dao.system.SysMenuMapper;
import com.example.service.system.SysMenuService;


@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class SysMenuServiceImpl implements SysMenuService {
	
	@Autowired
	private SysMenuMapper menuMapper;
	
	public static final String SYS_MENU_CACHE_KEY = "sys_menu_cache_key_report_boss";
	public static final String SYS_MENU_CACHE_ALL_KEY = "sys_menu_cache_all_key_report_boss";
	
	@Cacheable
	@Override
	public Set<String> listPerms(Long userId) {
		List<String> perms = menuMapper.listUserPerms(userId);
		Set<String> permsSet = new HashSet<>();
		for (String perm : perms) {
			if (!StringUtils.isEmpty(perm)) {
				permsSet.addAll(Arrays.asList(perm.trim().split(",")));
			}
		}
		return permsSet;
	}
}
