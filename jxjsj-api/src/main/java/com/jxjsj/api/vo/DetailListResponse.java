package com.jxjsj.api.vo;

import com.jxjsj.api.mongodb.DetailModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by niyang on 2017/10/24.
 */
public class DetailListResponse implements Serializable {

    private List<DetailModel> detailModelList;
    private Long count;

    public List<DetailModel> getDetailModelList() {
        return detailModelList;
    }

    public void setDetailModelList(List<DetailModel> detailModelList) {
        this.detailModelList = detailModelList;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
