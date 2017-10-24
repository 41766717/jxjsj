package com.jxjsj.service.impl;

import com.jxjsj.api.constans.Constans;
import com.jxjsj.api.mongodb.CaseModel;
import com.jxjsj.api.mongodb.CategoryModel;
import com.jxjsj.service.ICategoryService;
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

import java.util.Date;
import java.util.List;

/**
 * Created by niyang on 2017/10/24.
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<CategoryModel> getCategoryList() {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("isDeleted").is(false);
        query.addCriteria(criteria);
        List<CategoryModel> categoryModelList = mongoTemplate.find(query, CategoryModel.class, Constans.COLLECTIONS_CATEGORY);
        return categoryModelList;
    }

    @Override
    public String addCategory(CategoryModel categoryModel) {
        String uuid = UUIDUtil.generateUUID();
        categoryModel.setCategoryId(uuid);
        categoryModel.setCategoryName(categoryModel.getCategoryName());
        try {
            mongoTemplate.save(categoryModel, Constans.COLLECTIONS_CATEGORY);
        } catch (Exception e) {
            e.printStackTrace();
            BizException.fail(501, "保存数据出错");
        }
        return uuid;
    }

    @Override
    public Boolean updateCategory(CategoryModel categoryModel) {
        try {
            Criteria criteria = new Criteria("categoryId");
            criteria.is(categoryModel.getCategoryId());
            Query query = new Query();
            query.addCriteria(criteria);

            Update update = new Update();
            if (!StringUtils.isEmpty(categoryModel.getCategoryName())) {
                update.set("categoryName", categoryModel.getCategoryName());
                WriteResult result = mongoTemplate.updateMulti(query, update, CategoryModel.class, Constans.COLLECTIONS_CATEGORY);
                if (result.getN() <= 0) {
                    BizException.fail(502, "更新数据出错");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            BizException.fail(501, "更新数据出错");
        }
        return null;
    }

    @Override
    public Boolean deleteCategory(String categoryId) {
        try {
            Criteria criteria = new Criteria("categoryId");
            criteria.is(categoryId);
            Query query = new Query();
            query.addCriteria(criteria);

            Update update = new Update();
            update.set("isDeleted", true);
            WriteResult result = mongoTemplate.updateMulti(query, update, CategoryModel.class, Constans.COLLECTIONS_CATEGORY);
            if (result.getN() <= 0) {
                BizException.fail(502, "更新数据出错");
            }
        } catch (Exception e) {
            e.printStackTrace();
            BizException.fail(501, "更新数据出错");
        }
        return null;
    }
}
