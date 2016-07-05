package com.lishun.im.service;

import java.util.Map;

import com.lishun.im.bean.ImEmployeeSale;
import com.lishun.im.resultBean.ResultMessage;



public interface SaleManageService {

	ResultMessage editImEmployeeSale(ImEmployeeSale imEmployeeSale);

	Map<String, Object> queryListImEmployeeSale(Integer rows, Integer pageNo,
			String keyword,  String beginTime,
			String endTime);
	
}
