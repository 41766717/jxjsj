package com.jxjsj.service.impl;

import com.jxjsj.api.constans.Constans;
import com.jxjsj.api.mongodb.CaseModel;
import com.jxjsj.api.mongodb.DetailModel;
import com.jxjsj.api.vo.CaseListResponse;
import com.jxjsj.api.vo.DetailListRequest;
import com.jxjsj.api.vo.DetailListResponse;
import com.jxjsj.service.IDetailService;
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
public class DetailServiceImpl implements IDetailService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public DetailListResponse getDetailList(DetailListRequest request) {
        Integer pageSize = request.getPageSize();
        Integer pageIndex = request.getPageIndex();


        Integer criteriaArrayLenth = 0;
        Criteria criteria = new Criteria();
        List<Criteria> searchCriteriaList = new ArrayList<Criteria>();

        searchCriteriaList.add(Criteria.where("isDeleted").is(false));
        criteriaArrayLenth++;

        if (!StringUtils.isEmpty(request.getDetailTitle())) {
            searchCriteriaList.add(Criteria.where("detailTitle").regex(".?" + request.getDetailTitle() + ".*"));
            criteriaArrayLenth++;
        }

        if (!StringUtils.isEmpty(request.getDossierId())) {
            searchCriteriaList.add(Criteria.where("dossierId").is(request.getDossierId()));
            criteriaArrayLenth++;
        }

        Criteria[] searchCriteriaArray = new Criteria[criteriaArrayLenth];

        for (int i = 0; i < searchCriteriaList.size(); i++) {
            searchCriteriaArray[i] = searchCriteriaList.get(i);
        }

        criteria.andOperator(searchCriteriaArray);
        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, DetailModel.class, Constans.COLLECTIONS_DETAIL);
        if (!Objects.isNull(pageSize)) {
            query.skip((pageIndex - 1) * pageSize);
            query.limit(pageSize);
        }

        List<DetailModel> detailModelList = mongoTemplate.find(query, DetailModel.class, Constans.COLLECTIONS_DETAIL);

        DetailListResponse detailListResponse = new DetailListResponse();
        detailListResponse.setDetailModelList(detailModelList);
        detailListResponse.setCount(count);
        return detailListResponse;
    }

    @Override
    public String addDetail(DetailModel detailModel) {
        Date currentTime = new Date();
        String uuid = UUIDUtil.generateUUID();
        detailModel.setDetailId(uuid);
        detailModel.setCreateTime(currentTime);
        detailModel.setUpdateTime(currentTime);

        try {
            mongoTemplate.save(detailModel, Constans.COLLECTIONS_DETAIL);
        } catch (Exception e) {
            e.printStackTrace();
            BizException.fail(501, "保存数据出错");
        }
        return uuid;
    }

    /**
     * 更新卷书
     *
     * @param detailModel
     * @return
     */
    @Override
    public Boolean updateDetail(DetailModel detailModel) {
        try {
            Criteria criteria = new Criteria("detailId");
            criteria.is(detailModel.getDetailId());
            Query query = new Query();
            query.addCriteria(criteria);

            Update update = new Update();
            if (!StringUtils.isEmpty(detailModel.getDetailTitle())) {
                update.set("detailTitle", detailModel.getDetailTitle());
            }
            if (!StringUtils.isEmpty(detailModel.getFileName())) {
                update.set("fileName", detailModel.getFileName());
            }
            if (!StringUtils.isEmpty(detailModel.getAuthor())) {
                update.set("author", detailModel.getAuthor());
            }
            if (!StringUtils.isEmpty(detailModel.getReferenceNum())) {
                update.set("referenceNum", detailModel.getReferenceNum());
            }
            if (!StringUtils.isEmpty(detailModel.getPageNumber())) {
                update.set("pageNumber", detailModel.getPageNumber());
            }
            if (!StringUtils.isEmpty(detailModel.getRemark())) {
                update.set("remark", detailModel.getRemark());
            }
            update.set("updateTime", new Date());
            WriteResult result = mongoTemplate.updateMulti(query, update, DetailModel.class, Constans.COLLECTIONS_DETAIL);
            if (result.getN() <= 0) {
                BizException.fail(502, "更新数据出错");
            }
        } catch (Exception e) {
            e.printStackTrace();
            BizException.fail(501, "更新数据出错");
        }
        return true;
    }

    @Override
    public Boolean batchDeleteDetail(List<String> detailIdList) {
        try {
            Criteria criteria = new Criteria("detailId");
            criteria.in(detailIdList);
            Query query = new Query();
            query.addCriteria(criteria);

            Update update = new Update();
            update.set("isDeleted", true);
            update.set("updateTime", new Date());
            WriteResult result = mongoTemplate.updateMulti(query, update, DetailModel.class, Constans.COLLECTIONS_DETAIL);
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
     * 删除
     *
     * @param detailId
     * @return
     */
    @Override
    public Boolean deleteDetail(String detailId) {
        try {
            Criteria criteria = new Criteria("detailId");
            criteria.is(detailId);
            Query query = new Query();
            query.addCriteria(criteria);

            Update update = new Update();
            update.set("isDeleted", true);
            update.set("updateTime", new Date());
            WriteResult result = mongoTemplate.updateMulti(query, update, DetailModel.class, Constans.COLLECTIONS_DETAIL);
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
