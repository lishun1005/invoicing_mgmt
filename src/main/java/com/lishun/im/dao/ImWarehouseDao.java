package com.lishun.im.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lishun.im.bean.ImWarehouse;
import com.lishun.im.bean.SysUser;

public interface ImWarehouseDao{
	public List<ImWarehouse> queryList(@Param("rows")Integer rows, 
			@Param("pageNo")Integer pageNo,@Param("keyword")String keyword);
	public Long queryListCount(@Param("keyword")String keyword); 
	public int insert(ImWarehouse imWarehouse);
	public int update(ImWarehouse imWarehouse);
	public int delete(String id);
	public ImWarehouse queryImWarehouseByName(@Param("name")String name);
}
