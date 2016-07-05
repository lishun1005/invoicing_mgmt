package com.lishun.im.service.imp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lishun.im.bean.SysPermissions;
import com.lishun.im.bean.SysRole;
import com.lishun.im.bean.SysUser;
import com.lishun.im.dao.SysPermissionsDao;
import com.lishun.im.dao.SysRoleDao;
import com.lishun.im.dao.SysUserDao;
import com.lishun.im.service.SysUserManageService;
import com.lishun.im.shiro.utils.PasswordHelper;

@Service
@Transactional
public class SysUserManageServiceImpl extends BaseService implements SysUserManageService {
	
	@Autowired
	SysUserDao sysUserDao;
	
	@Autowired
	SysRoleDao sysRoleDao;
	
	@Autowired
	SysPermissionsDao sysPermissionsDao;
	
	
	@Autowired
	@Qualifier("passwordHelper")
	private PasswordHelper passwordHelper;
	@Override
	public SysUser findByUserName(String sysUsername) {
		return sysUserDao.findByUserName(sysUsername);
	}
	@Override
	public Set<String> findRoles(String SysUsername) {
		List<Map<String, Object>> result=sysUserDao.findRoles(SysUsername);
		Set<String> set = new HashSet<String>();
		for (Map<String, Object> row : result) {
			set.add(row.get("role").toString());
		}
		return set;

	}
	@Override
	public Set<String> findPermissions(String SysUsername) {
		List<Map<String, Object>> result =sysUserDao.findPermissions(SysUsername);
		Set<String> set = new HashSet<String>();
		for (Map<String, Object> row : result) {
			set.add(row.get("permission").toString());
		}
		return set;
	}
	
	@Override
	public Set<String> findRoleIds(String sysUsername) {
		List<Map<String, Object>> result = sysUserDao.findRoleIds(sysUsername);
		Set<String> roleIds = new HashSet<String>();
		for (Map<String, Object> role : result) {
			roleIds.add((String) role.get("id"));
		}
		return roleIds;
	}

	
	@Override
	public Set<String> findPermissionIdsByRoleId(String sysRoleId) {
		
		List<Map<String, Object>> result = sysRoleDao.findPermissionIdsByRoleId(sysRoleId);
		Set<String> rermissionIds = new HashSet<String>();
		for (Map<String, Object> permission : result) {
			rermissionIds.add((String) permission.get("permission_id"));
		}
		return rermissionIds;
	}

	@Override
	public Map<String, Object> querySysUserByPage(Integer rows, Integer pageNo,
			String keyword) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<SysUser> list=sysUserDao.querySysUserByPage(rows, pageNo-1, keyword);
		result.put("list",list);
		result.put("total", sysUserDao.querySysUserByPageCount(keyword));
		return result;
	}

	@Override
	public int createUser(SysUser userDto) {
		if (userDto.getId() == null || StringUtils.isBlank(userDto.getId())) {
			userDto.setId(UUID.randomUUID().toString());
		}
		return sysUserDao.createUser(userDto);
	}
	@Override
	public Integer updateUser(SysUser userDto) {
		String sql = "UPDATE im_sys_user " + "SET  email=?, locked=?,realname=? "
				+ " WHERE id=?;";
		Integer result = getJdbcTemplate().update(
				sql,
				new Object[] { userDto.getEmail(), userDto.getLocked()
						,userDto.getRealname(),userDto.getId() });
		return result;
	}

	@Override
	public Integer deleteUser(String sysUserId) {
		try {
			// 先删除角色关联表
			String sql = "DELETE FROM im_sys_user_role WHERE user_id='"
					+ sysUserId + "'";
			getJdbcTemplate().execute(sql);
			// 再删除用户表
			sysUserDao.delete(sysUserId);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	@Override
	public Map<String, Object> querySysRoleListMap(Integer rows,
			Integer pageNo, String keyword) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<SysUser> list=sysRoleDao.querySysRoleListMap(rows, pageNo-1, keyword);
		result.put("list",list);
		result.put("total", sysRoleDao.querySysRoleListMapCount(keyword));
		return result;
	}

	@Override
	public Map<String, Object> querySysPermissionListMap(Integer rows,
			Integer pageNo, String keyword) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<SysPermissions> list=sysPermissionsDao.querySysPermissionListMap(rows, pageNo-1, keyword);
		result.put("list",list);
		result.put("total", sysPermissionsDao.querySysPermissionListMapCount(keyword));
		return result;
	}

	@Override
	public int createRole(SysRole sysRoleDto) {
		if (sysRoleDto.getId() == null
				|| StringUtils.isBlank(sysRoleDto.getId())) {

			sysRoleDto.setId(UUID.randomUUID().toString());
		}
		return sysRoleDao.save(sysRoleDto);
	}

	@Override
	public Integer updateRole(SysRole sysRoleDto) {

		String sql = "UPDATE im_sys_role " + "SET  role=?, description=?,"
				+ "available=?" + " WHERE id=?;";
		Integer result =getJdbcTemplate().update(
				sql,
				new Object[] { sysRoleDto.getRole(),
						sysRoleDto.getDescription(), sysRoleDto.getAvailable(),
						sysRoleDto.getId() });
		return result;
	}

	@Transactional
	@Override
	public Integer deleteRole(String sysRoleId) {

		try {
			// 1. 先删除用户-角色关联表
			String sql = "DELETE FROM im_sys_user_role WHERE role_id='"
					+ sysRoleId + "'";
			getJdbcTemplate().execute(sql);

			// 2.再删除角色-权限关联表
			sql = "DELETE FROM im_sys_role_permission WHERE role_id='"
					+ sysRoleId + "'";
			getJdbcTemplate().execute(sql);

			// 3.删除角色表
			sysRoleDao.delete(sysRoleId);
		} catch (Exception e) {
			return -1;
		}
		return 1;

	}

	@Override
	public Integer createPermission(
			SysPermissions sysPermissionsDto) {

		if (sysPermissionsDto.getId() == null
				|| StringUtils.isBlank(sysPermissionsDto.getId())) {
			sysPermissionsDto.setId(UUID.randomUUID().toString());
		}
		return sysPermissionsDao.save(sysPermissionsDto);
	}

	@Override
	public Integer updatePermission(
			SysPermissions sysPermissionsDto) {

		String sql = "UPDATE im_sys_permissions "
				+ "SET  permission=?, description=?," + "available=?"
				+ " WHERE id=?;";
		Integer result = getJdbcTemplate().update(
				sql,
				new Object[] { sysPermissionsDto.getPermission(),
						sysPermissionsDto.getDescription(),
						sysPermissionsDto.getAvailable(),
						sysPermissionsDto.getId() });
		return result;
	}

	@Override
	public Integer deletePermission(String sysPermissionId) {

		try {
			// 1. 先删除用户-角色关联表
			String sql = "DELETE FROM im_sys_role_permission WHERE permission_id='"
					+ sysPermissionId + "'";
			getJdbcTemplate().execute(sql);
			// 2.删除权限表
			sysPermissionsDao.delete(sysPermissionId);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	@Override
	public void correlationRoles(String SysUserId, String... roleIds) {

		if (roleIds == null || roleIds.length == 0) {
			return;
		}
		String sql = "insert into im_sys_user_role(user_id , role_id) values(?,?)";
		for (String roleId : roleIds) {
			if (!exists_UserRoles(SysUserId, roleId)) {
				getJdbcTemplate().update(sql,
						new Object[] { SysUserId, roleId });
			}
		}

	}

	@Override
	public void uncorrelationRoles(String SysUserId, String... roleIds) {
		if (roleIds == null || roleIds.length == 0) {
			return;
		}
		String sql = "delete from im_sys_user_role where user_id=? and role_id=?";
		for (String roleId : roleIds) {
			if (exists_UserRoles(SysUserId, roleId)) {
				getJdbcTemplate().update(sql,
						new Object[] { SysUserId, roleId });
			}
		}
	}

	private Boolean exists_UserRoles(String SysUserId, String roleId) {
		String sql = "select * from im_sys_user_role where user_id=? and role_id=?";

		List<Map<String, Object>> list = getJdbcTemplate()
				.queryForList(sql, new Object[] { SysUserId, roleId });

		if (list.size() == 0 || list == null) {
			return false;
		}

		return true;
	}

	@Override
	public void correlationPermissions(String roleId, String... permissionIds) {
		if (permissionIds == null || permissionIds.length == 0) {
			return;
		}
		String sql = "insert into im_sys_role_permission(permission_id , role_id) values(?,?)";
		for (String permissionId : permissionIds) {
			if (!exists_RolePermissions(roleId, permissionId)) {
				getJdbcTemplate().update(sql,
						new Object[] { permissionId, roleId });
			}
		}
	}

	@Override
	public void uncorrelationPermissions(String roleId, String... permissionIds) {
		if (permissionIds == null || permissionIds.length == 0) {
			return;
		}
		String sql = "delete from im_sys_role_permission where permission_id=? and role_id=?";
		for (String permissionId : permissionIds) {
			if (exists_RolePermissions(roleId, permissionId)) {
				getJdbcTemplate().update(sql,
						new Object[] { permissionId, roleId });
			}
		}

	}

	private Boolean exists_RolePermissions(String roleId, String permissionId) {

		String sql = "select * from im_sys_role_permission where permission_id=? and role_id=?";
		List<Map<String, Object>> list = getJdbcTemplate()
				.queryForList(sql, new Object[] { permissionId, roleId });
		if (list.size() == 0 || list == null) {
			return false;
		}
		return true;

	}
	@Override
	public void changePassword(SysUser user) {
		try {
			passwordHelper.encryptPassword(user);
			sysUserDao.updateById(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
