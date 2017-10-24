package com.jxjsj.api.mongodb;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by niyang on 2017/10/24.
 */
public class DossierModel implements Serializable {
    //案件编号
    private String caseId;

    //卷书编号
    private String dossierId;

    //卷书名称
    private String dossierName;

    //分类ID
    private String categoryId;

    //是否删除
    private Boolean isDeleted = false;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getDossierId() {
        return dossierId;
    }

    public void setDossierId(String dossierId) {
        this.dossierId = dossierId;
    }

    public String getDossierName() {
        return dossierName;
    }

    public void setDossierName(String dossierName) {
        this.dossierName = dossierName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
