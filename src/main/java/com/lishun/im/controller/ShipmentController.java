package com.lishun.im.controller;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lishun.im.bean.ImCategory;
import com.lishun.im.bean.ImSpecies;
import com.lishun.im.bean.ImStock;
import com.lishun.im.bean.ImWarehouse;
import com.lishun.im.page.Page;
import com.lishun.im.resultBean.ResultCode;
import com.lishun.im.resultBean.ResultMessage;
import com.lishun.im.service.DataManageService;
import com.lishun.im.service.ImStockManageService;



@Controller
public class ShipmentController {
	public static Logger logger=LoggerFactory.getLogger(ShipmentController.class);
	@Autowired
	public ImStockManageService imStockManageService;
	@Autowired
	public DataManageService categoryManageService;
	
	@RequestMapping(value = "shipment/list", method = RequestMethod.GET)
	public String speciesList(Integer rows, Integer pageNo, String keyword,String beginTime,String endTime,
			String shipmentImWarehouseId,Model model) {
		Map<String, Object> mapImWarehouse = categoryManageService
				.queryListImWarehouse(null, 1, null);
		List<ImWarehouse> listImWarehouse = (List<ImWarehouse>) mapImWarehouse.get("list");
		model.addAttribute("listImWarehouse", listImWarehouse);
		
		if(StringUtils.isNotBlank(shipmentImWarehouseId)){
			if (null == rows) {
				rows = 10;
			}
			if (null == pageNo) {
				pageNo = 1;
			}
			if(keyword!=null)
				keyword=keyword.trim();
			Map<String, Object> map = imStockManageService.queryListImImStock(rows,
					pageNo, keyword,shipmentImWarehouseId,beginTime,endTime);
			Map<String, Object> mapCat = categoryManageService.queryListCategory(null,
					1, null);
			List<ImCategory> listCat = (List<ImCategory>) mapCat.get("list");
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("list");
			Long total = (Long) map.get("total");
			Page<Map<String, Object>> resultPage = new Page<Map<String, Object>>();
			resultPage.setResult(list);
			resultPage.setTotalItems(total);
			resultPage.setPageNo(pageNo);
			resultPage.setPageSize(rows);
			model.addAttribute("resultPage", resultPage);
			model.addAttribute("listCat", listCat);
			model.addAttribute("listImWarehouse", listImWarehouse);
			return "/imStock/shipmentList";
		}else{
			return "/imStock/shipmentList";
		}
	}
	/**
	* Description: 出货
	* @param shipmentId 出货商品id
	* @param shipmentNum 出货数量
	* @param imStockImWarehouseId 进货仓库id
	* @return String<br>
	* @author lishun 
	* @date 2016年6月1日 下午4:14:54
	 */
	@RequestMapping(value = "shipment/edit", method = RequestMethod.POST)
	public String speciesEdit(String shipmentId,Integer shipmentNum,String imStockImWarehouseId,Model model) {
		String msg="";
		ResultMessage resultMessage=imStockManageService
				.speciesEdit(shipmentId, shipmentNum, imStockImWarehouseId);
		if(resultMessage.getResultCode()==ResultCode.Success){
			msg="操作成功";
		}else{
			msg="操作失败!!"+resultMessage.getMessage();
		}
		model.addAttribute("msg", msg);
		return "redirect:/shipment/list";
	}
}
