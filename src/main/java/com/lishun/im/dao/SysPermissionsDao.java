package com.lishun.im.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lishun.im.bean.SysPermissions;
import com.lishun.im.bean.SysUser;

public interface SysPermissionsDao{

	public List<SysPermissions> querySysPermissionListMap(@Param("rows")Integer rows, 
			@Param("pageNo")Integer pageNo,@Param("keyword")String keyword);
	public Long querySysPermissionListMapCount(@Param("keyword")String keyword); 
	public int save(SysPermissions sysPermissions);
	public int delete(String sysPermissionId);
	public List<SysPermissions> getSysPermissionsByPermission(@Param("permission")String permission);
}
