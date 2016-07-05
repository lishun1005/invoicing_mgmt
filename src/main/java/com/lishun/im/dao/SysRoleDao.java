package com.lishun.im.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lishun.im.bean.SysRole;
import com.lishun.im.bean.SysUser;

public interface SysRoleDao{
	public List<Map<String,Object>> findPermissionIdsByRoleId(String sysRoleId);
	
	public List<SysUser> querySysRoleListMap(@Param("rows")Integer rows, 
			@Param("pageNo")Integer pageNo,@Param("keyword")String keyword);
	public Long querySysRoleListMapCount(@Param("keyword")String keyword); 
	public int save(SysRole sysRole);
	public int delete(String sysRoleId);
}
