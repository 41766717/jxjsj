package com.jxjsj.controller;

import com.jxjsj.api.mongodb.CaseModel;
import com.jxjsj.api.vo.CaseListRequest;
import com.jxjsj.api.vo.CaseListResponse;
import com.jxjsj.service.ICaseService;
import com.jxjsj.service.IDossierService;
import com.jxjsj.util.BizException;
import com.jxjsj.util.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by niyang on 2017/10/24.
 */
@RestController
@RequestMapping(value = "/jxjsj")
@ResponseBody
public class CaseController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ICaseService caseService;

    @Autowired
    private IDossierService detailService;

    /**
     * 案件列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/case-list", method = RequestMethod.POST)
    public RestResult<CaseListResponse> getCaseList(@RequestBody CaseListRequest request) {
        BizException.isNull(request.getPageIndex(), "pageIndex");
        BizException.isNull(request.getPageSize(), "pageSize");

        CaseListResponse caseListResponse = caseService.getCaseList(request);

        return RestResult.createSuccessfull(caseListResponse);
    }


    /**
     * 获取案件信息
     *
     * @param caseId
     * @return
     */
    @RequestMapping(value = "/case-info", method = RequestMethod.GET)
    public RestResult<CaseModel> getCaseInfo(@RequestParam("caseId") String caseId) {
        BizException.isNull(caseId, "案件编号");

        CaseModel caseModel = caseService.getCaseInfo(caseId);
        return RestResult.createSuccessfull(caseModel);

    }

    /**
     * 新增案件
     *
     * @param caseModel
     * @return
     */
    @RequestMapping(value = "/case-add", method = RequestMethod.POST)
    public RestResult<String> addCase(@RequestBody CaseModel caseModel) {
        String uuid = caseService.addCase(caseModel);
        return RestResult.createSuccessfull(uuid);
    }

    /**
     * 更新案件
     *
     * @param caseModel
     * @return
     */
    @RequestMapping(value = "/case-update", method = RequestMethod.POST)
    public RestResult<Boolean> updateCase(@RequestBody CaseModel caseModel) {
        BizException.isNull(caseModel.getCaseId(), "案件编号");

        caseService.updateCase(caseModel);
        return RestResult.createSuccessfull(true);
    }

    /**
     * 删除案件
     *
     * @param caseId
     * @return
     */
    @RequestMapping(value = "/case-delete", method = RequestMethod.POST)
    public RestResult<Boolean> deleteCase(@RequestParam(value = "caseId") String caseId) {
        BizException.isNull(caseId, "案件编号");

        caseService.deleteCase(caseId);
        return RestResult.createSuccessfull(true);
    }

    /**
     * 批量删除案件
     *
     * @param caseIdList
     * @return
     */
    @RequestMapping(value = "/case-batch-delete", method = RequestMethod.POST)
    public RestResult<Boolean> batchDeleteCase(@RequestParam(value = "caseIdList") List<String> caseIdList){
        if(CollectionUtils.isEmpty(caseIdList)){
            BizException.fail(603,"案件编号");
        }
        caseService.batchDeleteCase(caseIdList);
        return RestResult.createSuccessfull(true);
    }

}
