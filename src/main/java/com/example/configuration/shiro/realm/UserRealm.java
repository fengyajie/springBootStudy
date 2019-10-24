package com.example.configuration.shiro.realm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.example.domain.Customer;
import com.example.domain.UserInfo;
import com.example.service.system.SysMenuService;
import com.example.util.ApplicationContextRegister;

/**
 * 认证
 * @author fyj
 *
 */
public class UserRealm extends AuthorizingRealm {

	/**
	 * 授权（验证权限时调用）
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		UserInfo userinfo = (UserInfo) principals.getPrimaryPrincipal();
		
		SysMenuService sysMenuService = ApplicationContextRegister.getBean(SysMenuService.class);
		Set<String> perms = sysMenuService.listPerms(userinfo.getUserId());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(perms);
		return info;
	}

	/**
	 * 认证（登录时调用）
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
		String password = new String((char[]) token.getCredentials());
		//查询用户信息
		Customer user = null;//sysUserDao.selectOne(token.getUsername());
		if (!password.equals(user.getPassword())) {// 密码错误
			throw new IncorrectCredentialsException("用户名或密码错误！");
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
		
		return info;
	}

}
