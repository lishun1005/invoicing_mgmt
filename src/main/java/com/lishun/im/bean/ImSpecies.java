package com.lishun.im.bean;

import java.util.Date;

public class ImSpecies {
  
    private String id;

    
    private String name;

   
    private Date createTime;

    
    private String categoryId;

  
    public String getId() {
        return id;
    }

   
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

   
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}