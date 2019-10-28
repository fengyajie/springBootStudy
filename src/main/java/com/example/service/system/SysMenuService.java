package com.example.service.system;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.domain.Tree;

@Service
public interface SysMenuService{

	Set<String> listPerms(Long userId);
	
	List<Tree> listMenuTree(Long userId);
	
}
