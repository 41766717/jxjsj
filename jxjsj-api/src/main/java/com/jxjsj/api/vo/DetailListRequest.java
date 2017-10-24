package com.jxjsj.api.vo;

import java.io.Serializable;

/**
 * Created by niyang on 2017/10/24.
 */
public class DetailListRequest implements Serializable {
    private Integer pageIndex;
    private Integer pageSize;
    private String dossierId;
    private String detailTitle;

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

    public String getDossierId() {
        return dossierId;
    }

    public void setDossierId(String dossierId) {
        this.dossierId = dossierId;
    }

    public String getDetailTitle() {
        return detailTitle;
    }

    public void setDetailTitle(String detailTitle) {
        this.detailTitle = detailTitle;
    }
}
