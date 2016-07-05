package com.lishun.im.service;

import java.util.Map;
import java.util.Set;

import com.lishun.im.bean.SysPermissions;
import com.lishun.im.bean.SysRole;
import com.lishun.im.bean.SysUser;

public interface SysUserManageService {
	/**
	 * 
	 * description:根据用户名查找其角色
	 * @author: Wilson Lai
	 * @date 2015年10月20日
	 *  @param SysUsername
	 *  @return
	 *  Set<String>
	 */
	Set<String> findRoles(String SysUsername);
	
	/**
	 * description:根据用户名查找其权限
	 * @param SysUsername
	 * @return Set<String>
	 */
	Set<String> findPermissions(String SysUsername);
	
	/**
	 * description:根据用户名查找用户
	 * @param SysUsername
	 * @return SysUser
	 */
	SysUser findByUserName(String SysUsername);
	
	/**
	 * 
	 * description:后台用户-分页查询
	 * @author: Wilson Lai
	 * @date 2015年10月22日
	 *  @param rows
	 *  @param pageNo
	 *  @param keyword
	 *  @return
	 *  Map<String,Object>
	 */
	Map<String, Object> querySysUserByPage(Integer rows, Integer pageNo,
			String keyword);
	
	/**
	 * 
	 * description:后台用户-新增
	 * @author: Wilson Lai
	 * @date 2015年10月22日
	 *  @param user
	 *  @return
	 *  SysUser
	 */
	int createUser(SysUser user);
	
	/**
	 * 
	 * description:后台用户-更新
	 * @author: Wilson Lai
	 * @date 2015年10月22日
	 * @param user
	 * @return
	 *  SysUser
	 */
	Integer updateUser(SysUser user);
	/**
	 * 
	 * description:后台用户-删除（先删除用户与角色关联表）
	 * @author: Wilson Lai
	 * @date 2015年10月22日
	 *  @param sysUserId
	 *  @return
	 *  Integer
	 */
	Integer deleteUser(String sysUserId);
	/**
	 * 
	 * description:后台角色-分页查询
	 * @author: Wilson Lai
	 * @date 2015年10月22日
	 *  @param rows
	 *  @param pageNo
	 *  @param keyword
	 *  @return
	 *  Map<String,Object>
	 */
	Map<String, Object> querySysRoleListMap(Integer rows, Integer pageNo,
			String keyword);
	
	/**
	 * 
	 * description:后台权限-分页查询
	 * @author: Wilson Lai
	 * @date 2015年10月22日
	 *  @param rows
	 *  @param pageNo
	 *  @param keyword
	 *  @return
	 *  Map<String,Object>
	 */
	Map<String, Object> querySysPermissionListMap(Integer rows, Integer pageNo,
			String keyword);
	
	/**
	 * 
	 * description:后台角色管理-新增
	 * @author: Wilson Lai
	 * @date 2015年10月23日
	 *  @param sysRole
	 *  @return
	 *  SysRole
	 */
	int createRole(SysRole sysRole);
	/**
	 * 
	 * description:后台角色管理-更新
	 * @author: Wilson Lai
	 * @date 2015年10月23日
	 *  @param sysRole
	 *  @return
	 *  SysRole
	 */
	Integer updateRole(SysRole sysRole);
	/**
	 * 
	 * description:后台角色管理-删除（须删除与用户及权限的关联）
	 * @author: Wilson Lai
	 * @date 2015年10月23日
	 *  @param sysRoleId
	 *  @return
	 *  Integer
	 */
	Integer deleteRole(String sysRoleId);
	/**
	 * 
	 * description:后台权限管理-新增
	 * @author: Wilson Lai
	 * @date 2015年10月23日
	 *  @param sysPermissions
	 *  @return
	 *  SysPermissions
	 */
	Integer createPermission(SysPermissions sysPermissions);
	
	/**
	 * 
	 * description:后台权限管理-更新
	 * @author: Wilson Lai
	 * @date 2015年10月23日
	 *  @param sysPermissions
	 *  @return
	 *  SysPermissions
	 */
	Integer updatePermission(SysPermissions sysPermissions);
	/**
	 * 
	 * description:后台权限管理-删除
	 * @author: Wilson Lai
	 * @date 2015年10月23日
	 *  @param sysPermissionId
	 *  @return
	 *  Integer
	 */
	Integer deletePermission(String sysPermissionId);
	/**
	 * 
	 * description:根据用户名查找角色id数组
	 * @author: Wilson Lai
	 * @date 2015年10月23日
	 *  @param sysUsername
	 *  @return
	 *  Set<String>
	 */
	Set<String> findRoleIds(String sysUsername);
	/**
	 * 
	 * description:用户关联角色
	 * @author: Wilson Lai
	 * @date 2015年10月23日
	 *  @param SysUserId
	 *  @param roleIds
	 *  void
	 */
	void correlationRoles(String SysUserId, String[] roleIds);
	
	/**
	 * 
	 * description:删除 用户-角色关联
	 * @author: Wilson Lai
	 * @date 2015年10月23日
	 *  @param SysUserId
	 *  @param roleIds
	 *  void
	 */
	void uncorrelationRoles(String SysUserId, String[] roleIds);
	
	/**
	 * 
	 * description:根据角色id查询权限ids
	 * @author: Wilson Lai
	 * @date 2015年10月23日
	 *  @param sysRoleId
	 *  @return
	 *  Set<String>
	 */
	Set<String> findPermissionIdsByRoleId(String sysRoleId);
	/**
	 * 
	 * description:关联角色与权限(新建关联)
	 * @author: Wilson Lai
	 * @date 2015年10月23日
	 *  @param roleId
	 *  @param permissionIds
	 *  void
	 */
	void correlationPermissions(String roleId, String[] permissionIds);
	/**
	 * 
	 * description:关联角色与权限(删除关联)
	 * @author: Wilson Lai
	 * @date 2015年10月23日
	 *  @param roleId
	 *  @param permissionIds
	 *  void
	 */
	void uncorrelationPermissions(String roleId, String[] permissionIds);
	
	void changePassword(SysUser user);
}
