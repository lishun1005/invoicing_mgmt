package com.lishun.im.bean;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>
 * User: LISHUN
 * <p>
 * Date: 2015-03-17
 * <p>
 * Version: 1.0
 */
@Table(name="im_sys_user")
public class SysUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3834221229440700675L;
	private String id;
	private String username;
	private String password;
	private String email;
	private String salt;
	private Boolean locked;
	private String realname;
	
	@Id
	@Column(name = "id")
	public String getId(){
		return id; 
	}
	public  void setId(String id){
		this.id=id; 
	}

	@Column(name = "username")
	public String getUsername(){
		return username; 
	}
	public  void setUsername(String username){
		this.username=username; 
	}

	@Column(name = "password")
	public String getPassword(){
		return password; 
	}
	public  void setPassword(String password){
		this.password=password; 
	}

	@Column(name = "email")
	public String getEmail(){
		return email; 
	}
	public  void setEmail(String email){
		this.email=email; 
	}

	@Column(name = "salt")
	public String getSalt(){
		return salt; 
	}
	public  void setSalt(String salt){
		this.salt=salt; 
	}

	@Column(name = "locked")
	public Boolean getLocked(){
		return locked; 
	}
	public  void setLocked(Boolean locked){
		this.locked=locked; 
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}

}
