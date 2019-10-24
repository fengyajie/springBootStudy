package com.example.service.system.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.system.SysUserMapper;
import com.example.domain.system.SysUser;
import com.example.service.system.SysRoleService;
import com.example.service.system.SysUserService;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserMapper userMapper;
	
	@Autowired
	private SysRoleService roleService;

	private static final Logger logger = LoggerFactory.getLogger(SysUserService.class);


	@Override
	public boolean exitUser(String username) {
		SysUser query = new SysUser();
		query.setUsername(username);
		List<SysUser> sysUsers = userMapper.selectList(query);
		if (sysUsers != null && sysUsers.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	@Cacheable(value = "usercachereportboss")
	public SysUser getSysUser(Long userId) {
		List<Long> roleIds = roleService.listRoleIdsByUserId(userId);
		SysUser user = userMapper.selectByKey(userId);
		user.setRoleIds(roleIds);
		return user;
	}

	@Override
	public SysUser findByName(String username) {SysUser query = new SysUser();
	query.setUsername(username);
	List<SysUser> sysUser = userMapper.selectList(query);
	if(CollectionUtils.isNotEmpty(sysUser)) {
		return sysUser.get(0);
	}
	return null;}
}
