package com.lishun.im.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lishun.im.bean.ImEmployeeSale;
import com.lishun.im.dao.ImEmployeeSaleDao;
import com.lishun.im.resultBean.ResultCode;
import com.lishun.im.resultBean.ResultMessage;
import com.lishun.im.service.SaleManageService;

@Service
@Transactional
public class SaleManageServiceImpl extends BaseService implements SaleManageService {
	
	@Autowired
	ImEmployeeSaleDao imEmployeeSaleDao;
	
	@Override
	public Map<String, Object> queryListImEmployeeSale(Integer rows, Integer pageNo,
			String keyword,String beginTime,String endTime) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> list=imEmployeeSaleDao.queryList(rows, pageNo-1, keyword,beginTime,endTime);
		result.put("list",list);
		result.put("total", imEmployeeSaleDao.queryListCount(keyword,beginTime,endTime));
		return result;
	}
	
	@Override
	public ResultMessage editImEmployeeSale(ImEmployeeSale imEmployeeSale) {
		ResultMessage resultMessage=getResultMessage();
		if(!StringUtils.isNoneBlank(imEmployeeSale.getId())){//添加
			imEmployeeSale.setId(UUID.randomUUID().toString());
			imEmployeeSale.setCreateTime(new Date());
			if(imEmployeeSaleDao.insert(imEmployeeSale)>0){
				resultMessage.setResultCode(ResultCode.Success);
			}else{
				resultMessage.setResultCode(ResultCode.Failed);
				resultMessage.setMessage("影响行数为0");
			}
		}else{//更新
			if(imEmployeeSaleDao.update(imEmployeeSale)>0){
				resultMessage.setResultCode(ResultCode.Success);
			}else{
				resultMessage.setResultCode(ResultCode.Failed);
				resultMessage.setMessage("影响行数为0");
			}
			resultMessage.setResultCode(ResultCode.Success);
		}
		return resultMessage;
	}
}
