package com.lishun.im.controller;


import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
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
import com.lishun.im.resultBean.ResultCode;
import com.lishun.im.resultBean.ResultMessage;
import com.lishun.im.service.DataManageService;
import com.lishun.im.service.ImStockManageService;
import com.lishun.im.utils.ExcelTools;



@Controller
public class ImStockController {
	public static Logger logger=LoggerFactory.getLogger(ImStockController.class);
	@Autowired
	public ImStockManageService imStockManageService;
	@Autowired
	public DataManageService categoryManageService;
	@Autowired
	public DataManageService dataManageService;
	
	@RequestMapping(value = "imStock/list", method = RequestMethod.GET)
	public String imStockList(Integer rows, Integer pageNo, String keyword,String beginTime
			,Integer excel,String endTime,HttpServletResponse response,Model model) throws Exception {
		if (null == rows) {
			rows = 10;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		if(excel!=null&&excel==1){
			rows=1000000000;
		}
		if(keyword!=null)
			keyword=keyword.trim();
		Map<String, Object> map = imStockManageService.queryListImImStock(rows,
				pageNo, keyword,null,beginTime,endTime);
		
		Map<String, Object> mapCat = categoryManageService.queryListCategory(null,
				1, null);
		List<ImCategory> listCat = (List<ImCategory>) mapCat.get("list");
		
		Map<String, Object> mapImWarehouse = categoryManageService
				.queryListImWarehouse(null, 1, null);
		
		Map<String, Object> mapCompany = dataManageService.queryListImCompany(null,
				1, null);
		List<ImCompany> listCompany = (List<ImCompany>) mapCompany.get("list");
		
		List<ImWarehouse> listImWarehouse = (List<ImWarehouse>) mapImWarehouse.get("list");
		
		
		List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("list");
		
		if(excel!=null&&excel==1){
			for(int i=0;i<list.size();i++){
				Boolean isPromotion =(Boolean)list.get(i).get("is_promotion");
				if(isPromotion){
					list.get(i).put("is_promotion", "是");
				}else{
					list.get(i).put("is_promotion", "否");
				}
			}
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			Map<String,Object> headerMap=new LinkedHashMap<String, Object>();
			headerMap.put("iweName", "仓库");
			headerMap.put("icyName", "类别");
			headerMap.put("issName", "品种");
			headerMap.put("specifications", "规格");
			headerMap.put("inventory", "库存");
			headerMap.put("inside_price", "外部价");
			headerMap.put("outside_price", "内部价");
			headerMap.put("purchase_price", "进货价");
			headerMap.put("update_time", "更新时间");
			headerMap.put("is_promotion", "是否促销");
			headerMap.put("promotion_price", "促销价");
			headerMap.put("promotion_endtime", "促销截止时间");
			headerMap.put("create_time", "入库时间");
			Workbook wb =ExcelTools.exportListExcel(list, headerMap, "数据", null);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ URLEncoder.encode(sdf.format(new Date())+"_商品信息", "UTF-8") + ".xls");
			OutputStream ouputStream = response.getOutputStream();
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		}
		Long total = (Long) map.get("total");
		Page<Map<String, Object>> resultPage = new Page<Map<String, Object>>();
		resultPage.setResult(list);
		resultPage.setTotalItems(total);
		resultPage.setPageNo(pageNo);
		resultPage.setPageSize(rows);
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("listCat", listCat);
		model.addAttribute("listCompany", listCompany);
		model.addAttribute("listImWarehouse", listImWarehouse);
		return "/imStock/imStockList";
	}
	@RequestMapping(value = "imStockLog/list", method = RequestMethod.GET)
	public String imStockLogList(Integer rows, Integer pageNo, String keyword,Integer operateAction,
			Integer excel,String beginTime,String endTime,HttpServletResponse response,Model model) throws Exception {
		if (null == rows) {
			rows = 10;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		if(excel!=null&&excel==1){
			rows=1000000000;
		}
		if(keyword!=null)
			keyword=keyword.trim();
		Map<String, Object> map = imStockManageService.queryListImImStockLog(rows,
				pageNo, keyword,null,beginTime,endTime,operateAction);
		List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("list");
		if(excel!=null&&excel==1){
			for(int i=0;i<list.size();i++){
				Integer operateActionTmp =(Integer)list.get(i).get("operate_action");
				if(operateActionTmp==0){
					list.get(i).put("operate_action", "出库");
				}else{
					list.get(i).put("operate_action", "入库");
				}
			}
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			Map<String,Object> headerMap=new LinkedHashMap<String, Object>();
			headerMap.put("iweName", "仓库");
			headerMap.put("icyName", "类别");
			headerMap.put("issName", "品种");
			headerMap.put("specifications", "规格");
			headerMap.put("operate_num", "仓库数量");
			headerMap.put("operate_action", "操作");
			headerMap.put("operate_by", "操作人");
			headerMap.put("create_time", "操作时间");
			Workbook wb =ExcelTools.exportListExcel(list, headerMap, "数据", null);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ URLEncoder.encode(sdf.format(new Date())+"_进货出货日志", "UTF-8") + ".xls");
			OutputStream ouputStream = response.getOutputStream();
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		}
		Long total = (Long) map.get("total");
		Page<Map<String, Object>> resultPage = new Page<Map<String, Object>>();
		resultPage.setResult(list);
		resultPage.setTotalItems(total);
		resultPage.setPageNo(pageNo);
		resultPage.setPageSize(rows);
		model.addAttribute("resultPage", resultPage);
		return "/imStock/imStockLogList";
	}
	@RequestMapping(value = "imStock/edit", method = RequestMethod.POST)
	public String imStockEdit(ImStock imStock,
			Model model) {
		String msg="";
		ResultMessage resultMessage=imStockManageService.editImStock(imStock);
		if(resultMessage.getResultCode()==ResultCode.Success){
			msg="编辑成功";
		}else{
			msg="编辑失败!!"+resultMessage.getMessage();
		}
		model.addAttribute("msg", msg);
		return "redirect:/imStock/list";
	}
	@RequestMapping(value = "imStock/del", method = RequestMethod.GET)
	public String imWarehouseDel(String id,Model model) {
		String msg="";
		ResultMessage resultMessage=imStockManageService.delImStock(id);
		if(resultMessage.getResultCode()==ResultCode.Success){
			msg="操作成功";
		}else{
			msg="操作失败!!"+resultMessage.getMessage();
		}
		model.addAttribute("msg", msg);
		return "redirect:/imStock/list";
	}
	@ResponseBody
	@RequestMapping(value = "imStock/queryListByCategoryId", method = RequestMethod.GET)
	public List<ImSpecies> queryListByCategoryId(String categoryId) {
		return categoryManageService.queryListByCategoryId(categoryId);
	}
	@ResponseBody
	@RequestMapping(value = "imStock/queryByIds", method = RequestMethod.GET)
	public ImStock queryByIds(String imWarehouseId,String imSpeciesId,String specifications) {
		ResultBean<ImStock> resultBean=imStockManageService
					.queryByIds(imWarehouseId, imSpeciesId, specifications);
		return resultBean.getBean();
	}
	
	@ResponseBody
	@RequestMapping(value = "imStock/queryListByCompanyId", method = RequestMethod.GET)
	public List<ImEmployees> queryListByCompanyId(String id) {
		return dataManageService.queryListByCompanyId(id);
	}
}
