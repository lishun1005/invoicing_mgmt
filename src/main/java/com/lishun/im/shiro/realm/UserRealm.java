package com.lishun.im.shiro.realm;


import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import com.lishun.im.bean.SysUser;
import com.lishun.im.service.SysUserManageService;
import com.lishun.im.shiro.utils.PasswordHelper;


/**
 * <p>
 * SysUser: xiangsx
 * <p>
 * Date: 2015-03-17
 * <p>
 * Version: 1.0
 */
public class UserRealm extends AuthorizingRealm {

	@Autowired
	@Qualifier("passwordHelper")
	private PasswordHelper passwordHelper;

	@Value("#{propertiesReader['login.username']}")
	private String loginUsername;

	@Value("#{propertiesReader['login.passwd']}")
	private String loginPasswd;
	
	@Autowired
	private SysUserManageService sysUserManageService;

	protected final org.slf4j.Logger logger = LoggerFactory
			.getLogger(Logger.class);

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		try {

			String username = (String) principals.getPrimaryPrincipal();
			Set<String> userRole = sysUserManageService.findRoles(username);
			authorizationInfo.setRoles(userRole);
			authorizationInfo.setStringPermissions(sysUserManageService
					.findPermissions(username));
		} catch (Exception e) {
			logger.error("后台验证权限过程出错！ "+e.getMessage());
		}
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		SimpleAuthenticationInfo authenticationInfo = null;
		try {

			String username = (String) token.getPrincipal();
			SysUser userDto = sysUserManageService.findByUserName(username);
			
			if (userDto == null) {
				throw new UnknownAccountException();// 没找到帐号
			}
			if (Boolean.TRUE.equals(userDto.getLocked())) {
				throw new LockedAccountException(); // 帐号锁定
			}
			
			// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
			authenticationInfo = new SimpleAuthenticationInfo(
					userDto.getUsername(), // 用户名
					userDto.getPassword(), // 密码
					ByteSource.Util.bytes(userDto.getUsername()
							+ userDto.getSalt()),// salt=username+salt
					getName() // realm name
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return authenticationInfo;
		
	}
	@Override
	protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
		try {
			super.assertCredentialsMatch(token, info);
		} catch (Exception e) {
			if(e instanceof ExcessiveAttemptsException){
				 throw new IncorrectCredentialsException("输入密码错误超过10次,用户锁定1小时");
			}else{
				throw new AuthenticationException("登录失败,请检查用户名密码");
			}
		}
	};

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}

	/*public void setSysUserManageService(
			SysUserManageService sysUserManageService) {
		this.sysUserManageService = sysUserManageService;
	}*/

}
