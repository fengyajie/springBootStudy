package com.example.configuration.shiro.realm;

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
import org.springframework.stereotype.Component;

import com.example.dao.system.SysUserMapper;
import com.example.domain.Customer;
import com.example.domain.UserInfo;
import com.example.domain.system.SysUser;
import com.example.service.system.SysMenuService;
import com.example.util.ApplicationContextRegister;

/**
 * 认证
 * @author fyj
 *
 */
@Component
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
		
		SysUserMapper sysUserMapper = ApplicationContextRegister.getBean(SysUserMapper.class);
		//查询用户信息
		SysUser sysUser = new SysUser();
		sysUser.setUsername(token.getUsername());
		sysUser = sysUserMapper.selectList(sysUser).get(0);
		if (!password.equals(sysUser.getPassword())) {// 密码错误
			throw new IncorrectCredentialsException("用户名或密码错误！");
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(sysUser, password, getName());
		
		return info;
	}

}
