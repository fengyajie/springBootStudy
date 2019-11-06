package com.example.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import com.example.domain.UserInfo;
import com.example.domain.system.SysUser;
import com.example.service.system.SysUserService;
import com.example.util.ApplicationContextRegister;

public class SysUserFilter extends AccessControlFilter {

	/**
	 * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		SysUser sysUser = (SysUser) request.getAttribute("user");
		if(null == sysUser) {
			return true;
		}
		if(sysUser.getStatus() == 0) {
			getSubject(request, response).logout();
			saveRequestAndRedirectToLogin(request, response);
			return false;
		}
		return true;
	}

	/**
	 * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		getSubject(request, response).logout();
		saveRequestAndRedirectToLogin(request, response);
		return true;
	}

	/**
	 * 会自动调用这两个方法决定是否继续处理
	 */
	@Override
	public boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request,response);
		if(subject == null) {
			return true;
		}
		SysUser sysUser = (SysUser) subject.getPrincipal();
		if(null == sysUser) {
			return true;
		}
		
		SysUserService sysUserService = ApplicationContextRegister.getBean(SysUserService.class);
		SysUser sysUserInfo = sysUserService.findByName(sysUser.getUsername());
		UserInfo target = new UserInfo();
		target.setUserId(sysUserInfo.getUserId());
		target.setUsername(sysUserInfo.getUsername());
		target.setPassword(sysUserInfo.getPassword());
		request.setAttribute("user", target);
		return true;
	}
}
