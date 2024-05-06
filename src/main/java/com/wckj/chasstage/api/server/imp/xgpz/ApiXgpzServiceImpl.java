package com.wckj.chasstage.api.server.imp.xgpz;

import com.wckj.chasstage.api.def.xgpz.model.XgpzBean;
import com.wckj.chasstage.api.def.xgpz.model.XgpzParam;
import com.wckj.chasstage.api.def.xgpz.service.ApiXgpzService;
import com.wckj.chasstage.api.def.yjxx.model.YjxxBean;
import com.wckj.chasstage.api.def.yjxx.model.YjxxParam;
import com.wckj.chasstage.api.def.yjxx.service.ApiYjxxService;
import com.wckj.chasstage.common.util.DataQxbsUtil;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.xgpz.entity.ChasXgpz;
import com.wckj.chasstage.modules.xgpz.service.ChasXgpzService;
import com.wckj.chasstage.modules.yjxx.entity.ChasYjxx;
import com.wckj.chasstage.modules.yjxx.service.ChasYjxxService;
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
 * 巡更配置
 */
@Service
public class ApiXgpzServiceImpl implements ApiXgpzService {
    @Autowired
    private ChasXgpzService apiService;
    @Autowired
    private ChasBaqService baqService;
    @Override
    public ApiReturnResult<?> get(String id) {
        ChasXgpz xgpz = apiService.findById(id);
        if(xgpz!=null){
            return ResultUtil.ReturnSuccess(xgpz);
        }
        return ResultUtil.ReturnError("无法根据id找到巡更配置信息");
    }

    @Override
    public ApiReturnResult<?> save(XgpzBean bean) {
        try {
            initTime(bean);
            ChasXgpz yjlb = new ChasXgpz();
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
    public ApiReturnResult<?> update(XgpzBean bean) {
        try {
            initTime(bean);
            ChasXgpz yjlb = apiService.findById(bean.getId());
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
    private void initTime(XgpzBean bean){
        String jckssj1=bean.getJckssj1();
        if(StringUtils.isNotEmpty(jckssj1)){
            String[] strings = jckssj1.split(":");
            if(strings!=null&&strings.length==2){
                try {
                    bean.setJckssj(Integer.parseInt(strings[0]));
                    bean.setJckssjfz(Integer.parseInt(strings[1]));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    bean.setJckssj(0);
                    bean.setJckssjfz(0);
                }
            }
        }
        String jcjssj1=bean.getJcjssj1();
        if(StringUtils.isNotEmpty(jcjssj1)){
            String[] strings = jcjssj1.split(":");
            if(strings!=null&&strings.length==2){
                try {
                    bean.setJcjssj(Integer.parseInt(strings[0]));
                    bean.setJcjssjfz(Integer.parseInt(strings[1]));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    bean.setJcjssj(0);
                    bean.setJcjssjfz(0);
                }
            }
        }
    }
    private void setPageTime(ChasXgpz bean){
        if(bean.getJckssj()!=null&&bean.getJckssjfz()!=null){
            if(bean.getJckssjfz()<10){
                bean.setJckssj1(bean.getJckssj()+":0"+bean.getJckssjfz());
            }else{
                bean.setJckssj1(bean.getJckssj()+":"+bean.getJckssjfz());
            }

        }
        if(bean.getJcjssj()!=null&&bean.getJcjssjfz()!=null){
            if(bean.getJcjssjfz()<10){
                bean.setJcjssj1(bean.getJcjssj()+":0"+bean.getJcjssjfz());
            }else{
                bean.setJcjssj1(bean.getJcjssj()+":"+bean.getJcjssjfz());
            }
        }
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
    public ApiReturnResult<?> getPageData(XgpzParam param) {
        if(StringUtil.isEmpty(param.getBaqid())){
            String baqid =baqService.getZrBaqid();
            if(StringUtil.isEmpty(baqid)){
                return ResultUtil.ReturnError("当前登录人单位，未配置办案区！");
            }
            param.setBaqid(baqid);
        }
        Map<String, Object> map = MyBeanUtils.copyBean2Map(param);

//        DataQxbsUtil.getSelectAll(apiService, map);
//        if(WebContext.getSessionUser()!=null){
//            String userRoleId = WebContext.getSessionUser().getRoleCode();
//            if (!"0101".equals(userRoleId)) {
//                String orgCode = WebContext.getSessionUser().getCurrentOrgCode();
//                map.put("sydwdm", orgCode);
//            }
//        }
        PageDataResultSet<ChasXgpz> pageData = apiService.getEntityPageData(param.getPage(),
                param.getRows(), map, "xgsj desc");
        pageData.getData().stream().forEach(this::setPageTime);
        Map<String, Object> result = new HashMap<>();
        result.put("total",pageData.getTotal());
        result.put("rows", pageData.getData());
        return ResultUtil.ReturnSuccess(result);
    }
}
