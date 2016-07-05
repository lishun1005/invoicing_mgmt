package com.lishun.im.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lishun.im.bean.ImEmployeeSale;

public interface ImEmployeeSaleDao{
	
	public List<Map<String,Object>> queryList(@Param("rows")Integer rows,
			@Param("pageNo")Integer pageNo,@Param("keyword")String keyword,
			@Param("beginTime")String beginTime,@Param("endTime")String endTime);
	public Long queryListCount(@Param("keyword")String keyword,
			@Param("beginTime")String beginTime,@Param("endTime")String endTime);
	
	public int insert(ImEmployeeSale imEmployeeSale);
	public int update(ImEmployeeSale imEmployeeSale);
	
}
