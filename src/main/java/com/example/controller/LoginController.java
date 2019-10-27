package com.example.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.configuration.annotation.CurrentUser;
import com.example.domain.UserInfo;
import com.example.service.system.SysMenuService;
import com.example.util.ApiResult;
import com.example.util.ApplicationContextRegister;
import com.example.util.MD5Utils;

import sun.reflect.generics.tree.Tree;

@Controller
public class LoginController {
	
	/**
	 * @RestController不会返回视图，只会返回数据
	 * @return
	 */
	@RequestMapping("/loginView")
	public String loginView() {
		return "login";
	}
	
	
	@PostMapping("/login")
	@ResponseBody
	ApiResult ajaxLogin(String username, String password) {
		password = MD5Utils.encrypt(username, password);
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			return ApiResult.ok();
		} catch (UnknownAccountException e) {
			return new ApiResult(ApiResult.Status.ERROR, e.getMessage());
		} catch (IncorrectCredentialsException e) {
			return new ApiResult(ApiResult.Status.ERROR, e.getMessage());
		} catch (LockedAccountException e) {
			return new ApiResult(ApiResult.Status.ERROR, e.getMessage());
		} catch (AuthenticationException e) {
			return new ApiResult(ApiResult.Status.ERROR, "认证失败！");
		}
	}
	
	
	@GetMapping({ "/index" })
	String index(Model model, @CurrentUser UserInfo userInfo) {
		SysMenuService sysMenuService = ApplicationContextRegister.getBean(SysMenuService.class);
		List<Tree> menus = sysMenuService.listMenuTree(userInfo.getUserId());
		model.addAttribute("menus", menus);
		model.addAttribute("name", userInfo.getUsername());
		String picUrl = userInfo.getPicurl();
		model.addAttribute("picUrl", StringUtils.isEmpty(picUrl) ? picUrl : "/img/photo_s.jpg");
		model.addAttribute("username", userInfo.getUsername());
		return "index";
	}
	
}
