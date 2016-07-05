package com.lishun.im.service;

import java.util.List;
import java.util.Map;

import com.lishun.im.bean.ImStock;
import com.lishun.im.resultBean.ResultBean;
import com.lishun.im.resultBean.ResultMessage;


public interface ImStockManageService {
	
	/**
	* Description: 进货单列表
	* @param rows 页容量
	* @param pageNo 页码
	* @param keyword 关键字
	* @param imWarehouseId 仓库id
	* @return Map<String,Object><br>
	* @author lishun 
	* @date 2016年5月26日 上午10:19:43
	 */
	Map<String, Object> queryListImImStock(Integer rows, Integer pageNo,
			String keyword,String imWarehouseId,String beginTime,String endTime);
	/**
	* Description: 进货单日志列表
	* @param rows 页容量
	* @param pageNo 页码
	* @param keyword 关键字
	* @param imWarehouseId 仓库id
	* @return Map<String,Object><br>
	* @author lishun 
	* @date 2016年5月26日 上午10:19:43
	 */
	Map<String, Object> queryListImImStockLog(Integer rows, Integer pageNo,
			String keyword,String imWarehouseId,String beginTime,String endTime,Integer operateAction);
	/**
	* Description: edit 
	* @param imStock
	* @return ResultMessage<br>
	* @author lishun 
	* @date 2016年5月31日 下午5:02:06
	 */
	ResultMessage editImStock(ImStock imStock);
	/**
	* Description:del 
	* @param id
	* @return ResultMessage<br>
	* @author lishun 
	* @date 2016年5月31日 下午5:01:49
	 */
	ResultMessage delImStock(String id);
	
	/**
	* Description: 出货
	* @param shipmentId 出货商品id
	* @param shipmentNum 出货数量
	* @param imStockImWarehouseId 进货仓库id
	* @return int<br>
	* @author lishun 
	* @date 2016年6月1日 下午4:04:23
	 */
	public ResultMessage speciesEdit(String shipmentId,Long shipmentNum,String imStockImWarehouseId);
	ResultBean<ImStock> queryByIds(String imWarehouseId, String imSpeciesId,
			String specifications);
	ResultBean<List<ImStock>> queryByImWarehouseId(String imWarehouseId);
	ResultBean<List<ImStock>> queryByImSpeciesId(String imSpeciesId);
	ResultMessage salePro(String proId, Long shipmentNum);
}
