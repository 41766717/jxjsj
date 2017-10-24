package com.jxjsj.api.mongodb;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by niyang on 2017/10/24.
 */
public class CaseModel implements Serializable {

    //案件ID
    private String caseId;

    //案件名称
    private String caseName;

    //是否删除
    private Boolean isDeleted = false;

    //创建日期
    private Date createTime;

    //更新日期
    private Date updateTime;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
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
}
