package com.wckj.chasstage.modules.gnpz.service;

import com.wckj.chasstage.modules.fkgl.dao.ChasYwFkdjMapper;
import com.wckj.chasstage.modules.fkgl.entity.ChasYwFkdj;
import com.wckj.chasstage.modules.gnpz.dao.ChasXtGnpzMapper;
import com.wckj.chasstage.modules.gnpz.entity.ChasXtGnpz;
import com.wckj.framework.orm.mybatis.service.IBaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wutl
 * @Title: 功能配置服务
 * @Package 功能配置服务
 * @Description: 功能配置服务
 * @date 2020-10-11 13:52
 */
public interface ChasXtGnpzService extends IBaseService<ChasXtGnpzMapper, ChasXtGnpz> {

    ChasXtGnpz findByBaqid(String baqid);
}
