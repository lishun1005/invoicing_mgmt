package com.lishun.im.dto;


import java.io.Serializable;

import com.lishun.im.bean.SysRole;

public class SysRoleDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SysRole sysRole;
	private Boolean isbind;	

	public SysRole getSysRole() {
		return sysRole;
	}



	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
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
