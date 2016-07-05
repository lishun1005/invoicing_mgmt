package com.lishun.im.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lishun.im.bean.ImCompany;
import com.lishun.im.bean.ImWarehouse;

public interface ImCompanyDao{
	public List<ImCompany> queryList(@Param("rows")Integer rows, 
			@Param("pageNo")Integer pageNo,@Param("keyword")String keyword);
	public Long queryListCount(@Param("keyword")String keyword); 
	public int insert(ImCompany imWarehouse);
	public int update(ImCompany imWarehouse);
	public int delete(String id);
	public ImCompany queryImCompanyByName(@Param("name")String name);
}
