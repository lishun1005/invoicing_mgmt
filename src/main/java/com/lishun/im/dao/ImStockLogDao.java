package com.lishun.im.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized.Parameters;

import com.lishun.im.bean.ImStockLog;

public interface ImStockLogDao{
	/**
	* Description: 商品列表
	* @param rows 页容量
	* @param pageNo 页码
	* @param keyword 关键字
	* @param imWarehouseId 厂库id
	* @param beginTime 检索开始时间
	* @param endTime 检索结束时间
	* @return List<Map<String,Object>><br>
	* @author lishun 
	* @date 2016年6月3日 上午8:55:24
	 */
	public List<Map<String,Object>> queryList(@Param("rows")Integer rows,
			@Param("pageNo")Integer pageNo,@Param("keyword")String keyword,@Param("imWarehouseId")String imWarehouseId,
			@Param("beginTime")String beginTime,@Param("endTime")String endTime,@Param("operateAction")Integer operateAction);
	/**
	* Description: 获取列表总数
	* @param keyword 关键字
	* @param imWarehouseId 厂库id
	* @param beginTime 检索开始时间
	* @param endTime 检索结束时间
	* @author lishun 
	* @date 2016年6月3日 上午8:55:07
	 */
	public Long queryListCount(@Param("keyword")String keyword,@Param("imWarehouseId")String imWarehouseId,
			@Param("beginTime")String beginTime,@Param("endTime")String endTime,@Param("operateAction")Integer operateAction);
	/**
	* Description:添加商品 
	* @param imStock 商品bean
	* @return int<br>
	* @author lishun 
	* @date 2016年6月3日 上午8:56:50
	 */
	public int insert(ImStockLog imStockLog);
}
