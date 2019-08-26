package com.example.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Customer;
import com.example.domain.Result;
import com.example.domain.Result.Status;
import com.example.service.CustomerService;

@RestController
@RequestMapping("/api/user")
public class LoginController {
	
	@Autowired
	private CustomerService  customerService;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	
	@RequestMapping("/login")
	public Result login(HttpServletRequest request, String account, String password) {
		Customer customer = new Customer();
		customer.setCustomerName(account);
		customer.setPassword(password);
		Customer user = customerService.findUserByAccountAndPassword(customer);
		
		if(null != user) {
			HttpSession session = request.getSession();
			session.setAttribute("userId", user.getId());
			redisTemplate.opsForValue().set("userId"+user.getId(), session.getId());
			return new Result(Status.ok,"登录成功");
		}
		return new Result(Status.error,"登录失败");
	}
	

}
