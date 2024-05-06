package com.wckj.chasstage.modules.zpxx.service.impl;

import com.wckj.chasstage.api.def.zpxx.model.ZpxxParam;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.sswp.entity.ChasSswpxx;
import com.wckj.chasstage.modules.sswp.service.ChasSswpxxService;
import com.wckj.chasstage.modules.zpxx.dao.ChasSswpZpxxMapper;
import com.wckj.chasstage.modules.zpxx.entity.ChasSswpZpxx;
import com.wckj.chasstage.modules.zpxx.service.ChasSswpZpxxService;
import com.wckj.framework.core.frws.file.ByteFileObj;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import com.wckj.frws.sdk.core.obj.UploadParamObj;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import scala.annotation.meta.param;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChasSswpZpxxServiceImpl extends BaseService<ChasSswpZpxxMapper, ChasSswpZpxx> implements ChasSswpZpxxService {
    private static final Logger log = Logger.getLogger(ChasSswpZpxxServiceImpl.class);

    @Lazy
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private ChasSswpxxService sswpxxService;

    @Override
    public DevResult saveZpxxWithFile(ZpxxParam param){
        DevResult devResult = new DevResult();
        try {
            UploadParamObj paramObj = new UploadParamObj();
            Map<String, Object> params = new HashMap<>();
            params.put("rybh", param.getRybh());
            List<ChasBaqryxx> baqryxxes = baqryxxService.findList(params, null);
            ChasBaqryxx baqryxx = baqryxxes.get(0);
            ChasSswpxx sswpxx = sswpxxService.findById(String.valueOf(param.getWpid()));

            SessionUser user = WebContext.getSessionUser();
            Map<String, Object> data = new HashMap<>();
            if (StringUtil.isNotEmpty(param.getBizId())) {
                paramObj.setOrgSysCode(user.getOrgCode());
                paramObj.setOrgName(user.getOrgName());
                paramObj.setBizType(param.getBizType());
                paramObj.setBizId(param.getBizId());
                byte[] fileByte = decode(param.getBase64());
                FileInfoObj fileInfoObj = FrwsApiForThirdPart.uploadByteFile(new ByteFileObj("zp" + StringUtils.getGuid32
                        (), fileByte), paramObj);
                if (fileInfoObj != null) {
                    data = new HashMap<>(16);
                    data.put("imgUrl", fileInfoObj.getDownUrl());
                    data.put("fileId", fileInfoObj.getId());
                    devResult.success("保存成功");
                    devResult.setData(data);
                }
            }
            ChasSswpZpxx zpxx = new ChasSswpZpxx();
            zpxx.setId(StringUtils.getGuid32());
            zpxx.setLrrSfzh(user.getIdCard());
            zpxx.setLrsj(new Date());
            zpxx.setBaqid(baqryxx.getBaqid());
            zpxx.setBaqmc(baqryxx.getBaqmc());
            zpxx.setBizId(param.getBizId());
            zpxx.setDataFlag("");
            zpxx.setIsdel(0);
            zpxx.setRybh(baqryxx.getRybh());
            zpxx.setWpid(sswpxx.getId());
            zpxx.setZplx(SYSCONSTANT.SSWP_ZP_MX);  //明细照片
            zpxx.setXgrSfzh(user.getIdCard());
            zpxx.setXgsj(new Date());
            save(zpxx);
        } catch (Exception e) {
            devResult.error("保存失败");
            e.printStackTrace();
        }
        return devResult;

    }

    @Override
    public DevResult uploadpz(String bizId, String base64, String bizType) {
        DevResult w = new DevResult();
        SessionUser user = WebContext.getSessionUser();
        UploadParamObj paramObj = new UploadParamObj();
        if (StringUtil.isNotEmpty(bizId)) {
            paramObj.setOrgSysCode(user.getOrgCode());
            paramObj.setOrgName(user.getOrgName());
            paramObj.setBizType(bizType);
            paramObj.setBizId(bizId);
            byte[] fileByte = decode(base64);
            FileInfoObj fileInfoObj = FrwsApiForThirdPart.uploadByteFile(new ByteFileObj(StringUtils.getGuid32()+".png", fileByte), paramObj);
            if (fileInfoObj != null) {
                w.success("上传成功");
                w.setCode(1);
            } else {
                w.error("上传失败");
                w.setCode(0);
            }
        } else {
            w.error("biz为空");
            w.setCode(0);
        }

        return w;
    }

    @Override
    public void deleteByWpid(String wpid) {
        baseDao.deleteByWpid(wpid);
    }

    @Override
    public void deleteBybizId(String bizid) {
        baseDao.deleteBybizId(bizid);
    }

    @Override
    public ChasSswpZpxx findZpxxByBizId(String bizId) {
        return baseDao.findZpxxByBizId(bizId);
    }

    @Override
    public ChasSswpZpxx findHzpzxxByRybh(String rybh) {
        Map<String, Object> params = new HashMap<>();
        params.put("rybh", rybh);
        params.put("zplx", SYSCONSTANT.SSWP_ZP_RS_ZL);
        List<ChasSswpZpxx> chasSswpZpxxes = baseDao.selectAll(params, null);
        if(chasSswpZpxxes.size()>0){
            return chasSswpZpxxes.get(0);
        }else{
            return null;
        }
    }

    @Override
    public List<ChasSswpZpxx> findByWpid(String wpid) {
        Map<String, Object> params = new HashMap<>();
        params.put("wpid", wpid);
        return baseDao.selectAll(params, null);
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }


    /**
     * Base64字符串 转换为 byte数组
     */
    public static byte[] decode(String base64) {
        try {
            return new BASE64Decoder().decodeBuffer(base64);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

}
