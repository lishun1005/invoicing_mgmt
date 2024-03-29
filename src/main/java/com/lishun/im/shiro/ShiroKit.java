package com.lishun.im.shiro;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * 将所有Shiro指令封装成HTTL的函数。
 * 
 * @author xsx
 */
public class ShiroKit {

	private static final String NAMES_DELIMETER = ",";

	/**
	 * 禁止初始化
	 */
	private ShiroKit() {
	}

	/**
	 * 获取 Subject
	 * 
	 * @return Subject
	 */
	protected static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	/**
	 * 验证当前用户是否属于该角色？,使用时与lacksRole 搭配使用
	 * 
	 * @param roleName
	 *            角色名
	 * @return 属于该角色：true，否则false
	 */
	public static boolean hasRole(String roleName) {
		return getSubject() != null && roleName != null
				&& roleName.length() > 0 && getSubject().hasRole(roleName);
	}

	/**
	 * 与hasRole标签逻辑相反，当用户不属于该角色时验证通过。
	 * 
	 * @param roleName
	 *            角色名
	 * @return 不属于该角色：true，否则false
	 */
	public static boolean lacksRole(String roleName) {
		return !hasRole(roleName);
	}

	/**
	 * 验证当前用户是否属于以下任意一个角色。
	 * 
	 * @param roleNames
	 *            角色列表
	 * @return 属于:true,否则false
	 */
	public static boolean hasAnyRoles(String roleNames) {
		boolean hasAnyRole = false;
		Subject subject = getSubject();
		if (subject != null && roleNames != null && roleNames.length() > 0) {
			// Iterate through roles and check to see if the user has one of the
			// roles
			for (String role : roleNames.split(NAMES_DELIMETER)) {
				if (subject.hasRole(role.trim())) {
					hasAnyRole = true;
					break;
				}
			}
		}
		return hasAnyRole;
	}

	/**
	 * 验证当前用户是否属于以下所有角色。
	 * 
	 * @param roleNames
	 *            角色列表
	 * @return 属于:true,否则false
	 */
	public static boolean hasAllRoles(String roleNames) {
		boolean hasAllRole = true;
		Subject subject = getSubject();
		if (subject != null && roleNames != null && roleNames.length() > 0) {
			// Iterate through roles and check to see if the user has one of the
			// roles
			for (String role : roleNames.split(NAMES_DELIMETER)) {
				if (!subject.hasRole(role.trim())) {
					hasAllRole = false;
					break;
				}
			}
		}
		return hasAllRole;
	}

	/**
	 * 验证当前用户是否拥有指定权限,使用时与lacksPermission 搭配使用
	 * 
	 * @param permission
	 *            ,使用逗号分开 权限名
	 * @return 拥有权限：true，否则false
	 */
	public static boolean hasAnyPermission(String... permissions) {
		Subject sub = getSubject();
		boolean permitted = sub != null && permissions != null
				&& permissions.length > 0;
		if (!permitted)
			return false;
		for (String permission : permissions) {
			getSubject().isPermitted(permissions);
			return true;
		}
		return false;
	}

	/**
	 * 验证当前用户是否拥有指定权限,使用时与lacksPermission 搭配使用
	 * 
	 * @param permission
	 *            权限名
	 * @return 拥有权限：true，否则false
	 */
	public static boolean hasPermission(String permission) {
		boolean ret = getSubject() != null && permission != null
				&& permission.length() > 0
				&& getSubject().isPermitted(permission);
		return ret;
	}

	/**
	 * 与hasPermission标签逻辑相反，当前用户没有制定权限时，验证通过。
	 * 
	 * @param permission
	 *            权限名
	 * @return 拥有权限：true，否则false
	 */
	public static boolean lacksPermission(String permission) {
		return !hasPermission(permission);
	}

	/**
	 * 已认证通过的用户。不包含已记住的用户，这是与user标签的区别所在。与notAuthenticated搭配使用
	 * 
	 * @return 通过身份验证：true，否则false
	 */
	public static boolean authenticated() {
		return getSubject() != null && getSubject().isAuthenticated();
	}

	/**
	 * 未认证通过用户，与authenticated标签相对应。与guest标签的区别是，该标签包含已记住用户。。
	 * 
	 * @return 没有通过身份验证：true，否则false
	 */
	public static boolean notAuthenticated() {
		return !authenticated();
	}

	/**
	 * 认证通过或已记住的用户。与guset搭配使用。
	 * 
	 * @return 用户：true，否则 false
	 */
	public static boolean user() {
		return getSubject() != null && getSubject().getPrincipal() != null;
	}

	/**
	 * 验证当前用户是否为“访客”，即未认证（包含未记住）的用户。用user搭配使用
	 * 
	 * @return 访客：true，否则false
	 */
	public static boolean guest() {
		return !user();
	}

	/**
	 * 输出当前用户信息，通常为登录帐号信息。
	 * 
	 * @return 当前用户信息
	 */
	public static String principal() {
		if (getSubject() != null) {
			// Get the principal to print out
			Object principal = getSubject().getPrincipal();
			if (principal != null) {
				return principal.toString();
			} else {
				return "";
			}
		}
		return "";
	}

	/*
	 * 把BigDecimal和Double金额格式化 s：BigDecimal类型的值，len：保留小数点长度
	 */
	public static String formatMoney(Object s, int len) {
		if (s != null) {
			Double num;
			if (s instanceof Double) {
				num = (Double) s;
			} else{
				BigDecimal b = (BigDecimal) s;
				num = b.doubleValue();
			}
			NumberFormat formater = null;

			if (len == 0) {
				formater = new DecimalFormat("###,###");

			} else {
				StringBuffer buff = new StringBuffer();
				buff.append("###,###.");
				for (int i = 0; i < len; i++) {
					buff.append("#");
				}
				formater = new DecimalFormat(buff.toString());
			}
			String result = formater.format(num);
			if (result.indexOf(".") == -1) {
				result = "¥" + result + ".00";
			} else {
				result = "¥" + result;
			}
			return result;
		} else {
			return "";
		}
	}
	public static String dateFormat(Object date,String pattern){
		SimpleDateFormat sdf=new SimpleDateFormat(pattern);
		if(date!=null){
			if(date instanceof Date){
				return sdf.format(date);
			}else if(date instanceof java.sql.Timestamp){
				java.sql.Timestamp timestamp=(java.sql.Timestamp)date;
				return sdf.format(new Date(timestamp.getTime()));
			}
		}
		return "";
	}
	
	

}