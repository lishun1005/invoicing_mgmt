package com.lishun.im.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lishun.im.bean.ImStock;
import com.lishun.im.bean.ImStockLog;
import com.lishun.im.dao.ImStockDao;
import com.lishun.im.dao.ImStockLogDao;
import com.lishun.im.resultBean.ResultBean;
import com.lishun.im.resultBean.ResultCode;
import com.lishun.im.resultBean.ResultMessage;
import com.lishun.im.service.ImStockManageService;
import com.lishun.im.shiro.ShiroKit;

@Service
public class ImStockManageServiceImpl extends BaseService implements ImStockManageService {
	@Autowired
	ImStockDao imStockDao;
	@Autowired
	ImStockLogDao imStockLogDao;
	@Override
	public Map<String, Object> queryListImImStock(Integer rows, Integer pageNo,
			String keyword,String imWarehouseId,String beginTime,String endTime) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> list=imStockDao.queryList(rows, pageNo-1, keyword,imWarehouseId,beginTime,endTime);
		result.put("list",list);
		result.put("total", imStockDao.queryListCount(keyword,imWarehouseId,beginTime,endTime));
		return result;
	}
	@Override
	public Map<String, Object> queryListImImStockLog(Integer rows, Integer pageNo,
			String keyword,String imWarehouseId,String beginTime,String endTime,Integer operateAction) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> list=imStockLogDao.queryList(rows, pageNo-1, keyword,imWarehouseId,beginTime,endTime,operateAction);
		result.put("list",list);
		result.put("total", imStockLogDao.queryListCount(keyword,imWarehouseId,beginTime,endTime,operateAction));
		return result;
	}
	@Override
	public ResultMessage editImStock(ImStock imStock) {
		ResultMessage resultMessage=getResultMessage();
		if(!StringUtils.isNoneBlank(imStock.getId())){//添加
			imStock.setId(UUID.randomUUID().toString());
			imStock.setUpdateTime(new Date());
			if(imStockDao.insert(imStock)>0){
				resultMessage.setResultCode(ResultCode.Success);
			}else{
				resultMessage.setResultCode(ResultCode.Failed);
				resultMessage.setMessage("影响行数为0");
			}
		}else{//更新
			if(imStockDao.update(imStock)>0){
				resultMessage.setResultCode(ResultCode.Success);
			}else{
				resultMessage.setResultCode(ResultCode.Failed);
				resultMessage.setMessage("影响行数为0");
			}
			resultMessage.setResultCode(ResultCode.Success);
		}
		return resultMessage;
	}
	@Override
	public ResultMessage delImStock(String id) {
		ResultMessage resultMessage=getResultMessage();
		if(imStockDao.deleteById(id)>0){
			resultMessage.setResultCode(ResultCode.Success);
		}else{
			resultMessage.setResultCode(ResultCode.Failed);
			resultMessage.setMessage("影响行数为0");
		}
		return resultMessage;
	}
	@Override
	public ResultMessage speciesEdit(String shipmentId,Integer shipmentNum,String imStockImWarehouseId) {
		ResultMessage resultMessage=getResultMessage();
		ImStock shipmentTmp=imStockDao.queryById(shipmentId);
		ImStockLog imStockLogTmp=new ImStockLog();
		if(shipmentTmp!=null){
			String specifications=shipmentTmp.getSpecifications();
			String imSpeciesId=shipmentTmp.getImSpeciesId();
			imStockLogTmp.setId(UUID.randomUUID().toString());
			imStockLogTmp.setImSpeciesId(imSpeciesId);
			imStockLogTmp.setSpecifications(specifications);
			imStockLogTmp.setOperateNum(Long.valueOf(shipmentNum));
			imStockLogTmp.setOperateBy(ShiroKit.principal());
			ImStock imStockTmp=imStockDao.queryByIds(imStockImWarehouseId, imSpeciesId, specifications);
			imStockDao.updateInventory(shipmentId, "sub", shipmentNum);
			imStockLogTmp.setImWarehouseId(shipmentTmp.getImWarehouseId());
			imStockLogTmp.setOperateAction(0);
			imStockLogDao.insert(imStockLogTmp);
			
			if(imStockTmp!=null){
				imStockDao.updateInventory(imStockTmp.getId(), "add", shipmentNum);
			}else{//若收货仓库不存在该商品，就添加新的商品
				shipmentTmp.setId(UUID.randomUUID().toString());
				shipmentTmp.setImWarehouseId(imStockImWarehouseId);
				shipmentTmp.setUpdateTime(new Date());
				shipmentTmp.setInventory((Long.valueOf(shipmentNum)));
				imStockDao.insert(shipmentTmp);
			}
			imStockLogTmp.setId(UUID.randomUUID().toString());
			imStockLogTmp.setImWarehouseId(imStockImWarehouseId);
			imStockLogTmp.setOperateAction(1);
			imStockLogDao.insert(imStockLogTmp);
			
			resultMessage.setResultCode(ResultCode.Success);
		}else{
			resultMessage.setResultCode(ResultCode.Failed);
			resultMessage.setMessage("未找到id="+shipmentId+"商品");
		}
		return resultMessage;
	}
	
	@Override
	public ResultBean<ImStock> queryByIds(String imWarehouseId,String imSpeciesId,String specifications) {
		ResultBean<ImStock> resultBean=getResultBean();
		ImStock imStockTmp=imStockDao.queryByIds(imWarehouseId, imSpeciesId, specifications);
		resultBean.setBean(imStockTmp);
		resultBean.setResultCode(ResultCode.Success);
		return resultBean;
	}
	@Override
	public ResultBean<List<ImStock>> queryByImWarehouseId(String imWarehouseId) {
		ResultBean<List<ImStock>> resultBean=getResultBean();
		List<ImStock> imStockTmp=imStockDao.queryByImWarehouseId(imWarehouseId);
		resultBean.setBean(imStockTmp);
		resultBean.setResultCode(ResultCode.Success);
		return resultBean;
	}
	
	@Override
	public ResultBean<List<ImStock>> queryByImSpeciesId(String imSpeciesId) {
		ResultBean<List<ImStock>> resultBean=getResultBean();
		List<ImStock> imStockTmp=imStockDao.queryByImSpeciesId(imSpeciesId);
		resultBean.setBean(imStockTmp);
		resultBean.setResultCode(ResultCode.Success);
		return resultBean;
	}
	
}
