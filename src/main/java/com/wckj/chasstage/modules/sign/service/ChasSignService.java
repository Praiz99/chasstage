package com.wckj.chasstage.modules.sign.service;

import com.wckj.chasstage.modules.sign.dao.ChasSignMapper;
import com.wckj.chasstage.modules.sign.entity.ChasSign;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.List;
import java.util.Map;

public interface ChasSignService extends IBaseService<ChasSignMapper, ChasSign> {

    int deleteByPrimaryRybh(String rybh,String signType);

    Map<String, Object> getSignData(String rybh, String type);

    int saveSignData(String rybh, String type, String imgBody, String signName);

    Map<String, Object> getSignDataByRybhKhd(String rybh, String type);

    List<Map<String, Object>> getSignDataComprehensive(String rybh, String type,boolean isRemoveNy,boolean isIgnore);

    Map<String, Object> getSignDataByRybhKhdIgnoreOfBase64(String rybh, String type);

    ChasSign findByType(String type,String rybh);
}
