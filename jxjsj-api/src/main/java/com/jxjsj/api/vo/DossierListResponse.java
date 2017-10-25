package com.jxjsj.api.vo;

import com.jxjsj.api.mongodb.DossierModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by niyang on 2017/10/24.
 */
public class DossierListResponse implements Serializable {
    private List<DossierInfoVo> dossierInfoVoList;
    private Long count;

    public List<DossierInfoVo> getDossierInfoVoList() {
        return dossierInfoVoList;
    }

    public void setDossierInfoVoList(List<DossierInfoVo> dossierInfoVoList) {
        this.dossierInfoVoList = dossierInfoVoList;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
