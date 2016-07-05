package com.lishun.im.bean;

import java.util.Date;

public class ImStock {
    private String id;
    private Long inventory;
    private String specifications;
    private Double insidePrice;
    private Double outsidePrice;
    private Double purchasePrice;
    private Date updateTime;
    private Date createTime;
    private String imWarehouseId;
    private String imSpeciesId;
	private Boolean imLock;
	private Boolean isPromotion;
	private Double promotionPrice;
	private Date promotionEndtime;
    
	public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    
    public Long getInventory() {
        return inventory;
    }

    
    public void setInventory(Long inventory) {
        this.inventory = inventory;
    }

    
    public String getSpecifications() {
        return specifications;
    }

    
    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    
    public Double getInsidePrice() {
        return insidePrice;
    }

   
    public void setInsidePrice(Double insidePrice) {
        this.insidePrice = insidePrice;
    }

    
    public Double getOutsidePrice() {
        return outsidePrice;
    }

    
    public void setOutsidePrice(Double outsidePrice) {
        this.outsidePrice = outsidePrice;
    }

   
    public Double getPurchasePrice() {
        return purchasePrice;
    }

   
    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    

    
    public Date getUpdateTime() {
        return updateTime;
    }

    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    
    public Date getCreateTime() {
        return createTime;
    }

    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
	public Boolean getImLock() {
		return imLock;
	}
	public void setImLock(Boolean imLock) {
		this.imLock = imLock;
	}
	public Boolean getIsPromotion() {
		return isPromotion;
	}
	public void setIsPromotion(Boolean isPromotion) {
		this.isPromotion = isPromotion;
	}
	public Double getPromotionPrice() {
		return promotionPrice;
	}
	public void setPromotionPrice(Double promotionPrice) {
		this.promotionPrice = promotionPrice;
	}
	public Date getPromotionEndtime() {
		return promotionEndtime;
	}
	public void setPromotionEndtime(Date promotionEndtime) {
		this.promotionEndtime = promotionEndtime;
	}
}