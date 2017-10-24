package com.jxjsj.service;

import com.jxjsj.api.mongodb.DossierModel;
import com.jxjsj.api.vo.DossierListRequest;
import com.jxjsj.api.vo.DossierListResponse;
import com.jxjsj.util.BizException;

/**
 * Created by niyang on 2017/10/24.
 */
public interface IDossierService {

    /**
     * 获取卷书列表
     *
     * @param request
     * @return
     */
    public DossierListResponse getDossierList(DossierListRequest request) throws BizException;

    /**
     * 新增卷书
     *
     * @param dossierModel
     * @return
     */
    public String addDossier(DossierModel dossierModel) throws BizException;

    /**
     * 更新卷书
     *
     * @param dossierModel
     * @return
     */
    public Boolean updateDossier(DossierModel dossierModel) throws BizException;

    /**
     * 删除卷书
     *
     * @param DossierId
     * @return
     */
    public Boolean deleteDossier(String DossierId) throws BizException;


}
