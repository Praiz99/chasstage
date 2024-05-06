package com.wckj.chasstage.common.util.file.service.impl;

import com.wckj.chasstage.common.util.DateTimeUtils;
import com.wckj.chasstage.common.util.file.dto.FileParam;
import com.wckj.chasstage.common.util.file.service.BaqFileService;
import com.wckj.chasstage.common.util.file.util.FileUtil;
import com.wckj.framework.core.frws.file.ByteFileObj;
import com.wckj.framework.core.frws.file.IByteFileObj;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.web.WebContext;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import com.wckj.frws.sdk.core.obj.UploadParamObj;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class BaqFileServiceImpl implements BaqFileService {
    final static Logger log = Logger.getLogger(FileUtil.class);

    @Override
    public boolean uploadFile(FileParam param) {
        try {
            UploadParamObj uploadParam = new UploadParamObj();
            uploadParam.setOrgSysCode(param.getDwxtbh());
            uploadParam.setBizId(param.getBizId());
            uploadParam.setBizType(param.getBizType());
            FileInfoObj o = FrwsApiForThirdPart.uploadByteFile(
                    new ByteFileObj(param.getFileName(), param.getData()),
                    uploadParam);
            return o != null;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("文件上传失败", e);
        }
        return false;
    }

    @Override
    public String downloadFile(FileParam param) {

        if(StringUtils.isEmpty(param.getFilePath())){
            String basePath = "";
            String rootPath = WebContext.getRequestContext().getRealPath("/");
            if (!rootPath.endsWith("/") && !rootPath.endsWith("\\")) {
                rootPath += "/";
            }
            String day=DateTimeUtils.getDateStr(new Date(), 17);
            String uuid = StringUtils.getGuid32();
            basePath = rootPath + param.getBizType()+"/"+
                    uuid+ "/";
            param.setFilePath(basePath);
        }

        if (StringUtils.isNotEmpty(param.getBizId())) {
            File d = new File(param.getFilePath() + param.getFileName());
            if (d.exists()) {
                d.delete();
            }
            try {
                List<FileInfoObj> infoObjs = FrwsApiForThirdPart.getFileInfoListByBizIdBizType(param.getBizId(), param.getBizType());
                if (!infoObjs.isEmpty()) {
                    FileInfoObj fileInfoObj = infoObjs.get(0);
                    IByteFileObj obj = FrwsApiForThirdPart.downloadByteFileByFileId(fileInfoObj.getId());
                    if (obj != null) {
                        return FileUtil.byte2File(obj.getBytes(), param.getFilePath(), param.getFileName());
                    } else {
                        log.error("从文件服务器下载文件失败fileName:" + param.getFileName() + ",zpid:" + param.getBizId());
                    }
                }

            } catch (Exception e) {
                log.error("从文件服务器下载文件出错" + e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String getFileUrl(String zpid) {
        try {
            FileInfoObj fileList = FrwsApiForThirdPart.getFileInfoByBizId(zpid);
            if(fileList != null){
                return fileList.getDownUrl();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取文件信息出错zpid:"+zpid,e);
        }
        return null;
    }

    @Override
    public String saveFile(String base64Str) {
        if(StringUtils.isEmpty(base64Str)){
            return null;
        }
        String basePath = "";
        String rootPath = WebContext.getRequestContext().getRealPath("/");
        if (!rootPath.endsWith("/") && !rootPath.endsWith("\\")) {
            rootPath += "/";
        }
        //String day=DateTimeUtils.getDateStr(new Date(), 17);
        String day="tmp";
        basePath = rootPath +
                day+ "/";
        String tempImgName = StringUtils.getGuid32() + ".jpg";
        String file=FileUtil.byte2File(Base64.getDecoder().decode(base64Str),
                basePath,tempImgName);
        log.info("保存文件至"+file);
        return file;
    }
}
