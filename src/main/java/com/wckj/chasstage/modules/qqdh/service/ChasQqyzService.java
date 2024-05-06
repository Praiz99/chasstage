package com.wckj.chasstage.modules.qqdh.service;

import com.wckj.chasstage.api.def.qqdh.model.QqdhBean;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.qqdh.dao.ChasQqyzMapper;
import com.wckj.chasstage.modules.qqdh.entity.ChasQqyz;
import com.wckj.framework.orm.mybatis.service.IBaseService;
import com.wckj.jdone.modules.act2.core.NodeProcessCmdObj2;

import java.util.List;
import java.util.Map;

public interface ChasQqyzService extends IBaseService<ChasQqyzMapper, ChasQqyz> {

    void unusedPhone(String id, String org);

    List<ChasQqyz> findbyparams(Map<String, Object> map);

    DevResult saveOrUpdate(ChasQqyz qqyz,boolean add);

    Map<String, Object> selectAll(int pageNo, int pageSize, Map<String, Object> param, String orderBy);


    int deleteByPrimaryRbyh(String rybh);

    int deleteByqqid(String ids);

    // rybh 手动/刷卡开关电话
    DevResult closeQqdh(String baqid, String rybh, String watchNo);

    Map<String, Object> valideApprove(String ryid);

    DevResult closeQqdhById(String baqid, String rybh, String id);

    DevResult openQqdh(String baqid, String rybh, String watchNo);

    DevResult openQqdhById(String baqid, String rybh, String id);

    DevResult qqdhTqly(String id);
}
