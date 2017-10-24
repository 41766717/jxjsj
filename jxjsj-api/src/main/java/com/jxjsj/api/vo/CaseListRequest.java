package com.jxjsj.api.vo;

import java.io.Serializable;

/**
 * Created by niyang on 2017/10/24.
 */
public class CaseListRequest implements Serializable {

    private Integer pageIndex;
    private Integer pageSize;
    private String caseName;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }
}
