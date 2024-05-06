package com.wckj.chasstage.modules.mjzpk.dao;


import com.wckj.chasstage.modules.mjzpk.entity.ChasXtMjzpk;
import com.wckj.framework.orm.mybatis.dao.IBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ChasXtMjzpkMapper extends IBaseDao<ChasXtMjzpk> {
    List<ChasXtMjzpk> findByParams(Map<String, Object> map);
    //查找已经删除的民警信息，用于进行照片文件删除
    List<ChasXtMjzpk> findDelMjxx();
}