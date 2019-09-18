package com.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.springboot.dubbo.service.DemoService;

@Service
public class DemoServiceImpl implements DemoService {

	@Override
	public String sayHello(String name) {
		System.out.println("name="+name);
		return null;
	}

}
