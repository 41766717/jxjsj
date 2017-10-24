package com.jxjsj.api.vo;

import java.io.Serializable;

/**
 * Created by niyang on 2017/10/24.
 */
public class DossierListRequest implements Serializable {
    private String caseId;
    private Integer pageSize;
    private Integer pageIndex;
    private String dossierName;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getDossierName() {
        return dossierName;
    }

    public void setDossierName(String dossierName) {
        this.dossierName = dossierName;
    }
}
