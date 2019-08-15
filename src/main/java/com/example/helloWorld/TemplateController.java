package com.example.helloWorld;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.util.RedisUtil;

@Controller
public class TemplateController {

	@Autowired
	private RedisUtil redisUtil;
	
	@RequestMapping("/helloHtmlTem")
	public String template(Map<String,Object> map) {
		System.out.println("........");
		return "hello";
	}
	
	@RequestMapping("/redisTest")
	public void redisTest() {
		redisUtil.set("k1", "v1");
		
		System.out.println(redisUtil.get("k1"));
	}
	
	@RequestMapping("/uid")
	public String uid(HttpSession session) {
		UUID uuid = (UUID) session.getAttribute("uid");
		if(null == uuid) {
			uuid = UUID.randomUUID();
		}
		session.setAttribute("uid", uuid);
		return session.getId();
	}
}
