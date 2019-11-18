package com.example.service.system.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.customerShiro.SysRoleMapper;
import com.example.domain.system.SysRole;
import com.example.domain.system.SysRoleMenu;
import com.example.service.system.SysRoleMenuService;
import com.example.service.system.SysRoleService;
import com.example.service.system.SysUserRoleService;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    public static final String ROLE_ALL_KEY = "\"role_all\"";

    public static final String DEMO_CACHE_NAME = "role";
    @Autowired
    private SysRoleMapper sysRoleMapper;
    
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public List<SysRole> selectUserRoleList(Long userId) {
        List<SysRole> roles = sysRoleMapper.selectList();
        List<Long> rolesIds = listRoleIdsByUserId(userId);
        for (SysRole sysRole : roles) {
        	sysRole.setRoleSign("false");
            for (Long roleId : rolesIds) {
                if (Objects.equals(sysRole.getRoleId(), roleId)) {
                	sysRole.setRoleSign("true");
                    break;
                }
            }
        }
        return roles;
    }

	@Override
	public List<Long> listMenuIdByRoleId(Long roleId) {
		return sysRoleMenuService.listMenuIdByRoleId(roleId);
	}

	@Override
	public List<Long> listRoleIdsByUserId(Long userId) {
		return sysUserRoleService.listRoleIdsByUserId(userId);
	}
	

	@Override
	public List<String> findRoleDesc(List<Long> roleIds) {
		return sysRoleMapper.findRoleDesc(roleIds);
	}


}
