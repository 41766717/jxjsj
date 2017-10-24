package com.jxjsj.service.impl;

import com.jxjsj.api.constans.Constans;
import com.jxjsj.api.mongodb.CaseModel;
import com.jxjsj.api.vo.CaseListRequest;
import com.jxjsj.api.vo.CaseListResponse;
import com.jxjsj.service.ICaseService;
import com.jxjsj.service.IDossierService;
import com.jxjsj.util.BizException;
import com.jxjsj.util.UUIDUtil;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by niyang on 2017/10/24.
 */
@Service
public class CaseServiceImpl implements ICaseService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IDossierService detailService;

    /**
     * 案件列表
     *
     * @param request
     * @return
     */
    @Override
    public CaseListResponse getCaseList(CaseListRequest request) {
        Integer pageSize = request.getPageSize();
        Integer pageIndex = request.getPageIndex();


        Integer criteriaArrayLenth = 0;
        Criteria criteria = new Criteria();
        List<Criteria> searchCriteriaList = new ArrayList<Criteria>();

        searchCriteriaList.add(Criteria.where("isDeleted").is(false));
        criteriaArrayLenth++;

        if (!StringUtils.isEmpty(request.getCaseName())) {
            searchCriteriaList.add(Criteria.where("caseName").regex(".?" + request.getCaseName() + ".*"));
            criteriaArrayLenth++;
        }

        Criteria[] searchCriteriaArray = new Criteria[criteriaArrayLenth];

        for (int i = 0; i < searchCriteriaList.size(); i++) {
            searchCriteriaArray[i] = searchCriteriaList.get(i);
        }

        criteria.andOperator(searchCriteriaArray);
        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, CaseModel.class, Constans.COLLECTIONS_CASE);
        if (!Objects.isNull(pageSize)) {
            query.skip((pageIndex - 1) * pageSize);
            query.limit(pageSize);
        }

        List<CaseModel> caseModelList = mongoTemplate.find(query, CaseModel.class, Constans.COLLECTIONS_CASE);

        CaseListResponse caseListResponse = new CaseListResponse();
        caseListResponse.setCaseModelList(caseModelList);
        caseListResponse.setCount(count);
        return caseListResponse;
    }


    /**
     * 获取案件信息
     *
     * @param caseId
     * @return
     */
    @Override
    public CaseModel getCaseInfo(String caseId) {
        Criteria criteria = new Criteria();
        criteria.and("caseId").is(caseId);

        Query query = new Query();
        query.addCriteria(criteria);
        CaseModel caseModel = mongoTemplate.findOne(query, CaseModel.class, Constans.COLLECTIONS_CASE);
        return caseModel;
    }

    /**
     * 新增案件
     *
     * @param caseModel
     * @return
     */
    @Override
    public String addCase(CaseModel caseModel) {
        Date cuurrentDate = new Date();
        String uuid = UUIDUtil.generateUUID();
        caseModel.setCaseId(uuid);
        caseModel.setCaseName(caseModel.getCaseName());
        caseModel.setCreateTime(cuurrentDate);
        caseModel.setUpdateTime(cuurrentDate);
        try {
            mongoTemplate.save(caseModel, Constans.COLLECTIONS_CASE);
        } catch (Exception e) {
            e.printStackTrace();
            BizException.fail(501, "保存数据出错");
        }
        return uuid;
    }

    /**
     * 更新案件
     *
     * @param caseModel
     * @return
     */
    @Override
    public Boolean updateCase(CaseModel caseModel) {
        try {
            Criteria criteria = new Criteria("caseId");
            criteria.is(caseModel.getCaseId());
            Query query = new Query();
            query.addCriteria(criteria);

            Update update = new Update();
            if(!StringUtils.isEmpty(caseModel.getCaseName())){
                update.set("caseName", caseModel.getCaseName());
            }
            update.set("updateTime", new Date());
            WriteResult result = mongoTemplate.updateMulti(query, update, CaseModel.class, Constans.COLLECTIONS_CASE);
            if (result.getN() <= 0) {
                BizException.fail(502, "更新数据出错");
            }
        } catch (Exception e) {
            e.printStackTrace();
            BizException.fail(501, "更新数据出错");
        }
        return true;
    }

    /**
     * 删除案件
     *
     * @param caseId
     * @return
     */
    @Override
    public Boolean deleteCase(String caseId) {
        try {
            Criteria criteria = new Criteria("caseId");
            criteria.is(caseId);
            Query query = new Query();
            query.addCriteria(criteria);

            Update update = new Update();
            update.set("isDeleted", true);
            update.set("updateTime", new Date());
            WriteResult result = mongoTemplate.updateMulti(query, update, CaseModel.class, Constans.COLLECTIONS_CASE);
            if (result.getN() <= 0) {
                BizException.fail(502, "更新数据出错");
            }
        } catch (Exception e) {
            e.printStackTrace();
            BizException.fail(501, "更新数据出错");
        }
        return true;
    }
}
