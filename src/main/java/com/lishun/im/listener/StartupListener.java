package com.lishun.im.listener;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.lishun.im.bean.SysPermissions;
import com.lishun.im.dao.SysPermissionsDao;

/**
 * 启动监听器
 * @author wugq
 */
@Service
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

	
	@Autowired
    private SysPermissionsDao sysPermissionManagement;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			initSysPermission("dataManager:imWarehouseManager:*","数据管理：仓库管理：所有");
			initSysPermission("dataManager:imWarehouseManager:list","数据管理：仓库管理：列表");
			initSysPermission("dataManager:categoryManager:*","数据管理：类别管理：所有");
			initSysPermission("dataManager:categoryManager:list","数据管理：类别管理：列表");
			initSysPermission("dataManager:speciesManager:*","数据管理：品种管理：所有");
			initSysPermission("dataManager:speciesManager:list","数据管理：品种管理：列表");
			initSysPermission("dataManager:companyManager:*","数据管理：公司管理：所有");
			initSysPermission("dataManager:companyManager:list","数据管理：公司管理：列表");
			initSysPermission("dataManager:employeeManager:*","数据管理：员工管理：所有");
			initSysPermission("dataManager:employeeManager:list","数据管理：员工管理：列表");
			
			initSysPermission("productManager:imStockManager:*","商品管理：进货管理：所有");
			initSysPermission("productManager:imStockManager:list","商品管理：进货管理：列表");
			initSysPermission("productManager:shipmentManager:*","商品管理：出货管理：所有");
			initSysPermission("productManager:shipmentManager:list","商品管理：出货管理：列表");
			initSysPermission("productManager:imStockLogManager:*","商品管理：出货/进货日志：所有");
			initSysPermission("productManager:imStockLogManager:list","商品管理：出货/进货日志：列表");
			
			initSysPermission("saleManager:imEmployeeSaleManager:*","销售管理：销售列表：所有");
			initSysPermission("saleManager:imEmployeeSaleManager:list","销售管理：销售列表：列表");
        }
	}
	
	/**
	 * 权限初始化
	 * @param permission
	 * @param description
	 * @return
	 */
	public boolean initSysPermission(String permission,String description){
		int ret = 0;
		List<SysPermissions>  sysPermission  = sysPermissionManagement.getSysPermissionsByPermission(permission);
		if(sysPermission == null||sysPermission.size()==0){
			SysPermissions newSysPermission = new SysPermissions();
			newSysPermission.setId(UUID.randomUUID().toString());
			newSysPermission.setPermission(permission);
			newSysPermission.setDescription(description);
			newSysPermission.setAvailable(true);
			ret = sysPermissionManagement.save(newSysPermission);
		}else{
			ret = 1;
		}
		return ret ==1 ?true :false;
	}
	
	

}
