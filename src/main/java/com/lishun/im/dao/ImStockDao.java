package com.lishun.im.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lishun.im.bean.ImStock;

public interface ImStockDao{
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
			@Param("beginTime")String beginTime,@Param("endTime")String endTime);
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
			@Param("beginTime")String beginTime,@Param("endTime")String endTime);
	/**
	* Description:添加商品 
	* @param imStock 商品bean
	* @return int<br>
	* @author lishun 
	* @date 2016年6月3日 上午8:56:50
	 */
	public int insert(ImStock imStock);
	/**
	* Description:更新商品 
	* @param imStock 商品bean
	* @return int<br>
	* @author lishun 
	* @date 2016年6月3日 上午8:54:34
	 */
	public int update(ImStock imStock);
	/**
	* Description:通过id获取商品 
	* @param id
	* @return ImStock<br>
	* @author lishun 
	* @date 2016年6月3日 上午8:54:13
	 */
	public ImStock queryById(@Param("id")String id);
	/**
	* Description:仓库，品种，规格 确定唯一的商品 
	* @param imWarehouseId 仓库id
	* @param imSpeciesId 品种id 
	* @param specifications 规格
	* @return ImStock<br>
	* @author lishun 
	* @date 2016年6月3日 上午8:53:28
	 */
	public ImStock queryByIds(@Param("imWarehouseId")String imWarehouseId,@Param("imSpeciesId")String imSpeciesId
			,@Param("specifications")String specifications);
	
	public List<ImStock> queryByImWarehouseId(@Param("imWarehouseId")String imWarehouseId);
	
	public List<ImStock> queryByImSpeciesId(@Param("imSpeciesId")String imSpeciesId);
	
	public int deleteById(@Param("id")String id);
	/**
	* Description: 更新库存
	* @param id 商品id
	* @param action add:添加，sub:减少
	* @param num 数量
	* @return int<br>
	* @author lishun 
	* @date 2016年6月1日 下午4:04:23
	 */
	public int updateInventory(@Param("id")String id,@Param("action")String action,@Param("num")Long num);
}
