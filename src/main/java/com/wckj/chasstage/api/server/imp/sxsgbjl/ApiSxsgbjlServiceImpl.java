package com.wckj.chasstage.api.server.imp.sxsgbjl;

import com.alibaba.fastjson.JSONObject;
import com.wckj.chasstage.api.def.sxsgbjl.model.SxsgbjlBean;
import com.wckj.chasstage.api.def.sxsgbjl.model.SxsgbjlParam;
import com.wckj.chasstage.api.def.sxsgbjl.service.ApiSxsgbjlService;
import com.wckj.chasstage.api.def.yjxx.model.YjxxBean;
import com.wckj.chasstage.api.def.yjxx.model.YjxxParam;
import com.wckj.chasstage.api.def.yjxx.service.ApiYjxxService;
import com.wckj.chasstage.common.util.DataQxbsUtil;
import com.wckj.chasstage.common.util.MyBeanUtils;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.gnpz.entity.ChasXtGnpz;
import com.wckj.chasstage.modules.gnpz.service.ChasXtGnpzService;
import com.wckj.chasstage.modules.qysljcjl.entity.ChasYwQysljcjl;
import com.wckj.chasstage.modules.qysljcjl.service.ChasYwQysljcjlService;
import com.wckj.chasstage.modules.sxsgbjl.entity.ChasSxsGbjl;
import com.wckj.chasstage.modules.sxsgbjl.service.ChasSxsGbjlService;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsKzService;
import com.wckj.chasstage.modules.yjxx.entity.ChasYjxx;
import com.wckj.chasstage.modules.yjxx.service.ChasYjxxService;
import com.wckj.chasstage.modules.znpz.entity.ChasXtBaqznpz;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.dic.DicUtil;
import com.wckj.framework.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 审讯室关闭记录
 */
@Service
public class ApiSxsgbjlServiceImpl implements ApiSxsgbjlService {
    @Autowired
    private ChasSxsGbjlService apiService;
    @Autowired
    private ChasSxsKzService sxsKzService;
    @Autowired
    private ChasYwQysljcjlService qysljcjlService;
    @Autowired
    private ChasXtBaqznpzService baqznpzService;
    @Autowired
    private ChasBaqService baqService;
    @Autowired
    private ChasXtGnpzService gnpzService;
    @Override
   public ApiReturnResult<?> get(String id) {
        ChasSxsGbjl yjxx = apiService.findById(id);
        if(yjxx!=null){
            return ResultUtil.ReturnSuccess(yjxx);
        }
        return ResultUtil.ReturnError("无法根据id找到审讯室关闭记录信息");
    }

    @Override
    public ApiReturnResult<?> save(SxsgbjlBean bean) {
        try {
            ChasSxsGbjl yjlb = new ChasSxsGbjl();
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
    public ApiReturnResult<?> update(SxsgbjlBean bean) {
        try {
            ChasSxsGbjl yjlb = apiService.findById(bean.getId());
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
    public ApiReturnResult<?> getPageData(SxsgbjlParam param) {
        Map<String, Object> map = MyBeanUtils.copyBean2Map(param);
        if(param.getPage()==null){
            param.setPage(1);
        }
        if(param.getRows()==null){
            param.setRows(10);
        }
        DataQxbsUtil.getSelectAll(apiService, map);
        PageDataResultSet<ChasSxsGbjl> pageData = apiService.getEntityPageData(param.getPage(),
                param.getRows(), map, "xgsj desc");
        Map<String, Object> result = new HashMap<>();
        result.put("total",pageData.getTotal());
        result.put("rows", pageData.getData());
        return ResultUtil.ReturnSuccess(result);
    }
    @Override
    public ApiReturnResult<?> getSyjlPageData(SxsgbjlParam param) {
        Map<String, Object> map = MyBeanUtils.copyBean2Map(param);
        Map<String, Object> result = new HashMap<>();
        if(param.getPage()==null){
            param.setPage(1);
        }
        if(param.getRows()==null){
            param.setRows(10);
        }
        DataQxbsUtil.getSelectAll(sxsKzService, map);

        ChasBaq baq = baqService.getBaqByLoginedUser();

        if(baq == null){
            PageDataResultSet<ChasSxsKz> sxskzs = sxsKzService.getSxssyjlApiPageData(param.getPage(), param.getRows(), map, "lrsj desc");
            List<Map<String, Object>> data = DicUtil.translate(sxskzs.getData(), new String[]{
                            "CHAS_ZD_CASE_RYLB"},
                    new String[]{"rylx"});
            result.put("total",sxskzs.getTotal());
            result.put("rows", data);
        }else{
            ChasXtBaqznpz baqznpz = baqznpzService.findByBaqid2(baq.getId());
            ChasXtGnpz gnpz = gnpzService.findById(baqznpz.getGnpzid());
            String gnpzJson = gnpz.getGnpz();
            Map<String,Object> gnpzMap = JSONObject.parseObject(gnpzJson, Map.class);
            String checkedLcgkzd = (String) gnpzMap.get("checkedLcgkzd");
            if(StringUtils.isNotEmpty(checkedLcgkzd)){
                if(checkedLcgkzd.contains("1")){
                    PageDataResultSet<Map<String,Object>> list = qysljcjlService.getEntityPageDataNew(param.getPage(), param.getRows(), map, "lrsj desc");
                    result.put("total",list.getTotal());
                    result.put("rows", DicUtil.translate(list.getData(),new String[]{"GNSSYRYLX"},new String[]{"rylx"}));
                    return ResultUtil.ReturnSuccess(result);
                }
            }
            PageDataResultSet<ChasSxsKz> sxskzs = sxsKzService.getSxssyjlApiPageData(param.getPage(), param.getRows(), map, "lrsj desc");
            List<Map<String, Object>> data = DicUtil.translate(sxskzs.getData(), new String[]{"CHAS_ZD_CASE_RYLB"}, new String[]{"rylx"});
            result.put("total",sxskzs.getTotal());
            result.put("rows", data);
        }
        return ResultUtil.ReturnSuccess(result);
    }
}
