package com.jxjsj.service;

import com.jxjsj.api.mongodb.CategoryModel;
import com.jxjsj.util.BizException;

import java.util.List;

/**
 * Created by niyang on 2017/10/24.
 */
public interface ICategoryService {

    public List<CategoryModel> getCategoryList() throws BizException;

    public String addCategory(CategoryModel categoryModel) throws BizException;

    public Boolean updateCategory(CategoryModel categoryModel) throws BizException;

    public Boolean deleteCategory(String categoryId) throws BizException;
}
