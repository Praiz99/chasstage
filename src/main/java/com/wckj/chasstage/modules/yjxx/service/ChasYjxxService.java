package com.wckj.chasstage.modules.yjxx.service;



import com.wckj.chasstage.modules.yjxx.dao.ChasYjxxMapper;
import com.wckj.chasstage.modules.yjxx.entity.ChasYjxx;
import com.wckj.framework.orm.mybatis.service.IBaseService;

import java.util.List;

public interface ChasYjxxService extends IBaseService<ChasYjxxMapper, ChasYjxx> {

    List<ChasYjxx> findXzYjxx(String baqid);

    List<ChasYjxx> findXsYjxx(String baqid);
}
