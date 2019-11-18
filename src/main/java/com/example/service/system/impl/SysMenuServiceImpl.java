package com.example.service.system.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.example.dao.customerShiro.SysMenuMapper;
import com.example.domain.Tree;
import com.example.domain.system.SysMenu;
import com.example.service.system.SysMenuService;
import com.example.util.BuildTree;


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

	@Override
	public List<Tree> listMenuTree(Long userId) {
		List<Tree> trees = listTreeByUserId(userId);
		List<Tree> list = BuildTree.buildList(trees, "0");
		return list;
	}
	
	private List<Tree> listTreeByUserId(Long userId){
		List<Tree> trees = new ArrayList<Tree>();
		List<SysMenu> sysMenus = menuMapper.listMenuByUserId(userId);
		for (SysMenu sysMenu : sysMenus) {
			Tree tree = new Tree();
			tree.setId(sysMenu.getMenuId().toString());
			tree.setParentId(sysMenu.getParentId().toString());
			tree.setText(sysMenu.getMenuName());
			Map<String, Object> attributes = new HashMap<>(16);
			attributes.put("url", sysMenu.getUrl());
			attributes.put("icon", sysMenu.getIcon());
			tree.setAttributes(attributes);
			trees.add(tree);
		}
		return trees;
	}
}
