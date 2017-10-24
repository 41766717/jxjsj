package com.jxjsj.api.vo;

import com.jxjsj.api.mongodb.DossierModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by niyang on 2017/10/24.
 */
public class DossierListResponse implements Serializable {
    private List<DossierModel> dossierModelList;
    private Long count;

    public List<DossierModel> getDossierModelList() {
        return dossierModelList;
    }

    public void setDossierModelList(List<DossierModel> dossierModelList) {
        this.dossierModelList = dossierModelList;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
