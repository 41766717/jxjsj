package com.jxjsj.service;

import com.jxjsj.api.mongodb.DetailModel;
import com.jxjsj.api.vo.DetailListRequest;
import com.jxjsj.api.vo.DetailListResponse;
import com.jxjsj.util.BizException;

/**
 * Created by niyang on 2017/10/24.
 */
public interface IDetailService {

    public String addDetail(DetailModel detailModel) throws BizException;

    public DetailListResponse getDetailList(DetailListRequest request) throws BizException;

    public Boolean deleteDetail(String detailId) throws BizException;

    public Boolean updateDetail(DetailModel detailModel) throws BizException;
}
