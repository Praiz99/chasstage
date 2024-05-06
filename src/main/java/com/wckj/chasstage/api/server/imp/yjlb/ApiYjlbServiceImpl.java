package com.wckj.chasstage.api.server.imp.yjlb;

import com.wckj.chasstage.api.def.yjlb.model.YjlbBean;
import com.wckj.chasstage.api.def.yjlb.model.YjlbParam;
import com.wckj.chasstage.api.def.yjlb.service.ApiYjlbService;
import com.wckj.chasstage.common.util.DataQxbsUtil;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.yjlb.entity.ChasYjlb;
import com.wckj.chasstage.modules.yjlb.service.ChasYjlbService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.api.IApiBaseService;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.web.WebContext;
import com.wckj.jdone.modules.sys.util.DicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 预警类别
 */
@Service
public class ApiYjlbServiceImpl implements ApiYjlbService {
    @Autowired
    private ChasYjlbService apiService;
    @Autowired
    private ChasBaqService baqService;

    @Override
    public ApiReturnResult<?> get(String id) {
        ChasYjlb yjlb = apiService.findById(id);
        if(yjlb!=null){
            return ResultUtil.ReturnSuccess(yjlb);
        }
        return ResultUtil.ReturnError("无法根据id找到预警类别信息");
    }

    @Override
    public ApiReturnResult<?> save(YjlbBean bean) {
        try {
            ChasYjlb yjlb = new ChasYjlb();
            if(StringUtil.isEmpty(bean.getBaqid())){
                return ResultUtil.ReturnError("办案区id未设置");
            }
            ChasBaq baq = baqService.findById(bean.getBaqid());
            if(baq!=null){
                bean.setBaqmc(baq.getBaqmc());
            }
            if(StringUtil.isEmpty(bean.getBaqmc())){
                return ResultUtil.ReturnError("办案区名称未设置");
            }
            MyBeanUtils.copyBeanNotNull2Bean(bean, yjlb);
            yjlb.setId(StringUtils.getGuid32());
            yjlb.setIsdel(0);
            yjlb.setLrsj(new Date());
            yjlb.setXgsj(new Date());
            if(apiService.save(yjlb)>0){
                return ResultUtil.ReturnSuccess("保存成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.ReturnError("保存失败");
    }

    @Override
    public ApiReturnResult<?> update(YjlbBean bean) {
        try {
            ChasYjlb yjlb = apiService.findById(bean.getId());
            if(yjlb!=null){
                if(StringUtil.isEmpty(bean.getBaqid())){
                    return ResultUtil.ReturnError("办案区id未设置");
                }
                ChasBaq baq = baqService.findById(bean.getBaqid());
                if(baq!=null){
                    bean.setBaqmc(baq.getBaqmc());
                }
                if(StringUtil.isEmpty(bean.getBaqmc())){
                    return ResultUtil.ReturnError("办案区名称未设置");
                }
                if(StringUtil.isEmpty(bean.getYjlb())){
                    return ResultUtil.ReturnError("预警类别未设置");
                }
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
    public ApiReturnResult<?> getPageData(YjlbParam param) {
        Map<String, Object> map = MyBeanUtils.copyBean2Map(param);
        DataQxbsUtil.getSelectAll(apiService, map);
        if(WebContext.getSessionUser()!=null){
            String userRoleId = WebContext.getSessionUser().getRoleCode();
            if (!"0101".equals(userRoleId)) {
                String orgCode = WebContext.getSessionUser().getCurrentOrgCode();
                map.put("sydwdm", orgCode);
            }
        }
        PageDataResultSet<ChasYjlb> pageData = apiService.getEntityPageData(param.getPage(),
                param.getRows(), map, "xgsj desc");
        Map<String, Object> result = new HashMap<>();
        result.put("total",pageData.getTotal());
        result.put("rows", DicUtil.translate(pageData.getData(), new String[]{"YJLB","YJJB"}, new String[]{"yjlb","yjjb"}));
        return ResultUtil.ReturnSuccess(result);
    }
}
