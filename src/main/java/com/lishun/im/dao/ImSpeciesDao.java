package com.lishun.im.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lishun.im.bean.ImCategory;
import com.lishun.im.bean.ImSpecies;

public interface ImSpeciesDao{
	public List<Map<String,Object>> queryList(@Param("rows")Integer rows, 
			@Param("pageNo")Integer pageNo,@Param("keyword")String keyword);
	public Long queryListCount(@Param("keyword")String keyword); 
	public int insert(ImSpecies imSpecies);
	public int update(ImSpecies imSpecies);
	public int delete(String id);
	public List<ImSpecies> queryListByCategoryId(@Param("categoryId")String categoryId);
	public ImSpecies queryImSpeciesByName(@Param("name")String name,@Param("categoryId")String categoryId);
}
