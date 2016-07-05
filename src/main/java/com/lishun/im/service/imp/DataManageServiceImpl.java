package com.lishun.im.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lishun.im.bean.ImCategory;
import com.lishun.im.bean.ImCompany;
import com.lishun.im.bean.ImEmployees;
import com.lishun.im.bean.ImSpecies;
import com.lishun.im.bean.ImWarehouse;
import com.lishun.im.dao.ImCategoryDao;
import com.lishun.im.dao.ImCompanyDao;
import com.lishun.im.dao.ImEmployeesDao;
import com.lishun.im.dao.ImSpeciesDao;
import com.lishun.im.dao.ImWarehouseDao;
import com.lishun.im.service.DataManageService;

@Service
@Transactional
public class DataManageServiceImpl extends BaseService implements DataManageService {
	
	@Autowired
	ImCategoryDao imCategoryDao;
	@Autowired
	ImSpeciesDao ImSpeciesDao;
	
	@Autowired
	ImWarehouseDao imWarehouseDao;
	
	@Autowired
	ImCompanyDao imCompanyDao;
	@Autowired
	ImEmployeesDao imEmployeesDao;
	
	
	@Override
	public List<ImEmployees> queryListByCompanyId(String categoryId){
		return imEmployeesDao.queryListByCompanyId(categoryId);
	}
	
	
	@Override
	public int imEmployeesDel(String id){
		return imEmployeesDao.delete(id);
	}
	@Override
	public int imEmployeesEdit(ImEmployees imEmployees){
		if(!StringUtils.isNoneBlank(imEmployees.getId())){
			imEmployees.setId(UUID.randomUUID().toString());
			return imEmployeesDao.insert(imEmployees);
		}else{
			return imEmployeesDao.update(imEmployees);
		}
	}
	@Override
	public Map<String, Object> queryListImEmployees(Integer rows, Integer pageNo,
			String keyword) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String,Object>> list=imEmployeesDao.queryList(rows, pageNo-1, keyword);
		result.put("list",list);
		result.put("total", imEmployeesDao.queryListCount(keyword));
		return result;
	}
	@Override
	public Map<String, Object> queryListImCompany(Integer rows, Integer pageNo,
			String keyword) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<ImCompany> list=imCompanyDao.queryList(rows, pageNo-1, keyword);
		result.put("list",list);
		result.put("total", imCompanyDao.queryListCount(keyword));
		return result;
	}
	@Override
	public int editImCompany(ImCompany imCompany){
		if(!StringUtils.isNoneBlank(imCompany.getId())){
			imCompany.setId(UUID.randomUUID().toString());
			return imCompanyDao.insert(imCompany);
		}else{
			return imCompanyDao.update(imCompany);
		}
	}
	@Override
	public ImCompany queryImCompanyByName(String name){
		return imCompanyDao.queryImCompanyByName(name);
	}
	@Override
	public int imCompanyDel(String id){
		return imCompanyDao.delete(id);
	}
	@Override
	public int insertCatagory(ImCategory imCategory){
		return imCategoryDao.insert(imCategory);
	}
	@Override
	public Map<String, Object> queryListCategory(Integer rows, Integer pageNo,
			String keyword) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<ImCategory> list=imCategoryDao.queryList(rows, pageNo-1, keyword);
		result.put("list",list);
		result.put("total", imCategoryDao.queryListCount(keyword));
		return result;
	}
	@Override
	public int categoryEdit(ImCategory imCategory){
		if(!StringUtils.isNoneBlank(imCategory.getId())){
			imCategory.setId(UUID.randomUUID().toString());
			return imCategoryDao.insert(imCategory);
		}else{
			return imCategoryDao.update(imCategory);
		}
	}

	@Override
	public int categorysDel(String id){
		return imCategoryDao.delete(id);
	}
	
	@Override
	public Map<String, Object> queryListImSpecies(Integer rows, Integer pageNo,
			String keyword) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> list=ImSpeciesDao.queryList(rows, pageNo-1, keyword);
		result.put("list",list);
		result.put("total", ImSpeciesDao.queryListCount(keyword));
		return result;
	}
	@Override
	public int imSpeciesEdit(ImSpecies imSpecies){
		if(!StringUtils.isNoneBlank(imSpecies.getId())){
			imSpecies.setId(UUID.randomUUID().toString());
			return ImSpeciesDao.insert(imSpecies);
		}else{
			return ImSpeciesDao.update(imSpecies);
		}
	}
	@Override
	public int imSpeciesDel(String id){
		return ImSpeciesDao.delete(id);
	}
	
	@Override
	public Map<String, Object> queryListImWarehouse(Integer rows, Integer pageNo,
			String keyword) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<ImWarehouse> list=imWarehouseDao.queryList(rows, pageNo-1, keyword);
		result.put("list",list);
		result.put("total", imWarehouseDao.queryListCount(keyword));
		return result;
	}
	@Override
	public int imWarehouseEdit(ImWarehouse imWarehouse){
		if(!StringUtils.isNoneBlank(imWarehouse.getId())){
			imWarehouse.setId(UUID.randomUUID().toString());
			return imWarehouseDao.insert(imWarehouse);
		}else{
			return imWarehouseDao.update(imWarehouse);
		}
	}
	@Override
	public int imWarehouseDel(String id){
		return imWarehouseDao.delete(id);
	}
	@Override
	public List<ImSpecies> queryListByCategoryId(String categoryId){
		return ImSpeciesDao.queryListByCategoryId(categoryId);
	}
	@Override
	public ImCategory queryCategoryByName(String categoryName){
		return imCategoryDao.queryCategoryByName(categoryName);
	}
	
	@Override
	public ImSpecies queryImSpeciesByName(String name,String categoryId){
		return ImSpeciesDao.queryImSpeciesByName(name,categoryId);
	}
	@Override
	public ImWarehouse queryImWarehouseByName(String name){
		return imWarehouseDao.queryImWarehouseByName(name);
	}
	
}
