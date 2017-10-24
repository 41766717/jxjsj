package com.jxjsj.api.vo;

import com.jxjsj.api.mongodb.CaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by niyang on 2017/10/24.
 */
public class CaseListResponse implements Serializable{

    private List<CaseModel> caseModelList;
    private Long count;

    public List<CaseModel> getCaseModelList() {
        return caseModelList;
    }

    public void setCaseModelList(List<CaseModel> caseModelList) {
        this.caseModelList = caseModelList;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
