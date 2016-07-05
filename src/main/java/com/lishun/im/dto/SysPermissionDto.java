package com.lishun.im.dto;
import java.io.Serializable;

import com.lishun.im.bean.SysPermissions;

public class SysPermissionDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SysPermissions sysPermissions;
	private Boolean isbind;	

	public SysPermissions getSysPermission() {
		return sysPermissions;
	}



	public void setSysPermission(SysPermissions sysPermission) {
		this.sysPermissions = sysPermission;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public Boolean getIsbind() {
		return isbind;
	}



	public void setIsbind(Boolean isbind) {
		this.isbind = isbind;
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
