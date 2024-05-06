package com.wckj.chasstage.api.server.imp.cldj;

import com.wckj.chasstage.api.def.cldj.model.CldjBean;
import com.wckj.chasstage.api.def.cldj.model.CldjParam;
import com.wckj.chasstage.api.def.cldj.model.ClunbindParam;
import com.wckj.chasstage.api.def.cldj.service.ApiCldjService;
import com.wckj.chasstage.common.util.DataQxbsUtil;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.cldj.entity.ChasXtCldj;
import com.wckj.chasstage.modules.cldj.service.ChasXtCldjService;
import com.wckj.chasstage.modules.clsyjl.entity.ChasYwClsyjl;
import com.wckj.chasstage.modules.clsyjl.service.ChasYwClsyjlService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.jdone.common.util.SysUtil;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 车辆登记
 */
@Service
public class ApiCldjServiceImpl implements ApiCldjService {
    final static Logger log = Logger.getLogger(ApiCldjServiceImpl.class);
    @Autowired
    private ChasXtCldjService apiService;
    @Autowired
    private ChasYwClsyjlService clsyjlService;
    @Autowired
    private ChasBaqService baqService;

    @Override
    public ApiReturnResult<?> get(String id) {
        ChasXtCldj yjlb = apiService.findById(id);
        if (yjlb != null) {
            return ResultUtil.ReturnSuccess(yjlb);
        }
        return ResultUtil.ReturnError("无法根据id找到车辆登记信息");
    }

    @Override
    public ApiReturnResult<?> save(CldjBean bean) {
        try {
            if (bean.getZdrysl() == null || bean.getZdrysl() < 0) {
                bean.setZdrysl(0);
            }
            if (StringUtils.isEmpty(bean.getBaqid())) {
                return ResultUtil.ReturnError("办案区id不能为空");
            }
            ChasXtCldj yjlb = new ChasXtCldj();
            MyBeanUtils.copyBeanNotNull2Bean(bean, yjlb);
            yjlb.setId(StringUtils.getGuid32());
            yjlb.setIsdel(0);
            yjlb.setLrsj(new Date());
            yjlb.setXgsj(new Date());
            if (apiService.save(yjlb) > 0) {
                return ResultUtil.ReturnSuccess("保存成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.ReturnError("保存失败");
    }

    @Override
    public ApiReturnResult<?> update(CldjBean bean) {
        try {
//            if(bean.getZdrysl()==null||bean.getZdrysl()<0){
//                bean.setZdrysl(0);
//            }
            if (StringUtils.isEmpty(bean.getBaqid())) {
                return ResultUtil.ReturnError("办案区id不能为空");
            }
            ChasXtCldj yjlb = apiService.findById(bean.getId());
            if (yjlb != null) {
                yjlb.setXgsj(new Date());
                MyBeanUtils.copyBeanNotNull2Bean(bean, yjlb);
                if (apiService.update(yjlb) > 0) {
                    return ResultUtil.ReturnSuccess("修改成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.ReturnError("修改失败");
    }

    @Override
    public ApiReturnResult<?> deletes(String ids) {
        try {
            Stream.of(ids.split(","))
                    .forEach(id -> apiService.deleteById(id));
            return ResultUtil.ReturnSuccess("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.ReturnError("删除失败");
    }

    @Override
    public ApiReturnResult<?> getPageData(CldjParam param) {
        if (StringUtils.isEmpty(param.getBaqid())) {
            String baqid = baqService.getZrBaqid();
            if (StringUtils.isEmpty(baqid)) {
                return ResultUtil.ReturnError("当前用户没有关联办案区");
            }
            param.setBaqid(baqid);
        }
        Map<String, Object> map = MyBeanUtils.copyBean2Map(param);
        DataQxbsUtil.getSelectAll(apiService, map);

        PageDataResultSet<ChasXtCldj> pageData = apiService.getEntityPageData(param.getPage(),
                param.getRows(), map, "lrsj desc");
        Map<String, Object> result = new HashMap<>();
        result.put("total", pageData.getTotal());
        result.put("rows", DicUtil.translate(pageData.getData(), new String[]{"CHASET_CLSYZT", "CHASET_CLLX"}, new String[]{"clsyzt", "cllx"}));
        return ResultUtil.ReturnSuccess(result);
    }

    @Override
    public ApiReturnResult<?> getUsableSycl(CldjParam param) {
        if (StringUtils.isEmpty(param.getBaqid())) {
            return ResultUtil.ReturnError("办案区id不能为空");
        }
        if (StringUtils.isEmpty(param.getRybh())) {
            return ResultUtil.ReturnError("人员编号不能为空");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("baqid", param.getBaqid());
        map.put("cllx", "2");
        List<ChasXtCldj> list = apiService.findList(map, null);
        if (list != null && !list.isEmpty()) {
            list = list.stream().filter(cldj -> {
                int rysl = cldj.getZdrysl() == null ? 0 : cldj.getZdrysl();
                int c = clsyjlService.getClsyryslByclid(cldj.getId());
                return c < rysl;
            }).collect(Collectors.toList());
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        map.clear();
        map.put("rybh", param.getRybh());
        map.put("bindstat", "1");
        List<ChasYwClsyjl> listSyjl = clsyjlService.findList(map, "lrsj desc");
        if (listSyjl != null && !listSyjl.isEmpty()) {
            for (ChasYwClsyjl syjl : listSyjl) {
                ChasXtCldj sel = apiService.findById(syjl.getClid());
                boolean has = false;
                for (ChasXtCldj cl : list) {
                    if (sel.getId().equals(cl.getId())) {
                        has = true;
                        cl.setSelected("1");
                        break;
                    }
                }
                if (!has) {
                    sel.setSelected("1");
                    list.add(0, sel);
                }
            }
        }
        if (list != null && !list.isEmpty()) {
            if (param.getPage() > 0 && param.getRows() > 0) {
                list = list.stream().skip((param.getPage() - 1) * param.getRows()).limit(param.getRows())
                        .collect(Collectors.toList());
            }
            Map<String, Object> result = new HashMap<>();
            result.put("total", list.size());
            result.put("rows", DicUtil.translate(list, new String[]{"CHASET_CLSYZT", "CHASET_CLLX"}, new String[]{"clsyzt", "cllx"}));
            return ResultUtil.ReturnSuccess(result);
        }
        return ResultUtil.ReturnError("无可用送押车辆");
    }

    public ApiReturnResult<?> deleteSycl(ClunbindParam param) {
        if (StringUtils.isEmpty(param.getRybh()) || StringUtils.isEmpty(param.getClid())) {
            return ResultUtil.ReturnError("参数错误");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("rybh", param.getRybh());
        map.put("clid", param.getClid());
        List<ChasYwClsyjl> listSyjl = clsyjlService.findList(map, "lrsj desc");
        if (listSyjl != null && !listSyjl.isEmpty()) {
            for (ChasYwClsyjl syjl : listSyjl) {
                clsyjlService.deleteById(syjl.getId());
            }
        }
        return ResultUtil.ReturnSuccess("车辆解绑成功");
    }

    @Override
    public ApiReturnResult<?> unBindSycl(String id) {
        try {
            if (StringUtils.isEmpty(id)) {
                return ResultUtil.ReturnError("车辆id不能为空");
            }
            ChasXtCldj cldj = apiService.findById(id);
            if (cldj == null) {
                return ResultUtil.ReturnSuccess("车辆不存在");
            }
            clsyjlService.unBindsyjlByClid(id, "");
            cldj.setClsyzt("0");
            apiService.update(cldj);
            return ResultUtil.ReturnSuccess("车辆解绑成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("车辆解绑出错", e);
            return ResultUtil.ReturnError("车辆解绑失败:"+ SysUtil.getExceptionDetail(e));
        }
    }

    public ApiReturnResult<?> unBindSyclBycph(String baqid, String cphm, String crlx) {
        if (StringUtils.isEmpty(baqid) || StringUtils.isEmpty(cphm)) {
            return ResultUtil.ReturnError("车辆id不能为空");
        }
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("baqid", baqid);
            map.put("clNumber", cphm);
            map.put("cllx", "2");
            List<ChasXtCldj> list = apiService.findList(map, null);
            if (list == null || list.isEmpty()) {
                log.info("办案区没有车牌号" + cphm + "的送押车辆");
                return ResultUtil.ReturnError("办案区没有车牌号" + cphm + "车辆");
            }
            list.stream().map(ChasXtCldj::getId).forEach(ApiCldjServiceImpl.this::unBindSycl);
            return ResultUtil.ReturnSuccess("车辆解绑成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("车辆解绑出错", e);
        }
        return ResultUtil.ReturnError("车辆解绑失败");
    }
}
