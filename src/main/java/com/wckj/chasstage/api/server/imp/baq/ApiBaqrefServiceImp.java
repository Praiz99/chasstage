package com.wckj.chasstage.api.server.imp.baq;

import com.wckj.chasstage.api.def.baq.model.BaqrefBean;
import com.wckj.chasstage.api.def.baq.model.BaqrefParam;
import com.wckj.chasstage.api.def.baq.service.ApiBaqrefService;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.entity.ChasBaqref;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baq.service.ChasBaqrefService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.jdone.modules.sys.entity.JdoneSysOrg;
import com.wckj.jdone.modules.sys.service.JdoneSysOrgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wutl
 * @Title:
 * @Package
 * @Description:
 * @date 2020-9-920:56
 */
@Service
public class ApiBaqrefServiceImp implements ApiBaqrefService {
    private static final Logger log = LoggerFactory.getLogger(ApiBaqrefServiceImp.class);

    @Autowired
    private ChasBaqrefService chasBaqrefService;
    @Autowired
    private ChasBaqService baqService;
    @Autowired
    private JdoneSysOrgService orgService;

    /**
     * 保存办案区关联机构信息（单条）
     * @param bean
     * @return
     */
    @Override
    public ApiReturnResult<?> saveBaqRef(BaqrefBean bean) {
        try {
            chasBaqrefService.saveBaqRef(bean);
        } catch (Exception e) {
            log.error("ApiBaqServiceImp.saveBaqRef:" ,e);
            return ResultUtil.ReturnError("操作失败:"+e.getMessage());
        }
        return ResultUtil.ReturnSuccess("操作成功!");
    }

    /**
     * 保存当前办案区的关联信息
     * @param baqid
     * @param orgs
     * @return
     */
    @Override
    public ApiReturnResult<?> saveBatchRef(String baqid, String orgs) {
        try{
            Map<String,Object> result = chasBaqrefService.saveBatch(baqid, orgs);
            if(!(boolean)result.get("success")){
                return ResultUtil.ReturnError("操作失败:"+result.get("msg"));
            }
        }catch (Exception e){
            log.error("saveBatchRef:",e);
            return ResultUtil.ReturnError("操作失败:"+e.getMessage());
        }
        return ResultUtil.ReturnSuccess("操作成功!");
    }

    /**
     * 删除办案区关联信息
     * @param ids
     * @return
     */
    @Override
    public ApiReturnResult<?> deleteBaqRef(String ids) {
        try {
            chasBaqrefService.deleteByids(ids);
            return ResultUtil.ReturnSuccess("操作成功!");
        } catch (Exception e) {
            log.error("ApiBaqServiceImp.deleteBaqRef:" , e);
            return ResultUtil.ReturnError("操作失败!");
        }
    }

    /**
     * 获取办案区关联分页数据
     * @param param
     * @return
     */
    @Override
    public ApiReturnResult<?> getBaqrefPageData(BaqrefParam param) {
        Map<String,String> params = new HashMap<>();
        params.put("baqid",param.getBaqid());
        params.put("sydwxtbh",param.getSydwxtbh());
        return ResultUtil.ReturnSuccess(chasBaqrefService.getBaqrefPageData(param.getPage(),param.getRows(),params,null));
    }

    @Override
    public ApiReturnResult<?> getOrgsByBaqid(String baqid) {
        if (StringUtils.isEmpty(baqid)) {
            return ResultUtil.ReturnError("办案区ID不能为空!");
        }

        Map<String, Object> params = new HashMap<>();
        params.put("baqid", baqid);
        List<JdoneSysOrg> orgList = new ArrayList<>();
        List<ChasBaqref> baqrefs = chasBaqrefService.findList(params, null);
        for (ChasBaqref baqref : baqrefs) {
            params.clear();
            params.put("code", baqref.getSydwdm());
            List<JdoneSysOrg> orgs = orgService.findList(params, null);
            if (!orgs.isEmpty()) {
                //只取第一个，可能出现重复，所以不适用addAll
                orgList.add(orgs.get(0));
            }
        }
        if (orgList.isEmpty()) {
            return ResultUtil.ReturnError("暂无数据!");
        }
        return ResultUtil.ReturnSuccess(orgList);
    }
    @Override
    public ApiReturnResult<?> getOrgPageData(String baqid, String org,int page, int rows) {
        Map<String, Object> result = new HashMap<>();

        ChasBaq baq = baqService.findById(baqid);
        if(baq==null){
            result.put("total",0);
            result.put("rows",null);
            return ResultUtil.ReturnSuccess(result);
        }
        String dwdm = baq.getDwdm();
        if (StringUtils.isEmpty(dwdm)){
            result.put("total",0);
            result.put("rows",null);
            return ResultUtil.ReturnSuccess(result);
        }
        PageDataResultSet<JdoneSysOrg> list = null;
        Map<String, Object> params = new HashMap<>();
        List<String> relaOrgs = new ArrayList<>();
        // if(StringUtils.isEmpty(org)){
        // 已经是办案区子单位的org集合
        params.clear();
        params.put("dwdm", dwdm);
        List<ChasBaqref> refs = chasBaqrefService.findList(params, null);
        if (refs != null && refs.size() > 0) {
            for (ChasBaqref ref : refs) {
                relaOrgs.add(ref.getSydwdm());
            }
        }
        List<String> ids = getOrgs(dwdm, relaOrgs);
        if (StringUtils.isNotEmpty(org)) {
            ids = getOrgs(org, relaOrgs);
            if (ids.isEmpty()) {
                ids.add(org);
            }
        }
        // 查询下级单位
        if (ids == null || ids.size() == 0)
            ids.add("null");
        params.clear();
        params.put("Codes", ids.toArray());
        // }else{
        // //条件查询
        // params.clear();
        // String[] codes={org};
        // params.put("Codes",codes);
        // }
        list = orgService.getPageDataByMap(page, rows, params, null);
        result.put("total", list.getTotal());
        result.put("rows", list.getData());
        return ResultUtil.ReturnSuccess(result);
    }

    // 查询下级单位
    public List<String> getOrgs(String org, List<String> relaOrgs) {
        List<String> list = new ArrayList<String>();
        // 删除单位代码末尾所有的0
        org = org.replaceAll("0*$", "");
        // 所有子单位包含自身

        List<JdoneSysOrg> orgs = orgService.getChildOrgListByCode(org);
        // 去掉自身已经已经关联的子单位
        if (orgs == null || orgs.size() == 0)
            return list;
        for (JdoneSysOrg depart : orgs) {
            if (org.equals(depart.getCode()))
                continue;
            if (relaOrgs == null || relaOrgs.size() == 0
                    || !relaOrgs.contains(depart.getCode())) {
                list.add(depart.getCode());
            }
        }
        return list;
    }
}
