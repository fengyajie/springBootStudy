package com.example.service.system;

import org.springframework.stereotype.Service;

import com.example.domain.system.SysUser;


@Service
public interface SysUserService{

	SysUser getSysUser(Long userId);
	
	boolean exitUser(String username);
	
	SysUser findByName(String username);
}
