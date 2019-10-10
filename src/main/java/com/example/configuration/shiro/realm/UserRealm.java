package com.example.configuration.shiro.realm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.dao.customerShiro.CustomerDao;
import com.example.domain.Customer;

/**
 * 认证
 * @author fyj
 *
 */
@Component
public class UserRealm extends AuthorizingRealm {

	@Autowired
	private CustomerDao customerDao;
	
	/**
	 * 授权（验证权限时调用）
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Customer customer = (Customer) principals.getPrimaryPrincipal();
		Integer id = customer.getId();
		//用户权限列表
		Set<String> permsSet = new HashSet<>();
		//根据用户id查询出权限
		String perms = "";
		permsSet.addAll(Arrays.asList(perms.trim().split(",")));
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permsSet);
		return info;
	}

	/**
	 * 认证（登录时调用）
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
		//查询用户信息
		Customer user = null;//sysUserDao.selectOne(token.getUsername());
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
		return info;
	}

}
