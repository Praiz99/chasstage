package com.wckj.chasstage.common.util.face.service.imp;

import com.wckj.chasstage.api.def.face.model.FaceTzlx;
import com.wckj.chasstage.api.def.face.model.RegisterParam;
import com.wckj.chasstage.api.def.face.service.BaqFaceService;
import com.wckj.chasstage.common.util.face.service.FaceInvokeService;
import com.wckj.chasstage.modules.baq.entity.ChasBaqref;
import com.wckj.chasstage.modules.baq.service.ChasBaqrefService;
import com.wckj.framework.core.exception.BizDataException;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wutl
 * @Title: 人脸服务调用实现类
 * @Package
 * @Description:
 * @date 2020-12-1514:52
 */
@Service
public class FaceInvokeServiceImp implements FaceInvokeService {

    private static Logger logger = LoggerFactory.getLogger(FaceInvokeServiceImp.class);

    @Autowired
    private BaqFaceService baqFaceService;
    @Autowired
    private ChasBaqrefService baqrefService;

    /**
     * @param bizId  照片bizId
     * @param baqid  办案区id
     * @param dwxtbh 嫌疑人主办单位系统编号
     * @param tzbh
     */
    @Override
    public void saveBaqryFeature(String bizId, String baqid, String dwxtbh, String tzbh) {
        try {
            RegisterParam registerParam = new RegisterParam();
            //人脸生成特征或修改
            registerParam.setBaqid(baqid);
            registerParam.setDwxtbh(dwxtbh);
            registerParam.setBizId(bizId);
            registerParam.setTzlx(FaceTzlx.BAQRY.getCode());
            registerParam.setTzbh(tzbh);
            boolean register = baqFaceService.register(registerParam);
            if (!register) {
                logger.error("单位下人脸【" + tzbh + "】生成特征失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("人员【" + tzbh + "】生成特征失败!");
        }
    }

    /**
     * @param bizId  照片bizId
     * @param dwxtbh 嫌疑人主办单位系统编号
     * @param tzbh
     */
    @Override
    public boolean saveMjFeature(String bizId, String dwxtbh, String tzbh) {
        boolean result = true;
        if (StringUtils.isNotEmpty(bizId) && StringUtils.isNotEmpty(dwxtbh)) {
            Map<String, Object> param = new HashMap<>();
            param.put("sydwxtbh", dwxtbh);
            List<ChasBaqref> list = baqrefService.findList(param, null);
            if (list.size() < 1) {
                throw new BizDataException("民警【" + tzbh + "】不存在关联办案区！");
            }
            for (int i = 0; i < list.size(); i++) {
                ChasBaqref chasBaqref = list.get(i);
                RegisterParam registerParam = new RegisterParam();
                //人脸生成特征或修改
                registerParam.setBaqid(chasBaqref.getBaqid());
                registerParam.setDwxtbh(dwxtbh);
                registerParam.setBizId(bizId);
                registerParam.setTzlx(FaceTzlx.MJ.getCode());
                registerParam.setTzbh(tzbh);
                boolean register = baqFaceService.register(registerParam);
                if (!register) {
                    logger.error("单位下人脸【" + chasBaqref.getSydwxtbh() + "," + tzbh + "】生成特征失败!");
                }
            }
        } else {
            logger.error("bizId或dwxtbh为空,生成民警特征失败!");
        }
        return result;
    }

    /**
     * 根据办案区id删除特征
     *
     * @param baqid
     * @param tzbh
     */
    @Override
    public void deleteFeatureByTzbh(String baqid, String tzbh, String tzlx) {
        try {
            baqFaceService.delete(baqid, tzbh, tzlx);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除【" + tzbh + "】特征失败!");

        }
    }
}
