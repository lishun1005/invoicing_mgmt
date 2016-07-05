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

import com.lishun.im.bean.ImCategory;
import com.lishun.im.bean.ImCompany;
import com.lishun.im.bean.ImEmployeeSale;
import com.lishun.im.bean.ImWarehouse;
import com.lishun.im.page.Page;
import com.lishun.im.resultBean.ResultCode;
import com.lishun.im.resultBean.ResultMessage;
import com.lishun.im.service.SaleManageService;
import com.lishun.im.utils.ExcelTools;



@Controller
public class SaleController {
	public static Logger logger=LoggerFactory.getLogger(SaleController.class);
	@Autowired
	public SaleManageService saleManageService;
	@RequestMapping(value = "imEmployeeSale/edit", method = RequestMethod.POST)
	public String imStockEdit(ImEmployeeSale imEmployeeSale,
			Model model) {
		String msg="";
		ResultMessage resultMessage=saleManageService.editImEmployeeSale(imEmployeeSale);
		if(resultMessage.getResultCode()==ResultCode.Success){
			msg="编辑成功";
		}else{
			msg="编辑失败!!"+resultMessage.getMessage();
		}
		model.addAttribute("msg", msg);
		return "redirect:/imStock/list";
	}
	
	
	@RequestMapping(value = "imEmployeeSale/list", method = RequestMethod.GET)
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
		return "/imStock/imStockList";
	}

}
