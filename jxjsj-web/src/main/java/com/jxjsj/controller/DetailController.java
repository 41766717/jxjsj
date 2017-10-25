package com.jxjsj.controller;

import com.jxjsj.api.mongodb.DetailModel;
import com.jxjsj.api.vo.DetailListRequest;
import com.jxjsj.api.vo.DetailListResponse;
import com.jxjsj.service.IDetailService;
import com.jxjsj.util.BizException;
import com.jxjsj.util.RestResult;
import com.jxjsj.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Created by niyang on 2017/10/24.
 */
@RestController
@RequestMapping(value = "/jxjsj")
@ResponseBody
public class DetailController {

    @Value("${filePath}")
    private String filePath;

    private static String STANDARD_SUFFIX_NAME = ".pdf";

    @Autowired
    private IDetailService detailService;


    /**
     * 事件列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/detail-list", method = RequestMethod.POST)
    public RestResult<DetailListResponse> getDossierList(@RequestBody DetailListRequest request) {
        BizException.isNull(request.getDossierId(), "卷宗编号");
        BizException.isNull(request.getPageIndex(), "pageIndex");
        BizException.isNull(request.getPageSize(), "pageSize");

        DetailListResponse detailListResponse = detailService.getDetailList(request);
        return RestResult.createSuccessfull(detailListResponse);

    }

    /**
     * 新增事件
     *
     * @param detailModel
     * @return
     */
    @RequestMapping(value = "/detail-add", method = RequestMethod.POST)
    public RestResult<String> addDossier(@RequestBody DetailModel detailModel) {
        BizException.isNull(detailModel.getDossierId(), "卷宗编号");
        BizException.isNull(detailModel.getDetailTitle(), "事件标题");

        String uuid = detailService.addDetail(detailModel);
        return RestResult.createSuccessfull(uuid);
    }

    /**
     * 更新事件
     *
     * @param detailModel
     * @return
     */
    @RequestMapping(value = "/detail-update", method = RequestMethod.POST)
    public RestResult<Boolean> updateDossier(@RequestBody DetailModel detailModel) {
        BizException.isNull(detailModel.getDetailId(), "事件编号");
        BizException.isNull(detailModel.getDetailTitle(), "事件标题");

        detailService.updateDetail(detailModel);
        return RestResult.createSuccessfull(true);
    }

    /**
     * 删除事件
     *
     * @param detailId
     * @return
     */
    @RequestMapping(value = "/detail-delete", method = RequestMethod.POST)
    public RestResult<Boolean> deleteDetail(@RequestParam(value = "detailId") String detailId) {
        BizException.isNull(detailId, "事件编号");

        detailService.deleteDetail(detailId);
        return RestResult.createSuccessfull(true);
    }

    /**
     * 批量删除事件
     *
     * @param detailIdList
     * @return
     */
    @RequestMapping(value = "/detail-batch-delete", method = RequestMethod.POST)
    public RestResult<Boolean> batchDeleteCase(@RequestParam(value = "detailIdList") List<String> detailIdList){
        if(CollectionUtils.isEmpty(detailIdList)){
            BizException.fail(603,"事件编号");
        }
        detailService.batchDeleteDetail(detailIdList);
        return RestResult.createSuccessfull(true);
    }

    /**
     * 上传PDF
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/file-upload", method = RequestMethod.POST)
    public RestResult<String> fileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            BizException.fail(300, "文件为空");
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        suffixName = suffixName.toLowerCase();
        if (!STANDARD_SUFFIX_NAME.equals(suffixName)) {
            BizException.fail(300, "只能上传pdf文件");
        }

        // 文件上传后的路径
        fileName = UUIDUtil.generateUUID() + suffixName;

        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (Exception e) {
            e.printStackTrace();
            BizException.fail(500, "上传失败");
        }
        return RestResult.createSuccessfull(fileName);
    }


    //文件下载相关代码
    @RequestMapping(value = "/file-download", method = RequestMethod.GET)
    public RestResult<Boolean> downloadFile(@RequestParam(value = "fileName") String fileName, org.apache.catalina.servlet4preview.http.HttpServletRequest request, HttpServletResponse response) {
        if (fileName != null) {
            File file = new File(filePath, fileName);
            if (file.exists()) {
                response.setContentType("application/pdf");// 设置强制下载不打开
//                response.addHeader("Content-Disposition",
//                        "attachment;fileName=" +  fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            BizException.fail(500, "加载失败");
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            BizException.fail(500, "加载失败");
                        }
                    }
                }
            } else {
                BizException.fail(404, "文件未找到");
            }
        }
        return null;
    }
}
