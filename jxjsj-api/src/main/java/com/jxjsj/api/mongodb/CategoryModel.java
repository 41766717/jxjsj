package com.jxjsj.api.mongodb;

import java.io.Serializable;

/**
 * Created by niyang on 2017/10/24.
 */
public class CategoryModel implements Serializable {

    private String categoryId;
    private String categoryName;

    private Boolean isDeleted = false;

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
