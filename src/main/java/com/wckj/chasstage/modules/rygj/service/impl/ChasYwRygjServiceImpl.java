package com.wckj.chasstage.modules.rygj.service.impl;


import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.fkgl.entity.ChasYwFkdj;
import com.wckj.chasstage.modules.mjgl.entity.ChasYwMjrq;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.rygj.dao.ChasRygjMapper;
import com.wckj.chasstage.modules.rygj.entity.ChasRygj;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.data.set.MybatisPageDataResultSet;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChasYwRygjServiceImpl extends BaseService<ChasRygjMapper, ChasRygj> implements ChasYwRygjService {
    private static Logger log = Logger.getLogger(ChasYwRygjServiceImpl.class);

    @Autowired
    private ChasSbService sbService;
    @Autowired
    private ChasXtBaqznpzService baqznpzService;
    @Autowired
    private ChasXtQyService qyService;
    @Autowired
    private ChasRyjlService ryjlService;

    @Override
    public ChasRygj findzhlocation(Map<String, Object> map) {
        return baseDao.findzhlocation(map);
    }

    @Override
    public List<ChasRygj> findzhlocations(Map<String, Object> map) {
        return baseDao.findzhlocations(map);
    }

    @Override
    public void delete(String[] ids) {
        for (String s : ids) {
            baseDao.deleteByPrimaryKey(s);
        }
    }

    @Override
    public List<ChasRygj> findbyparams(Map<String, Object> map) {
        return baseDao.findbyparams(map);
    }

    @Override
    public PageDataResultSet<Map> selectAllByFk(int var1, int var2, Object var3, String var4) {
        MybatisPageDataResultSet<Map> mybatisPageData = baseDao.selectAllByFk(var1, var2, var3, var4);
        return mybatisPageData == null ? null : mybatisPageData.convert2PageDataResultSet();
    }

    //根据区域id获取该区域当前所有的人员(包含民警)的最新定位信息
    @Override
    public List<ChasRygj> selectrygjBysxs(String baqid, String qyid, String kssj) {
        Map<String, Object> map = new HashMap<>();
        map.put("baqid", baqid);
        map.put("qyid", qyid);
        map.put("startsj", kssj);
        return baseDao.selectrygjBysxs(map);
    }

    @Override
    public List<ChasRygj> selectrygjByMj(Map<String, Object> map) {
        return baseDao.selectrygjByMj(map);
    }

    @Override
    public Map<String, Object> selectAll(int pageNo, int pageSize, Map<String, Object> param, String orderBy) {
        Map<String, Object> result = new HashMap<>();

//        Map<String,Object> params = MapCollectionUtil.conversionRequestToMap(request);
//        DataQxbsUtil.getSelectAll(rygjService,params);

        MybatisPageDataResultSet<ChasRygj> list = baseDao.selectAll(pageNo, pageSize, param, orderBy);


        result.put("rows", list.getData());
        result.put("total", list.getTotal());
        return result;
    }

    @Override
    public String findcountByBaqid(String baqid) {
        return baseDao.findcountByBaqid(baqid);
    }

    @Override
    public Map<String, Object> getRtspValByQyid(String qyid) {
        return sbService.getRtspValByQyid(qyid);

    }

    @Override
    public void manualAddRygj(ChasBaqryxx ryxx) {
        SessionUser user = WebContext.getSessionUser();
        log.info("进入嫌疑人手动添加第一条轨迹"+ryxx.getRyxm()+ryxx.getRybh());
        BaqConfiguration configuration = baqznpzService.findByBaqid(ryxx.getBaqid());
        //人脸定位是否需要生成第一条轨迹，先预留
        if(configuration.getDwHkrl() ){
            Map<String,Object> param = new HashMap<>();
            param.put("baqid", ryxx.getBaqid());
            param.put("fjlx", "1");
            List<ChasXtQy> qyList = qyService.findList(param, "ysbh asc");
            if(qyList!=null&&!qyList.isEmpty()){
                ChasRyjl ryjl = ryjlService.findRyjlByRybh(ryxx.getBaqid(), ryxx.getRybh());
                if(ryjl==null){
                    log.info("找不到人员记录"+ryxx.getRybh());
                    return;
                }
                param.clear();
                param.put("baqid", ryxx.getBaqid());
                param.put("rybh", ryxx.getRybh());
                List<ChasRygj> list = findList(param, null);
                if(list!=null&&!list.isEmpty()){//已经存在轨迹不手动添加
                    return;
                }
                log.info("手动添加人脸定位轨迹");
                ChasRygj gj = new ChasRygj();
                gj.setId(StringUtils.getGuid32());
                gj.setIsdel(0);
                gj.setDataflag("");
                gj.setLrrSfzh(Objects.isNull(user)?ryxx.getMjSfzh():user.getIdCard());
                gj.setLrsj(new Date());
                gj.setXgrSfzh(Objects.isNull(user)?ryxx.getMjSfzh():user.getIdCard());
                gj.setXgsj(new Date());
                gj.setBaqid(ryxx.getBaqid());
                gj.setBaqmc(ryxx.getBaqmc());
                gj.setRyid(ryxx.getId());
                gj.setQyid(qyList.get(0).getYsid());
                gj.setXm(ryxx.getRyxm());
                gj.setKssj(new Date());
                gj.setFzrxm(ryxx.getMjXm());
                gj.setQymc(qyList.get(0).getQymc());
                gj.setRybh(ryxx.getRybh());
                gj.setRylx("xyr");
                save(gj);

            }else{
                log.info("办案区未配置登记区，不能手动添加轨迹数据");
            }
        }else{
            Map<String,Object> param = new HashMap<>();
            param.put("baqid", ryxx.getBaqid());
            param.put("fjlx", "1");
            List<ChasXtQy> qyList = qyService.findList(param, "ysbh asc");
            if(qyList!=null&&!qyList.isEmpty()){
                ChasRyjl ryjl = ryjlService.findRyjlByRybh(ryxx.getBaqid(), ryxx.getRybh());
                if(ryjl==null){
                    log.info("找不到人员记录"+ryxx.getRybh());
                    return;
                }
                param.clear();
                param.put("baqid", ryxx.getBaqid());
                param.put("rybh", ryxx.getRybh());
                List<ChasRygj> list = findList(param, null);
                if(list!=null&&!list.isEmpty()){//已经存在轨迹不手动添加
                    return;
                }
                log.info("手动添加uwb定位轨迹");
                ChasRygj gj = new ChasRygj();
                gj.setId(StringUtils.getGuid32());
                gj.setIsdel(0);
                gj.setDataflag("");
                gj.setLrrSfzh(Objects.isNull(user)?ryxx.getMjSfzh():user.getIdCard());
                gj.setLrsj(new Date());
                gj.setXgrSfzh(Objects.isNull(user)?ryxx.getMjSfzh():user.getIdCard());
                gj.setXgsj(new Date());
                gj.setBaqid(ryxx.getBaqid());
                gj.setBaqmc(ryxx.getBaqmc());
                gj.setRyid(ryxx.getId());
                gj.setWdbh(ryjl.getWdbhH());
                gj.setQyid(qyList.get(0).getYsid());
                gj.setXm(ryxx.getRyxm());
                gj.setKssj(new Date());
                gj.setFzrxm(ryxx.getMjXm());
                gj.setQymc(qyList.get(0).getQymc());
                gj.setRybh(ryxx.getRybh());
                gj.setRylx("xyr");
                save(gj);
            }else{
                log.info("办案区未配置登记区，不能手动添加轨迹数据");
            }
        }
    }
    @Override
    public void manualAddRygj(ChasYwMjrq ryxx) {
        log.info("进入民警手动添加第一条轨迹"+ryxx.getMjxm()+ryxx.getRybh());
        BaqConfiguration configuration = baqznpzService.findByBaqid(ryxx.getBaqid());
        if(configuration.getDwUwb() ){
            Map<String,Object> param = new HashMap<>();
            param.put("baqid", ryxx.getBaqid());
            param.put("fjlx", "1");
            List<ChasXtQy> qyList = qyService.findList(param, "ysbh asc");
            if(qyList!=null&&!qyList.isEmpty()){
                param.clear();
                param.put("baqid", ryxx.getBaqid());
                param.put("rybh", ryxx.getRybh());
                List<ChasRygj> list = findList(param, null);
                if(list!=null&&!list.isEmpty()){//已经存在轨迹不手动添加
                    return;
                }
                log.info("手动添加uwb定位轨迹");
                ChasRygj gj = new ChasRygj();
                gj.setId(StringUtils.getGuid32());
                gj.setIsdel(0);
                gj.setDataflag("");
                gj.setLrrSfzh("");
                gj.setLrsj(new Date());
                gj.setXgrSfzh("");
                gj.setXgsj(new Date());
                gj.setBaqid(ryxx.getBaqid());
                gj.setBaqmc(ryxx.getBaqmc());
                gj.setRyid(ryxx.getId());
                gj.setWdbh(ryxx.getWdbhH());
                gj.setQyid(qyList.get(0).getYsid());
                gj.setXm(ryxx.getMjxm());
                gj.setKssj(new Date());
                gj.setQymc(qyList.get(0).getQymc());
                gj.setRybh(ryxx.getRybh());
                gj.setRylx("mj");
                save(gj);

            }else{
                log.info("办案区未配置登记区，不能手动添加轨迹数据");
            }
        }else{
            log.info("未启用uwb定位系统");
        }
        //人脸定位是否需要生成第一条轨迹，先预留
        if(configuration.getDwHkrl() ){
            Map<String,Object> param = new HashMap<>();
            param.put("baqid", ryxx.getBaqid());
            param.put("fjlx", "1");
            List<ChasXtQy> qyList = qyService.findList(param, "ysbh asc");
            if(qyList!=null&&!qyList.isEmpty()){
                param.clear();
                param.put("baqid", ryxx.getBaqid());
                param.put("rybh", ryxx.getRybh());
                List<ChasRygj> list = findList(param, null);
                if(list!=null&&!list.isEmpty()){//已经存在轨迹不手动添加
                    return;
                }
                log.info("手动添加uwb定位轨迹");
                ChasRygj gj = new ChasRygj();
                gj.setId(StringUtils.getGuid32());
                gj.setIsdel(0);
                gj.setDataflag("");
                gj.setLrrSfzh("");
                gj.setLrsj(new Date());
                gj.setXgrSfzh("");
                gj.setXgsj(new Date());
                gj.setBaqid(ryxx.getBaqid());
                gj.setBaqmc(ryxx.getBaqmc());
                gj.setRyid(ryxx.getId());
                gj.setQyid(qyList.get(0).getYsid());
                gj.setXm(ryxx.getMjxm());
                gj.setKssj(new Date());
                gj.setQymc(qyList.get(0).getQymc());
                gj.setRybh(ryxx.getRybh());
                gj.setRylx("mj");
                save(gj);

            }else{
                log.info("办案区未配置登记区，不能手动添加轨迹数据");
            }
        }else{
            log.info("未启用人脸定位系统");
        }
    }
    @Override
    public void manualAddRygj(ChasYwFkdj ryxx) {
        log.info("进入访客手动添加第一条轨迹"+ryxx.getFkxm()+ryxx.getRybh());
        BaqConfiguration configuration = baqznpzService.findByBaqid(ryxx.getBaqid());
        if(configuration.getDwUwb() ){
            Map<String,Object> param = new HashMap<>();
            param.put("baqid", ryxx.getBaqid());
            param.put("fjlx", "1");
            List<ChasXtQy> qyList = qyService.findList(param, "ysbh asc");
            if(qyList!=null&&!qyList.isEmpty()){
                param.clear();
                param.put("baqid", ryxx.getBaqid());
                param.put("rybh", ryxx.getRybh());
                List<ChasRygj> list = findList(param, null);
                if(list!=null&&!list.isEmpty()){//已经存在轨迹不手动添加
                    return;
                }
                log.info("手动添加uwb定位轨迹");
                ChasRygj gj = new ChasRygj();
                gj.setId(StringUtils.getGuid32());
                gj.setIsdel(0);
                gj.setDataflag("");
                gj.setLrrSfzh("");
                gj.setLrsj(new Date());
                gj.setXgrSfzh("");
                gj.setXgsj(new Date());
                gj.setBaqid(ryxx.getBaqid());
                gj.setBaqmc(ryxx.getBaqmc());
                gj.setRyid(ryxx.getId());
                gj.setWdbh(ryxx.getWdbhH());
                gj.setQyid(qyList.get(0).getYsid());
                gj.setXm(ryxx.getFkxm());
                gj.setKssj(new Date());
                gj.setQymc(qyList.get(0).getQymc());
                gj.setRybh(ryxx.getRybh());
                gj.setRylx("fk");
                save(gj);

            }else{
                log.info("办案区未配置登记区，不能手动添加轨迹数据");
            }
        }else{
            log.info("未启用uwb定位系统");
        }
        //人脸定位是否需要生成第一条轨迹，先预留
        if(configuration.getDwHkrl() ){
            Map<String,Object> param = new HashMap<>();
            param.put("baqid", ryxx.getBaqid());
            param.put("fjlx", "1");
            List<ChasXtQy> qyList = qyService.findList(param, "ysbh asc");
            if(qyList!=null&&!qyList.isEmpty()){
                param.clear();
                param.put("baqid", ryxx.getBaqid());
                param.put("rybh", ryxx.getRybh());
                List<ChasRygj> list = findList(param, null);
                if(list!=null&&!list.isEmpty()){//已经存在轨迹不手动添加
                    return;
                }
                log.info("手动添加uwb定位轨迹");
                ChasRygj gj = new ChasRygj();
                gj.setId(StringUtils.getGuid32());
                gj.setIsdel(0);
                gj.setDataflag("");
                gj.setLrrSfzh("");
                gj.setLrsj(new Date());
                gj.setXgrSfzh("");
                gj.setXgsj(new Date());
                gj.setBaqid(ryxx.getBaqid());
                gj.setBaqmc(ryxx.getBaqmc());
                gj.setRyid(ryxx.getId());
                gj.setWdbh(ryxx.getWdbhH());
                gj.setQyid(qyList.get(0).getYsid());
                gj.setXm(ryxx.getFkxm());
                gj.setKssj(new Date());
                gj.setQymc(qyList.get(0).getQymc());
                gj.setRybh(ryxx.getRybh());
                gj.setRylx("fk");
                save(gj);

            }else{
                log.info("办案区未配置登记区，不能手动添加轨迹数据");
            }
        }else{
            log.info("未启用人脸定位系统");
        }
    }
}
