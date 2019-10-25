package com.example.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.service.CustomerService;
import com.example.util.ApiResult;
import com.example.util.MD5Utils;

@Controller
public class LoginController {
	
	@Autowired
	private CustomerService  customerService;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	
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
	
	
	/*
	 * @Log("请求访问主页")
	 * 
	 * @GetMapping({ "/index" }) String index(Model model, @CurrentUser UserInfo
	 * userInfo) { SysMenuRemoteService sysMenuRemoteService =
	 * ApplicationContextRegister.getBean(SysMenuRemoteService.class); List<Tree>
	 * menus = sysMenuRemoteService.listMenuTree(userInfo.getUserId());
	 * model.addAttribute("menus", menus); model.addAttribute("name",
	 * userInfo.getUsername()); String picUrl = userInfo.getPicurl();
	 * model.addAttribute("picUrl",
	 * StringUtils.isNotBlank(picUrl)?picUrl:"/img/photo_s.jpg");
	 * model.addAttribute("username", userInfo.getUsername()); return "index"; }
	 */
	
}
