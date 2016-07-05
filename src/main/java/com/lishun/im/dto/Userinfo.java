package com.lishun.im.dto;


import java.math.BigDecimal;

/**
 * Userinfo entity. @author MyEclipse Persistence Tools
 */

public class Userinfo implements java.io.Serializable {

	/**
     * @Fields serialVersionUID : TODO
     */
    private static final long serialVersionUID = 4685470691049419097L;
    // Fields

		private String id;
		private String username;
		private String userpassword;
		private String realname;
		private String useremail;
		private String userphone;
		private String remark;
		private String createtime;
		private String modifytime;
		private Short deleteflag;
		private String usercompany;
		private String useraddress;
		private BigDecimal money;
		private String cloudpath;
		private Integer sex;
		private Integer realname_auth;
		// Constructors

		/** default constructor */
		public Userinfo() {
		}

		/** minimal constructor */
//		public Userinfo(String id, String username, String userpassword, String realname, Timestamp createtime) {
//			this.id = id;
//			this.username = username;
//			this.userpassword = userpassword;
//			this.realname = realname;
//			this.createtime = createtime;
//		}

		/** full constructor */
//		public Userinfo(String id, String username, String userpassword, String realname, String useremail, String userphone, String remark, Timestamp createtime, Timestamp modifytime, Short deleteflag,
//				String usercompany, String useraddress, BigDecimal money) {
//			this.id = id;
//			this.username = username;
//			this.userpassword = userpassword;
//			this.realname = realname;
//			this.useremail = useremail;
//			this.userphone = userphone;
//			this.remark = remark;
//			this.createtime = createtime;
//			this.modifytime = modifytime;
//			this.deleteflag = deleteflag;
//			this.usercompany = usercompany;
//			this.useraddress = useraddress;
//			this.money = money;
//		}

		// Property accessors

		public String getId() {
			return this.id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getUsername() {
			return this.username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getUserpassword() {
			return this.userpassword;
		}

		public void setUserpassword(String userpassword) {
			this.userpassword = userpassword;
		}

		public String getRealname() {
			return this.realname;
		}

		public void setRealname(String realname) {
			this.realname = realname;
		}

		public String getUseremail() {
			return this.useremail;
		}

		public void setUseremail(String useremail) {
			this.useremail = useremail;
		}

		public String getUserphone() {
			return this.userphone;
		}

		public void setUserphone(String userphone) {
			this.userphone = userphone;
		}

		public String getRemark() {
			return this.remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

//		public Timestamp getCreatetime() {
//			return this.createtime;
//		}
	//
//		public void setCreatetime(Timestamp createtime) {
//			this.createtime = createtime;
//		}
	//
//		public Timestamp getModifytime() {
//			return this.modifytime;
//		}
	//
//		public void setModifytime(Timestamp modifytime) {
//			this.modifytime = modifytime;
//		}

		public Short getDeleteflag() {
			return this.deleteflag;
		}

		public void setDeleteflag(Short deleteflag) {
			this.deleteflag = deleteflag;
		}

		public String getUsercompany() {
			return this.usercompany;
		}

		public void setUsercompany(String usercompany) {
			this.usercompany = usercompany;
		}

		public String getUseraddress() {
			return this.useraddress;
		}

		public void setUseraddress(String useraddress) {
			this.useraddress = useraddress;
		}

		public BigDecimal getMoney() {
			return this.money;
		}

		public void setMoney(BigDecimal money) {
			this.money = money;
		}

		public String getCreatetime() {
			return createtime;
		}

		public void setCreatetime(String createtime) {
			this.createtime = createtime;
		}

		public String getModifytime() {
			return modifytime;
		}

		public void setModifytime(String modifytime) {
			this.modifytime = modifytime;
		}

        public String getCloudpath() {
            return cloudpath;
        }

        public void setCloudpath(String cloudpath) {
            this.cloudpath = cloudpath;
        }

		public Integer getSex()
		{
			return sex;
		}

		public void setSex(Integer sex)
		{
			this.sex = sex;
		}

		public Integer getRealname_auth()
		{
			return realname_auth;
		}

		public void setRealname_auth(Integer realname_auth)
		{
			this.realname_auth = realname_auth;
		}
		
		
}