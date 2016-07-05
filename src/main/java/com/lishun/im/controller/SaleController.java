package com.lishun.im.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lishun.im.bean.ImEmployeeSale;
import com.lishun.im.page.Page;
import com.lishun.im.resultBean.ResultCode;
import com.lishun.im.resultBean.ResultMessage;
import com.lishun.im.service.ImStockManageService;
import com.lishun.im.service.SaleManageService;



@Controller
public class SaleController {
	public static Logger logger=LoggerFactory.getLogger(SaleController.class);
	@Autowired
	public SaleManageService saleManageService;
	
	@Autowired
	public ImStockManageService imStockManageService;
	@RequestMapping(value = "imEmployeeSale/edit", method = RequestMethod.POST)
	public String imStockEdit(ImEmployeeSale imEmployeeSale,String proId,
			Model model) {
		String msg="";
		ResultMessage result=imStockManageService.salePro(proId, imEmployeeSale.getNum());
		if(result.getResultCode()==ResultCode.Success){
			ResultMessage resultMessage=saleManageService.editImEmployeeSale(imEmployeeSale);
			if(resultMessage.getResultCode()==ResultCode.Success){
				msg="编辑成功";
			}else{
				msg="编辑失败!!"+resultMessage.getMessage();
			}
		}else{
			msg="编辑失败!!"+result.getMessage();
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
		Map<String, Object> map = saleManageService.queryListImEmployeeSale(rows,
				pageNo, keyword,beginTime,endTime);
		List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("list");
		Long total = (Long) map.get("total");
		Page<Map<String, Object>> resultPage = new Page<Map<String, Object>>();
		resultPage.setResult(list);
		resultPage.setTotalItems(total);
		resultPage.setPageNo(pageNo);
		resultPage.setPageSize(rows);
		model.addAttribute("resultPage", resultPage);
		return "/imStock/saleList";
	}

}
