package com.jxjsj.service;

import com.jxjsj.api.mongodb.CaseModel;
import com.jxjsj.api.vo.CaseListRequest;
import com.jxjsj.api.vo.CaseListResponse;
import com.jxjsj.util.BizException;

import java.util.List;

/**
 * Created by niyang on 2017/10/24.
 */
public interface ICaseService {

    /**
     * 获取案件列表
     *
     * @param request
     * @return
     */
    public CaseListResponse getCaseList(CaseListRequest request) throws BizException;

    /**
     * 获取案件信息
     *
     * @param caseId
     * @return
     */
    public CaseModel getCaseInfo(String caseId) throws BizException;

    /**
     * 添加案件
     *
     * @param caseModel
     * @return
     */
    public String addCase(CaseModel caseModel) throws BizException;

    /**
     * 更新案件
     *
     * @param caseModel
     * @return
     */
    public Boolean updateCase(CaseModel caseModel) throws BizException;

    /**
     * 删除案件
     *
     * @param caseId
     * @return
     */
    public Boolean deleteCase(String caseId) throws BizException;

    /**
     * 批量删除案件
     * @param caseIdList
     * @return
     * @throws BizException
     */
    public Boolean batchDeleteCase(List<String> caseIdList) throws BizException;
}
