package com.lishun.im.bean;

import java.util.Date;

public class ImStockLog {

	private String id;
	private String imWarehouseId;
	private String imSpeciesId;
	private String specifications;
	private String operateBy;
	private Long operateNum;
	private Integer operateAction;
	private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImWarehouseId() {
		return imWarehouseId;
	}

	public void setImWarehouseId(String imWarehouseId) {
		this.imWarehouseId = imWarehouseId;
	}

	public String getImSpeciesId() {
		return imSpeciesId;
	}

	public void setImSpeciesId(String imSpeciesId) {
		this.imSpeciesId = imSpeciesId;
	}

	public Long getOperateNum() {
		return operateNum;
	}

	public void setOperateNum(Long operateNum) {
		this.operateNum = operateNum;
	}

	public Integer getOperateAction() {
		return operateAction;
	}

	public void setOperateAction(Integer operateAction) {
		this.operateAction = operateAction;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	public String getOperateBy() {
		return operateBy;
	}

	public void setOperateBy(String operateBy) {
		this.operateBy = operateBy;
	}

}