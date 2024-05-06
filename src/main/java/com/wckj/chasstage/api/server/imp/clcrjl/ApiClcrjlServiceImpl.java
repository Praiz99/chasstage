package com.wckj.chasstage.api.server.imp.clcrjl;

import com.wckj.chasstage.api.def.clcrjl.model.ClcrjlBean;
import com.wckj.chasstage.api.def.clcrjl.model.ClcrjlParam;
import com.wckj.chasstage.api.def.clcrjl.service.ApiClcrjlService;
import com.wckj.chasstage.api.def.cldj.model.CldjBean;
import com.wckj.chasstage.api.def.cldj.model.CldjParam;
import com.wckj.chasstage.api.def.cldj.service.ApiCldjService;
import com.wckj.chasstage.common.util.DataQxbsUtil;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.modules.clcrjl.entity.ChasYwClcrjl;
import com.wckj.chasstage.modules.clcrjl.service.ChasYwClcrjlService;
import com.wckj.chasstage.modules.cldj.entity.ChasXtCldj;
import com.wckj.chasstage.modules.cldj.service.ChasXtCldjService;
import com.wckj.framework.api.ApiReturnResult;
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
 * 车辆出入记录
 */
@Service
public class ApiClcrjlServiceImpl implements ApiClcrjlService {
    @Autowired
    private ChasYwClcrjlService apiService;

    @Override
    public ApiReturnResult<?> get(String id) {
        ChasYwClcrjl yjlb = apiService.findById(id);
        if(yjlb!=null){
            return ResultUtil.ReturnSuccess(yjlb);
        }
        return ResultUtil.ReturnError("无法根据id找到车辆出入记录信息");
    }

    @Override
    public ApiReturnResult<?> save(ClcrjlBean bean) {
        try {
            ChasYwClcrjl yjlb = new ChasYwClcrjl();
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
    public ApiReturnResult<?> update(ClcrjlBean bean) {
        try {
            ChasYwClcrjl yjlb = apiService.findById(bean.getId());
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
    public ApiReturnResult<?> getPageData(ClcrjlParam param) {
        Map<String, Object> map = MyBeanUtils.copyBean2Map(param);
        DataQxbsUtil.getSelectAll(apiService, map);
        if(WebContext.getSessionUser()!=null){
            String userRoleId = WebContext.getSessionUser().getRoleCode();
            if (!"0101".equals(userRoleId)) {
                String orgCode = WebContext.getSessionUser().getCurrentOrgCode();
                map.put("sydwdm", orgCode);
            }
        }
        PageDataResultSet<ChasYwClcrjl> pageData = apiService.getEntityPageData(param.getPage(),
                param.getRows(), map, "xgsj desc");
        Map<String, Object> result = new HashMap<>();
        result.put("total",pageData.getTotal());
        result.put("rows", pageData.getData());
        return ResultUtil.ReturnSuccess(result);
    }
}
