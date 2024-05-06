package com.wckj.chasstage.api.server.imp.clsyjl;

import com.wckj.chasstage.api.def.clsyjl.model.AddClsyjlParam;
import com.wckj.chasstage.api.def.clsyjl.model.ClsyjlBean;
import com.wckj.chasstage.api.def.clsyjl.model.ClsyjlParam;
import com.wckj.chasstage.api.def.clsyjl.service.ApiClsyjlService;
import com.wckj.chasstage.common.util.DataQxbsUtil;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.cldj.entity.ChasXtCldj;
import com.wckj.chasstage.modules.cldj.service.ChasXtCldjService;
import com.wckj.chasstage.modules.clsyjl.entity.ChasYwClsyjl;
import com.wckj.chasstage.modules.clsyjl.service.ChasYwClsyjlService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.web.WebContext;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 车辆登记
 */
@Service
public class ApiClsyjlServiceImpl implements ApiClsyjlService {
    @Autowired
    private ChasYwClsyjlService apiService;
    @Autowired
    private ChasXtCldjService cldjService;
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private ChasRyjlService ryjlService;
    @Override
    public ApiReturnResult<?> get(String id) {
        ChasYwClsyjl yjlb = apiService.findById(id);
        if(yjlb!=null){
            return ResultUtil.ReturnSuccess(yjlb);
        }
        return ResultUtil.ReturnError("无法根据id找到车辆使用记录");
    }

    @Override
    public ApiReturnResult<?> save(AddClsyjlParam bean) {
        try {
            ChasYwClsyjl yjlb = new ChasYwClsyjl();
            yjlb.setId(StringUtils.getGuid32());
            yjlb.setIsdel(0);
            yjlb.setLrsj(new Date());
            yjlb.setXgsj(new Date());
            ChasBaqryxx baqryxx = null;
            if(StringUtils.isNotEmpty(bean.getRybh())){
                baqryxx = baqryxxService.findBaqryxByRybhAll(bean.getRybh(),"");
            }else if(StringUtils.isNotEmpty(bean.getWdbhL())){
                baqryxx = baqryxxService.findRyxxBywdbhL(bean.getWdbhL());
            }
            if(baqryxx==null){
                return ResultUtil.ReturnError("根据编号或者手环编号，找不到人员信息");
            }
            Map<String,Object> map = new HashMap<>();
            map.put("rybh", baqryxx.getRybh());
            //map.put("isend", "0");
            map.put("bindstat", "1");
            List<ChasYwClsyjl> list = apiService.findList(map, "lrsj desc");
            if(list!=null&&!list.isEmpty()){
                return ResultUtil.ReturnError(baqryxx.getRyxm()+"已经绑定车辆");
            }
            yjlb.setBaqid(baqryxx.getBaqid());
            yjlb.setBaqmc(baqryxx.getBaqmc());
            ChasXtCldj cldj = cldjService.findById(bean.getClid());
            if(cldj==null){
                return ResultUtil.ReturnError("找不到车辆登记信息");
            }
            //多个人可使用同一辆车，去掉这个检查
            /*if("1".equals(cldj.getClsyzt())){
                return ResultUtil.ReturnError("车辆正在使用中");
            }*/
            yjlb.setClid(cldj.getId());
            yjlb.setClNumber(cldj.getClNumber());
            yjlb.setJzbh(cldj.getCzjzbh());
            yjlb.setRybh(baqryxx.getRybh());
            yjlb.setRyxm(baqryxx.getRyxm());
            ChasRyjl ryjl = ryjlService.findRyjlByRybh(baqryxx.getBaqid(), baqryxx.getRybh());
            if(ryjl==null){
                return ResultUtil.ReturnError("找不到人员记录信息");
            }
            yjlb.setWdbhL(ryjl.getWdbhL());
            yjlb.setWdbhH(ryjl.getWdbhH());
            yjlb.setSypcbh("");
            yjlb.setIsend("0");
            yjlb.setBindstat("1");
            if(apiService.save(yjlb)>0){
                cldj.setClsyzt("1");
                cldjService.update(cldj);
                return ResultUtil.ReturnSuccess("保存成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.ReturnError("保存失败");
    }

    @Override
    public ApiReturnResult<?> update(ClsyjlBean bean) {
        try {
            ChasYwClsyjl yjlb = apiService.findById(bean.getId());
            if(yjlb!=null){
                yjlb.setXgsj(new Date());
                MyBeanUtils.copyBeanNotNull2Bean(bean, yjlb);
                if(apiService.update(yjlb)>0){
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
                    .forEach(id->apiService.deleteById(id));
            return ResultUtil.ReturnSuccess("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.ReturnError("删除失败");
    }

    @Override
    public ApiReturnResult<?> getPageData(ClsyjlParam param) {
        Map<String, Object> map = MyBeanUtils.copyBean2Map(param);
        DataQxbsUtil.getSelectAll(apiService, map);
        if(WebContext.getSessionUser()!=null){
            String userRoleId = WebContext.getSessionUser().getRoleCode();
            if (!"0101".equals(userRoleId)) {
                String orgCode = WebContext.getSessionUser().getCurrentOrgCode();
                map.put("sydwdm", orgCode);
            }
        }
        PageDataResultSet<ChasYwClsyjl> pageData = apiService.getEntityPageData(param.getPage(),
                param.getRows(), map, "xgsj desc");
        Map<String, Object> result = new HashMap<>();
        result.put("total",pageData.getTotal());
        result.put("rows", DicUtil.translate(pageData.getData(), new String[]{"CHASET_ZD_SF"}, new String[]{"isend"}));
        return ResultUtil.ReturnSuccess(result);
    }

    @Override
    public ApiReturnResult<?> getBindCarByRybh(ClsyjlParam param) {
        if(StringUtils.isEmpty(param.getRybh())){
            return ResultUtil.ReturnError("参数错误");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("rybh", param.getRybh());
        map.put("isend", "0");
        List<ChasYwClsyjl> list = apiService.findList(map, "lrsj desc");
        Map<String, Object> result = new HashMap<>();
        if(list !=null && !list.isEmpty()){
            result.put("syjlxx", list.get(0));
            result.put("isbind", "1");
        }else{
            result.put("isbind", "0");
        }
        return ResultUtil.ReturnSuccess(result);
    }


}
