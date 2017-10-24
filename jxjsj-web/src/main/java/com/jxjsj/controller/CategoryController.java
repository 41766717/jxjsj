package com.jxjsj.controller;

import com.jxjsj.api.mongodb.CategoryModel;
import com.jxjsj.service.ICategoryService;
import com.jxjsj.util.BizException;
import com.jxjsj.util.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by niyang on 2017/10/24.
 */
@RestController
@RequestMapping(value = "/jxjsj")
@ResponseBody
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    /**
     * 分类列表
     *
     * @return
     */
    @RequestMapping(value = "/category-list", method = RequestMethod.GET)
    public RestResult<List<CategoryModel>> getCategoryList() {
        List<CategoryModel> categoryModelList= categoryService.getCategoryList();

        return RestResult.createSuccessfull(categoryModelList);
    }


    /**
     * 新增分类
     *
     * @param categoryModel
     * @return
     */
    @RequestMapping(value = "/category-add", method = RequestMethod.POST)
    public RestResult<String> addCategory(@RequestBody CategoryModel categoryModel) {
        String uuid = categoryService.addCategory(categoryModel);
        return RestResult.createSuccessfull(uuid);
    }

    /**
     * 更新分类
     *
     * @param categoryModel
     * @return
     */
    @RequestMapping(value = "/category-update", method = RequestMethod.POST)
    public RestResult<Boolean> updateCategory(@RequestBody CategoryModel categoryModel) {
        BizException.isNull(categoryModel.getCategoryId(), "分类编号");

        categoryService.updateCategory(categoryModel);
        return RestResult.createSuccessfull(true);
    }

    /**
     * 删除案件
     *
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/category-delete", method = RequestMethod.POST)
    public RestResult<Boolean> deleteCategory(@RequestParam(value = "categoryId") String categoryId) {
        BizException.isNull(categoryId, "分类编号");

        categoryService.deleteCategory(categoryId);
        return RestResult.createSuccessfull(true);
    }
}
