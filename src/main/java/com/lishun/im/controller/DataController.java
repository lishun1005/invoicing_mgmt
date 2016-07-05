package com.lishun.im.controller;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lishun.im.bean.ImCategory;
import com.lishun.im.bean.ImCompany;
import com.lishun.im.bean.ImEmployees;
import com.lishun.im.bean.ImSpecies;
import com.lishun.im.bean.ImStock;
import com.lishun.im.bean.ImWarehouse;
import com.lishun.im.page.Page;
import com.lishun.im.resultBean.ResultBean;
import com.lishun.im.service.DataManageService;
import com.lishun.im.service.ImStockManageService;



@Controller
public class DataController {
	public static Logger logger=LoggerFactory.getLogger(DataController.class);
	@Autowired
	public DataManageService dataManageService;
	@Autowired
	public ImStockManageService imStockManageService;
	
	
	@RequestMapping(value = "imEmployees/del", method = RequestMethod.GET)
	public String imEmployeesDel(String id,Model model) {
		String msg="";
		//ResultBean<List<ImStock>> res=imStockManageService.queryByImSpeciesId(id);
		//if(res.getBean()!=null&&res.getBean().size()>0){
			msg="删除失败，该类别下存在商品";
		//}else{
			int result=dataManageService.imEmployeesDel(id);
			if(result>0){
				msg="操作成功";
			}else{
				msg="操作失败";
			}
		//}
		
		model.addAttribute("msg", msg);
		return "redirect:/imEmployees/list";
	}
	
	@RequestMapping(value = "imEmployees/edit", method = RequestMethod.POST)
	public String imEmployeesEdit(ImEmployees imEmployees,Model model) {
		String msg="";
		int result=dataManageService.imEmployeesEdit(imEmployees);
		if(result>0){
			msg="操作成功";
		}else{
			msg="操作失败";
		}
		model.addAttribute("msg", msg);
		return "redirect:/imEmployees/list";
	}
	@RequestMapping(value = "imEmployees/list", method = RequestMethod.GET)
	public String imEmployeesList(Integer rows, Integer pageNo, String keyword,
			Model model) {
		if (null == rows) {
			rows = 10;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		Map<String, Object> mapCat = dataManageService.queryListImCompany(null,
				1, null);
		List<ImCompany> listCat = (List<ImCompany>) mapCat.get("list");
		
		
		Map<String, Object> map = dataManageService.queryListImEmployees(rows,
				pageNo, keyword);
		
		List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("list");
		Long total = (Long) map.get("total");
		Page<Map<String, Object>> resultPage = new Page<Map<String, Object>>();
		resultPage.setResult(list);
		resultPage.setTotalItems(total);
		resultPage.setPageNo(pageNo);
		resultPage.setPageSize(rows);
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("listCat", listCat);
		return "/category/imEmployeesList";
	}
	
	@RequestMapping(value = "category/list", method = RequestMethod.GET)
	public String categoryList(Integer rows, Integer pageNo, String keyword,
			Model model) {
		if (null == rows) {
			rows = 10;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		Map<String, Object> map = dataManageService.queryListCategory(rows,
				pageNo, keyword);
		List<ImCategory> list = (List<ImCategory>) map.get("list");
		Long total = (Long) map.get("total");
		Page<ImCategory> categoryPage = new Page<ImCategory>();
		categoryPage.setResult(list);
		categoryPage.setTotalItems(total);
		categoryPage.setPageNo(pageNo);
		categoryPage.setPageSize(rows);
		model.addAttribute("resultPage", categoryPage);
		
		return "/category/categoryList";
	}
	@RequestMapping(value = "category/edit", method = RequestMethod.POST)
	public String categoryEdit(ImCategory imCategory,Model model) {
		String msg="";
		int result=dataManageService.categoryEdit(imCategory);
		if(result>0){
			msg="操作成功";
		}else{
			msg="操作失败";
		}
		model.addAttribute("msg", msg);
		return "redirect:/category/list";
	}
	
	@ResponseBody
	@RequestMapping(value = "category/queryCategoryByName", method = RequestMethod.GET)
	public ImCategory queryCategoryByName(String categoryName) {
		return dataManageService.queryCategoryByName(categoryName);
	}
	
	@RequestMapping(value = "category/del", method = RequestMethod.GET)
	public String categoryDel(String id,Model model) {
		String msg="";
		List<ImSpecies> list=dataManageService.queryListByCategoryId(id);
		if(list!=null&&list.size()>0){
			msg="删除失败，该类别下存在品种";
		}else{
			int result=dataManageService.categorysDel(id);
			if(result>0){
				msg="操作成功";
			}else{
				msg="操作失败";
			}
		}
		
		model.addAttribute("msg", msg);
		return "redirect:/category/list";
	}
	
	@RequestMapping(value = "species/list", method = RequestMethod.GET)
	public String speciesList(Integer rows, Integer pageNo, String keyword,
			Model model) {
		if (null == rows) {
			rows = 10;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		Map<String, Object> mapCat = dataManageService.queryListCategory(null,
				1, null);
		List<ImCategory> listCat = (List<ImCategory>) mapCat.get("list");
		
		
		Map<String, Object> map = dataManageService.queryListImSpecies(rows,
				pageNo, keyword);
		
		List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("list");
		Long total = (Long) map.get("total");
		Page<Map<String, Object>> resultPage = new Page<Map<String, Object>>();
		resultPage.setResult(list);
		resultPage.setTotalItems(total);
		resultPage.setPageNo(pageNo);
		resultPage.setPageSize(rows);
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("listCat", listCat);
		return "/category/speciesList";
	}
	@ResponseBody
	@RequestMapping(value = "species/queryImSpeciesByName", method = RequestMethod.GET)
	public ImSpecies queryImSpeciesByName(String name,String categoryId) {
		return dataManageService.queryImSpeciesByName(name,categoryId);
	}
	@RequestMapping(value = "species/edit", method = RequestMethod.POST)
	public String speciesEdit(ImSpecies imSpecies,Model model) {
		String msg="";
		int result=dataManageService.imSpeciesEdit(imSpecies);
		if(result>0){
			msg="操作成功";
		}else{
			msg="操作失败";
		}
		model.addAttribute("msg", msg);
		return "redirect:/species/list";
	}
	@RequestMapping(value = "species/del", method = RequestMethod.GET)
	public String speciesDel(String id,Model model) {
		String msg="";
		ResultBean<List<ImStock>> res=imStockManageService.queryByImSpeciesId(id);
		if(res.getBean()!=null&&res.getBean().size()>0){
			msg="删除失败，该类别下存在商品";
		}else{
			int result=dataManageService.imSpeciesDel(id);
			if(result>0){
				msg="操作成功";
			}else{
				msg="操作失败";
			}
		}
		
		model.addAttribute("msg", msg);
		return "redirect:/species/list";
	}
	
	@RequestMapping(value = "imCompany/list", method = RequestMethod.GET)
	public String imCompanyList(Integer rows, Integer pageNo, String keyword,
			Model model) {
		if (null == rows) {
			rows = 10;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		Map<String, Object> map = dataManageService.queryListImCompany(rows, pageNo, keyword);
		
		List<ImCompany> list = (List<ImCompany>) map.get("list");
		Long total = (Long) map.get("total");
		Page<ImCompany> resultPage = new Page<ImCompany>();
		resultPage.setResult(list);
		resultPage.setTotalItems(total);
		resultPage.setPageNo(pageNo);
		resultPage.setPageSize(rows);
		model.addAttribute("resultPage", resultPage);
		return "/category/imCompanyList";
	}
	
	@RequestMapping(value = "imCompany/del", method = RequestMethod.GET)
	public String imCompanyDel(String id,Model model) {
		String msg="";
		List<ImEmployees> list=dataManageService.queryListByCompanyId(id);
		if(list!=null&&list.size()>0){
			msg="删除失败，该公司下存在员工";
		}else{
			int result=dataManageService.imCompanyDel(id);
			if(result>0){
				msg="操作成功";
			}else{
				msg="操作失败";
			}
		}
		
		model.addAttribute("msg", msg);
		return "redirect:/imCompany/list";
	}
	@RequestMapping(value = "imCompany/edit", method = RequestMethod.POST)
	public String imCompanyEdit(ImCompany imCompany,Model model) {
		String msg="";
		int result=dataManageService.editImCompany(imCompany);
		if(result>0){
			msg="操作成功";
		}else{
			msg="操作失败";
		}
		model.addAttribute("msg", msg);
		return "redirect:/imCompany/list";
	}
	@ResponseBody
	@RequestMapping(value = "imCompany/queryImCompanyByName", method = RequestMethod.GET)
	public ImCompany queryImCompanyByName(String name) {
		return dataManageService.queryImCompanyByName(name);
	}
	@RequestMapping(value = "imWarehouse/list", method = RequestMethod.GET)
	public String imWarehouseList(Integer rows, Integer pageNo, String keyword,
			Model model) {
		if (null == rows) {
			rows = 10;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		Map<String, Object> map = dataManageService.queryListImWarehouse(rows, pageNo, keyword);
		
		List<ImWarehouse> list = (List<ImWarehouse>) map.get("list");
		Long total = (Long) map.get("total");
		Page<ImWarehouse> resultPage = new Page<ImWarehouse>();
		resultPage.setResult(list);
		resultPage.setTotalItems(total);
		resultPage.setPageNo(pageNo);
		resultPage.setPageSize(rows);
		model.addAttribute("resultPage", resultPage);
		return "/category/imWarehouseList";
	}
	@RequestMapping(value = "imWarehouse/edit", method = RequestMethod.POST)
	public String imWarehouseEdit(ImWarehouse imWarehouse,Model model) {
		String msg="";
		int result=dataManageService.imWarehouseEdit(imWarehouse);
		if(result>0){
			msg="操作成功";
		}else{
			msg="操作失败";
		}
		model.addAttribute("msg", msg);
		return "redirect:/imWarehouse/list";
	}
	@ResponseBody
	@RequestMapping(value = "imWarehouse/queryImWarehouseByName", method = RequestMethod.GET)
	public ImWarehouse queryImWarehouseByName(String name) {
		return dataManageService.queryImWarehouseByName(name);
	}
	@RequestMapping(value = "imWarehouse/del", method = RequestMethod.GET)
	public String imWarehouseDel(String id,Model model) {
		String msg="";
		ResultBean<List<ImStock>> res=imStockManageService.queryByImWarehouseId(id);
		if(res.getBean()!=null&&res.getBean().size()>0){
			msg="删除失败，该仓库下存在商品";
		}else{
			int result=dataManageService.imWarehouseDel(id);
			if(result>0){
				msg="操作成功";
			}else{
				msg="操作失败";
			}
		}
		
		model.addAttribute("msg", msg);
		return "redirect:/imWarehouse/list";
	}

}
