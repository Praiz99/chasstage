package com.wckj.chasstage.modules.khdbb.service.impl;

import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.khdbb.dao.ChasXtBbkzMapper;
import com.wckj.chasstage.modules.khdbb.entity.ChasXtBbkz;
import com.wckj.chasstage.modules.khdbb.service.ChasXtBbkzService;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ChasXtBbkzServiceImp extends BaseService<ChasXtBbkzMapper, ChasXtBbkz> implements ChasXtBbkzService {

    private static Logger logger = LoggerFactory.getLogger(ChasXtBbkzServiceImp.class);

    @Override
    public Map<String, Object> saveOrUpdate(String id, ChasXtBbkz bbkz) {
        SessionUser user = WebContext.getSessionUser();
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            if (StringUtil.equals(id, "")) {
                bbkz.setId(StringUtils.getGuid32());
                bbkz.setLrrSfzh(user.getIdCard());
                bbkz.setLrrXm(user.getName());
                bbkz.setLrsj(new Date());
                bbkz.setXgrSfzh(user.getIdCard());
                bbkz.setXgrXm(user.getName());
                bbkz.setXgsj(new Date());
                bbkz.setIsdel(0);
                if (StringUtils.equals(bbkz.getRecent() + "", "1")) {
                    baseDao.updateByClientType(bbkz.getClientType());  //修改同类型的版本默认
                }
                save(bbkz);
            } else {
                ChasXtBbkz xtBbkz = findById(id);
                xtBbkz.setExplain(bbkz.getExplain());
                xtBbkz.setRecent(bbkz.getRecent());
                xtBbkz.setClientNo(bbkz.getClientNo());
                xtBbkz.setClientType(bbkz.getClientType());
                xtBbkz.setInstallId(bbkz.getInstallId());
                xtBbkz.setUpdateId(bbkz.getUpdateId());
                xtBbkz.setXgrSfzh(user.getIdCard());
                xtBbkz.setXgrXm(user.getName());
                xtBbkz.setXgsj(new Date());
                update(xtBbkz);
            }
            result.put("success", true);
            result.put("msg", "操作成功!");
        } catch (Exception e) {
            result.put("success", false);
            result.put("msg", "操作失败:" + e.getMessage());
        }
        return result;
    }

    @Override
    public Map<String, Object> deleteClient(String[] ids) {
        Map<String, Object> result = new HashMap<String, Object>();
        for (String id : ids) {
            deleteById(id);
        }
        result.put("success", true);
        result.put("msg", "删除成功!");
        return result;
    }

    @Override
    public Map<String, Object> getClientVersion(String verisonNo, String clientType) {
        Map<String, Object> result = new HashMap<String, Object>();

        if (StringUtils.isEmpty(verisonNo)) {
            result.put("code", -1);
            result.put("message", "versionNo为空");
            return result;
        }
        if (StringUtils.isEmpty(clientType)) {
            result.put("code", -1);
            result.put("message", "clientType为空");
            return result;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("clientType", clientType);
        params.put("Isrecent", 1);
        List<ChasXtBbkz> xtBbkzList = findList(params, " lrsj desc");
        if (xtBbkzList.isEmpty()) {
            result.put("code", 1);
            result.put("message", "数据库无数据！");
            return result;
        }
        ChasXtBbkz xtBbkz = xtBbkzList.get(0);
        if (StringUtil.equals(verisonNo, xtBbkz.getClientNo())) {
            result.put("code", 1);
            result.put("message", "已是最新版");
        } else {
            result.put("code", 0);
            result.put("message", "有更新,请下载");
            String installId = xtBbkz.getInstallId();
            String updateId = xtBbkz.getUpdateId();
            FileInfoObj installFileInfo = null;
            FileInfoObj updateFileInfo = null;
            try {
                installFileInfo = FrwsApiForThirdPart.getFileInfoByBizId(installId);
                updateFileInfo = FrwsApiForThirdPart.getFileInfoByBizId(updateId);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("获取文件[" + installId + "]/[" + updateId + "]文件信息失败", e);
            }
            if (installFileInfo != null) {
                result.put("downUrl", installFileInfo.getDownUrl());
            }
            if (updateFileInfo != null) {
                result.put("updateUrl", updateFileInfo.getDownUrl());
            }
            result.put("data", xtBbkzList);
            result.put("recordsTotal", xtBbkzList.size());
        }
        return result;
    }
}
