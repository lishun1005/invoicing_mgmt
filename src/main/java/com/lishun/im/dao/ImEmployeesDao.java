package com.lishun.im.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lishun.im.bean.ImEmployees;
import com.lishun.im.bean.ImSpecies;

public interface ImEmployeesDao{
	public List<Map<String,Object>> queryList(@Param("rows")Integer rows, 
			@Param("pageNo")Integer pageNo,@Param("keyword")String keyword);
	public Long queryListCount(@Param("keyword")String keyword); 
	public int insert(ImEmployees imEmployees);
	public int update(ImEmployees imEmployees);
	public int delete(String id);
	public List<ImEmployees> queryListByCompanyId(@Param("companyId")String companyId);
	/*public ImCategory queryCategoryByName(@Param("categoryName")String categoryName);*/
}
