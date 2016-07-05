package com.lishun.im.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lishun.im.bean.ImCategory;
import com.lishun.im.bean.SysUser;

public interface ImCategoryDao{
	public List<ImCategory> queryList(@Param("rows")Integer rows, 
			@Param("pageNo")Integer pageNo,@Param("keyword")String keyword);
	public Long queryListCount(@Param("keyword")String keyword); 
	public int insert(ImCategory imCategory);
	public int update(ImCategory imCategory);
	public int delete(String id);
	public ImCategory queryCategoryByName(@Param("categoryName")String categoryName);
}
