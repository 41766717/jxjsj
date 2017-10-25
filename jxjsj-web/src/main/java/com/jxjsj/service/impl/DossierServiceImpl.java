package com.jxjsj.service.impl;

import com.jxjsj.api.constans.Constans;
import com.jxjsj.api.mongodb.CategoryModel;
import com.jxjsj.api.mongodb.DossierModel;
import com.jxjsj.api.vo.DossierInfoVo;
import com.jxjsj.api.vo.DossierListRequest;
import com.jxjsj.api.vo.DossierListResponse;
import com.jxjsj.service.ICategoryService;
import com.jxjsj.service.IDossierService;
import com.jxjsj.util.BizException;
import com.jxjsj.util.UUIDUtil;
import com.mongodb.WriteResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by niyang on 2017/10/24.
 */
@Service
public class DossierServiceImpl implements IDossierService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ICategoryService categoryService;

    /**
     * 卷书列表
     *
     * @param request
     * @return
     */
    @Override
    public DossierListResponse getDossierList(DossierListRequest request) {
        String caseId = request.getCaseId();
        Integer pageSize = request.getPageSize();
        Integer pageIndex = request.getPageIndex();


        Integer criteriaArrayLenth = 0;
        Criteria criteria = new Criteria();
        List<Criteria> searchCriteriaList = new ArrayList<Criteria>();

        searchCriteriaList.add(Criteria.where("isDeleted").is(false));
        criteriaArrayLenth++;

        searchCriteriaList.add(Criteria.where("caseId").is(caseId));
        criteriaArrayLenth++;

        if (!StringUtils.isEmpty(request.getDossierName())) {
            searchCriteriaList.add(Criteria.where("DossierName").regex(".?" + request.getDossierName() + ".*"));
            criteriaArrayLenth++;
        }

        Criteria[] searchCriteriaArray = new Criteria[criteriaArrayLenth];

        for (int i = 0; i < searchCriteriaList.size(); i++) {
            searchCriteriaArray[i] = searchCriteriaList.get(i);
        }

        criteria.andOperator(searchCriteriaArray);
        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, DossierModel.class, Constans.COLLECTIONS_DOSSIER);
        if (!Objects.isNull(pageSize)) {
            query.skip((pageIndex - 1) * pageSize);
            query.limit(pageSize);
        }

        List<DossierModel> dossierModelList = mongoTemplate.find(query, DossierModel.class, Constans.COLLECTIONS_DOSSIER);

        List<CategoryModel> categoryModelList=categoryService.getCategoryList();
        Map<String,CategoryModel> categoryModelMap=categoryModelList.stream().collect(Collectors.toMap(CategoryModel::getCategoryId, Function.identity()));

        List<DossierInfoVo> dossierInfoVoList=new ArrayList<>();
        for (DossierModel dossierModel:dossierModelList) {
            DossierInfoVo dossierInfoVo=new DossierInfoVo();
            BeanUtils.copyProperties(dossierModel,dossierInfoVo);
            String categoryId=dossierModel.getCategoryId();
            String categoryName=categoryModelMap.get(categoryId).getCategoryName();
            dossierInfoVo.setCategoryName(categoryName);
            dossierInfoVoList.add(dossierInfoVo);
        }
        DossierListResponse dossierListResponse = new DossierListResponse();
        dossierListResponse.setDossierInfoVoList(dossierInfoVoList);
        dossierListResponse.setCount(count);
        return dossierListResponse;

    }

    /**
     * 新增卷书
     *
     * @param dossierModel
     * @return
     */
    @Override
    public String addDossier(DossierModel dossierModel) {
        Date cuurrentDate = new Date();
        String uuid = UUIDUtil.generateUUID();
        dossierModel.setCaseId(dossierModel.getCaseId());
        dossierModel.setDossierName(dossierModel.getDossierName());
        dossierModel.setDossierId(uuid);
        dossierModel.setCategoryId(dossierModel.getCategoryId());
        dossierModel.setCreateTime(cuurrentDate);
        dossierModel.setUpdateTime(cuurrentDate);
        try {
            mongoTemplate.save(dossierModel, Constans.COLLECTIONS_DOSSIER);
        } catch (Exception e) {
            e.printStackTrace();
            BizException.fail(501, "保存数据出错");
        }
        return uuid;
    }

    /**
     * 更新卷书
     *
     * @param dossierModel
     * @return
     */
    @Override
    public Boolean updateDossier(DossierModel dossierModel) {
        try {
            Criteria criteria = new Criteria("dossierId");
            criteria.is(dossierModel.getDossierId());
            Query query = new Query();
            query.addCriteria(criteria);

            Update update = new Update();
            if (!StringUtils.isEmpty(dossierModel.getDossierName())) {
                update.set("dossierName", dossierModel.getDossierName());
            }
            update.set("categoryId", dossierModel.getCategoryId());
            update.set("updateTime", new Date());
            WriteResult result = mongoTemplate.updateMulti(query, update, DossierModel.class, Constans.COLLECTIONS_DOSSIER);
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
     * 删除卷书
     *
     * @param dossierId
     * @return
     */
    @Override
    public Boolean deleteDossier(String dossierId) {
        try {
            Criteria criteria = new Criteria("dossierId");
            criteria.is(dossierId);
            Query query = new Query();
            query.addCriteria(criteria);

            Update update = new Update();
            update.set("isDeleted", true);
            update.set("updateTime", new Date());
            WriteResult result = mongoTemplate.updateMulti(query, update, DossierModel.class, Constans.COLLECTIONS_DOSSIER);
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
