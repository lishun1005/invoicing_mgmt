package com.lishun.im.dao;

import java.util.List;
import java.util.Map;




import org.apache.ibatis.annotations.Param;

import com.lishun.im.bean.SysUser;

public interface SysUserDao{
	public SysUser findByUserName(String sysUsername);
	public List<Map<String,Object>> findRoles(String sysUsername);
	public List<Map<String,Object>> findPermissions(String sysUsername);
	public List<Map<String,Object>> findRoleIds(String sysUsername);
	public List<SysUser> querySysUserByPage(@Param("rows")Integer rows, 
			@Param("pageNo")Integer pageNo,@Param("keyword")String keyword);
	public Long querySysUserByPageCount(@Param("keyword")String keyword); 
	public int delete(String sysUserId);
	public int createUser(SysUser userDto);
	public int updateById(SysUser userDto);
	
}
