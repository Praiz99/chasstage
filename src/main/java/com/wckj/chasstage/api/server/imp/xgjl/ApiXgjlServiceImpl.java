package com.wckj.chasstage.api.server.imp.xgjl;

import com.wckj.chasstage.api.def.baq.model.BaqBean;
import com.wckj.chasstage.api.def.baq.service.ApiBaqService;
import com.wckj.chasstage.api.def.xgjl.model.SdXgBean;
import com.wckj.chasstage.api.def.xgjl.model.XgjlBean;
import com.wckj.chasstage.api.def.xgjl.model.XgjlParam;
import com.wckj.chasstage.api.def.xgjl.service.ApiXgjlService;
import com.wckj.chasstage.api.def.xgpz.model.XgpzBean;
import com.wckj.chasstage.api.def.xgpz.model.XgpzParam;
import com.wckj.chasstage.api.def.xgpz.service.ApiXgpzService;
import com.wckj.chasstage.common.util.DataQxbsUtil;
import com.wckj.chasstage.common.util.DateTimeUtils;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.xgjl.entity.ChasXgjl;
import com.wckj.chasstage.modules.xgjl.service.ChasXgjlService;
import com.wckj.chasstage.modules.xgpz.entity.ChasXgpz;
import com.wckj.chasstage.modules.xgpz.service.ChasXgpzService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.web.WebContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 巡更记录
 */
@Service
public class ApiXgjlServiceImpl implements ApiXgjlService {
    @Autowired
    private ChasXgjlService apiService;
    @Autowired
    private ApiBaqService baqService;
    @Override
    public ApiReturnResult<?> get(String id) {
        ChasXgjl xgpz = apiService.findById(id);
        if(xgpz!=null){
            return ResultUtil.ReturnSuccess(xgpz);
        }
        return ResultUtil.ReturnError("无法根据id找到巡更信息");
    }

    @Override
    public ApiReturnResult<?> save(XgjlBean bean) {
        try {
            ChasXgjl yjlb = new ChasXgjl();
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
    public ApiReturnResult<?> update(XgjlBean bean) {
        try {
            ChasXgjl yjlb = apiService.findById(bean.getId());
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
    public ApiReturnResult<?> getPageData(XgjlParam param) {
        Map<String, Object> map = MyBeanUtils.copyBean2Map(param);

        DataQxbsUtil.getSelectAll(apiService, map);
        if(WebContext.getSessionUser()!=null){
            String userRoleId = WebContext.getSessionUser().getRoleCode();
            if (!"0101".equals(userRoleId)) {
                String orgCode = WebContext.getSessionUser().getCurrentOrgCode();
                map.put("sydwdm", orgCode);
            }
        }
        PageDataResultSet<ChasXgjl> pageData = apiService.getEntityPageData(param.getPage(),
                param.getRows(), map, "dksj desc");
        Map<String, Object> result = new HashMap<>();
        result.put("total",pageData.getTotal());
        result.put("rows", pageData.getData());
        return ResultUtil.ReturnSuccess(result);
    }

    @Override
    public ApiReturnResult<?> sddk(SdXgBean bean) {
        ChasXgjl chasXgjl = new ChasXgjl();
        try {
            chasXgjl.setId(StringUtils.getGuid32());
            chasXgjl.setLrsj(new Date());
            chasXgjl.setXgsj(new Date());
            if(WebContext.getSessionUser()!=null){
                chasXgjl.setLrrSfzh(WebContext.getSessionUser().getIdCard());
                chasXgjl.setXgrSfzh(WebContext.getSessionUser().getIdCard());
            }

            ApiReturnResult<BaqBean> baq = baqService.getBaqByLogin();
            if(baq!=null&&baq.getData()!=null){
                chasXgjl.setBaqid(baq.getData().getId());
                chasXgjl.setBaqmc(baq.getData().getBaqmc());
            }
            chasXgjl.setKh(bean.getKh());
            chasXgjl.setYcqk(bean.getYcqk());
            chasXgjl.setSbmc("手动打卡");
            chasXgjl.setDksj(bean.getDksj());
            chasXgjl.setQyid("b21380e56ca8bdde016ca8c57f2b0009");
            chasXgjl.setQymc("看守区");
            apiService.save(chasXgjl);
            return ResultUtil.ReturnSuccess("保存成功");

        } catch (Exception e) {
            return ResultUtil.ReturnError("手动巡更出错"+e.getMessage());
        }
    }
}
