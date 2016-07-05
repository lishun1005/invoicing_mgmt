package com.lishun.im.bean;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.junit.Ignore;

@Table(name = "im_sys_permissions")
public class SysPermissions implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2407267540372450539L;
	private String id;
	private String permission;
	private String description;
	private Boolean available;
	
	@Id
	@Column(name = "id")
	public String getId(){
		return id; 
	}
	public  void setId(String id){
		this.id=id; 
	}

	@Column(name = "permission")
	public String getPermission(){
		return permission; 
	}
	public  void setPermission(String permission){
		this.permission=permission; 
	}

	@Column(name = "description")
	public String getDescription(){
		return description; 
	}
	public  void setDescription(String description){
		this.description=description; 
	}

	@Column(name = "available")
	public Boolean getAvailable(){
		return available; 
	}
	public  void setAvailable(Boolean available){
		this.available=available; 
	}

}