package com.example.service.system;

import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public interface SysMenuService{

	Set<String> listPerms(Long userId);
	
}
