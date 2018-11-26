package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.configuration.annotation.SystemControllerLog;

@RestController
@RequestMapping("/aop")
public class AopController {

	@SystemControllerLog(LogAction="查询",ModuleID=12,LogContent="测试aop示例")
	@RequestMapping("/hello")
	public String hello(){
		return "hello";
	}
}
