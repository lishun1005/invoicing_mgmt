package com.lishun.im.service;

import java.util.List;
import java.util.Map;

import com.lishun.im.bean.ImCategory;
import com.lishun.im.bean.ImCompany;
import com.lishun.im.bean.ImEmployees;
import com.lishun.im.bean.ImSpecies;
import com.lishun.im.bean.ImWarehouse;


public interface DataManageService {
	/**
	* Description: 类别列表
	* @param rows 页容量
	* @param pageNo 页码
	* @param keyword 关键字
	* @return Map<String,Object><br>
	* @author lishun 
	* @date 2016年5月26日 上午10:19:43
	 */
	Map<String, Object> queryListCategory(Integer rows, Integer pageNo,
			String keyword);
	/**
	* Description: 添加类别 
	* @param imCategory
	* @return int 大于0：成功，否则失败
	* @author lishun 
	* @date 2016年5月26日 上午10:22:09
	 */
	int insertCatagory(ImCategory imCategory);
	/**
	* Description: 类别更新
	* @param imCategory
	* @return int 大于0：成功，否则失败
	* @author lishun 
	* @date 2016年5月26日 上午10:23:01
	 */
	int categoryEdit(ImCategory imCategory);
	/**
	* Description: 类别删除
	* @param id
	* @return int 大于0：成功，否则失败
	* @author lishun 
	* @date 2016年5月26日 上午10:23:28
	 */
	int categorysDel(String id);
	/**
	* Description: 品种列表
	* @param rows 页容量
	* @param pageNo 页码
	* @param keyword 关键字
	* @return Map<String,Object><br>
	* @author lishun 
	* @date 2016年5月26日 上午10:19:43
	 */
	Map<String, Object> queryListImSpecies(Integer rows, Integer pageNo,
			String keyword);
	/**
	* Description: 品种添加或者更新
	* @param imSpecies
	* @return int 大于0：成功，否则失败
	* @author lishun 
	* @date 2016年5月26日 上午10:24:04
	 */
	int imSpeciesEdit(ImSpecies imSpecies);
	/**
	* Description: 品种删除
	* @param id
	* @return int 大于0：成功，否则失败
	* @author lishun 
	* @date 2016年5月26日 上午10:25:08
	 */
	int imSpeciesDel(String id);
	/**
	* Description: 仓库列表
	* @param rows 页容量
	* @param pageNo 页码
	* @param keyword 关键字
	* @return Map<String,Object><br>
	* @author lishun 
	* @date 2016年5月26日 上午10:19:43
	 */
	Map<String, Object> queryListImWarehouse(Integer rows, Integer pageNo,
			String keyword);
	/**
	* Description: 仓库添加或更新
	* @param imWarehouse
	* @return int 大于0：成功，否则失败
	* @author lishun 
	* @date 2016年5月26日 上午10:26:36
	 */
	int imWarehouseEdit(ImWarehouse imWarehouse);
	/**
	* Description: 仓库删除
	* @param id
	* @return int 大于0：成功，否则失败
	* @author lishun 
	* @date 2016年5月26日 上午10:25:08
	 */
	int imWarehouseDel(String id);
	/**
	 * 
	 * @param categoryId
	 * @return
	 */
	List<ImSpecies> queryListByCategoryId(String categoryId);
	/**
	* Description:根据名称获取类型 
	* @param categoryName
	* @return ImCategory<br>
	* @author lishun 
	* @date 2016年6月14日 下午3:49:19
	 */
	ImCategory queryCategoryByName(String categoryName);
	/**
	* Description:根据名称获取种类 
	* @param name
	* @return ImSpecies<br>
	* @author lishun 
	* @date 2016年6月14日 下午4:30:43
	 */
	ImSpecies queryImSpeciesByName(String name,String categoryId);
	/**
	* Description: 根据名称获取仓库
	* @param name
	* @return  
	* @return ImWarehouse<br>
	* @author lishun 
	* @date 2016年6月14日 下午4:55:36
	 */
	ImWarehouse queryImWarehouseByName(String name);
	/**
	 * 
	 * @param rows
	 * @param pageNo
	 * @param keyword
	 * @return
	 */
	Map<String, Object> queryListImCompany(Integer rows, Integer pageNo,
			String keyword);
	/**
	 * 
	 * @param imCompany
	 * @return
	 */
	int editImCompany(ImCompany imCompany);
	/**
	 * 
	 * @param name
	 * @return
	 */
	ImCompany queryImCompanyByName(String name);
	int imCompanyDel(String id);
	/**
	 * 
	* Description:获取员工记录 
	* @param rows
	* @param pageNo
	* @param keyword
	* @return  
	* @return Map<String,Object><br>
	* @author lishun 
	* @date 2016年7月2日 上午11:56:17
	 */
	Map<String, Object> queryListImEmployees(Integer rows, Integer pageNo,
			String keyword);
	/**
	 * 
	* Description:编辑员工 
	* @param imEmployees
	* @return  
	* @return int<br>
	* @author lishun 
	* @date 2016年7月2日 下午2:09:15
	 */
	int imEmployeesEdit(ImEmployees imEmployees);
	/**
	 * 
	* Description:删除员工 
	* @param id
	* @return  
	* @return int<br>
	* @author lishun 
	* @date 2016年7月2日 下午2:15:49
	 */
	int imEmployeesDel(String id);
	/**
	 * 
	* Description: 
	* @param categoryId
	* @return  
	* @return List<ImEmployees><br>
	* @author lishun 
	* @date 2016年7月2日 下午2:34:24
	 */
	List<ImEmployees> queryListByCompanyId(String categoryId);
	
	
	
}
