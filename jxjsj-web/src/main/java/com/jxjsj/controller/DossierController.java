package com.jxjsj.controller;

import com.jxjsj.api.mongodb.DossierModel;
import com.jxjsj.api.vo.DossierListRequest;
import com.jxjsj.api.vo.DossierListResponse;
import com.jxjsj.service.IDossierService;
import com.jxjsj.util.BizException;
import com.jxjsj.util.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * Created by niyang on 2017/10/24.
 */
@RestController
@RequestMapping(value = "/jxjsj")
@ResponseBody
public class DossierController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IDossierService dossierService;

    /**
     * 卷书列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/dossier-list", method = RequestMethod.POST)
    public RestResult<DossierListResponse> getDossierList(@RequestBody DossierListRequest request) {
        BizException.isNull(request.getCaseId(), "案件编号");
        BizException.isNull(request.getPageIndex(), "pageIndex");
        BizException.isNull(request.getPageSize(), "pageSize");

        DossierListResponse dossierListResponse = dossierService.getDossierList(request);
        return RestResult.createSuccessfull(dossierListResponse);

    }

    /**
     * 新增卷书
     *
     * @param dossierModel
     * @return
     */
    @RequestMapping(value = "/dossier-add", method = RequestMethod.POST)
    public RestResult<String> addDossier(@RequestBody DossierModel dossierModel) {
        BizException.isNull(dossierModel.getCaseId(), "案件编号");
        BizException.isNull(dossierModel.getDossierName(), "卷书名称");
        BizException.isNull(dossierModel.getCategoryId(), "分类");

        String uuid = dossierService.addDossier(dossierModel);
        return RestResult.createSuccessfull(uuid);
    }

    /**
     * 更新卷书
     *
     * @param dossierModel
     * @return
     */
    @RequestMapping(value = "/dossier-update", method = RequestMethod.POST)
    public RestResult<Boolean> updateDossier(@RequestBody DossierModel dossierModel) {
        BizException.isNull(dossierModel.getDossierId(), "卷书编号");
        BizException.isNull(dossierModel.getDossierName(), "卷书名称");
        BizException.isNull(dossierModel.getCategoryId(), "分类");

        dossierService.updateDossier(dossierModel);
        return RestResult.createSuccessfull(true);
    }

    /**
     * 删除卷书
     *
     * @param dossierId
     * @return
     */
    @RequestMapping(value = "/dossier-delete", method = RequestMethod.POST)
    public RestResult<Boolean> deleteDossier(@RequestParam(value = "dossierId") String dossierId) {
        BizException.isNull(dossierId, "卷书编号");

        dossierService.deleteDossier(dossierId);
        return RestResult.createSuccessfull(true);
    }
}
