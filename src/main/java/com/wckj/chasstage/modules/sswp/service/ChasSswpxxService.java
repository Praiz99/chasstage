package com.wckj.chasstage.modules.sswp.service;

import com.wckj.chasstage.api.def.wpgl.model.SswpBean;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.sswp.dao.ChasSswpxxMapper;
import com.wckj.chasstage.modules.sswp.entity.ChasSswpxx;
import com.wckj.framework.orm.mybatis.service.IBaseService;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Map;

public interface ChasSswpxxService extends IBaseService<ChasSswpxxMapper, ChasSswpxx> {

    Map<String, Object> saveOrupdate(String id, String sswpxgsJson, String delIds, ChasSswpxx sswpxx, String wplb) throws Exception;

    void SaveWithUpdate_List(String json, String delids) throws Exception;

    DevResult SaveWithUpdate_Form(SswpBean sswpxx);

    Map<String, Object> takeBack(String ids, String wpclzt);

    void csqwByRybh(String baqid, String rybh);

    Map<String, Object> findWpxxByRybh(String rybh);

    Map<String, Object> findWpxxById(String id);



    Map<String, Object> deleteSswpxxByIds(String ids);

    Map<String, Object> linkedPeople(String wpid, String ryid) throws Exception;

    Map<String, Object> unlinkedPeople(String wpid);

    Map<String, Object> saveWtyj(ChasSswpxx sswpxx, CommonsMultipartFile file);

    Map<String, Object> selectAll(int pageNo, int pageSize, Map<String, Object> param, String orderBy);
    Map<String, Object> selectCyrAll(int pageNo, int pageSize, Map<String, Object> param, String orderBy);


    public Map<String, Object> getImageByWpid(String id);
    Map<String, Object> findZpxxByRybh(String rybh,String zplx);

}
