package com.wckj.chasstage.api.server.imp.xyHik;

import com.wckj.chasstage.api.def.sxsgl.model.SxsBean;
import com.wckj.chasstage.api.def.xyHik.service.ApiXyHikService;
import com.wckj.chasstage.api.def.yjxx.model.DpDataBean;
import com.wckj.chasstage.common.util.*;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baq.service.ChasBaqrefService;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxxBq;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsKzService;
import com.wckj.chasstage.modules.yjxx.entity.ChasYjxx;
import com.wckj.chasstage.modules.yjxx.service.ChasYjxxService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.dic.DicUtil;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.entity.JdoneSysUserRole;
import com.wckj.jdone.modules.sys.service.JdoneSysUserRoleService;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApiXyHikServiceImpl implements ApiXyHikService {
    protected Logger log = LoggerFactory.getLogger(ApiXyHikServiceImpl.class);

    @Autowired
    private ChasBaqService chasBaqService;
    @Autowired
    private ChasXtQyService qyService;
    @Autowired
    private ChasSxsKzService sxsKzService;
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private ChasSbService sbService;
    @Autowired
    private JdoneSysUserService userService;
    @Autowired
    private JdoneSysUserRoleService userRoleService;
    @Autowired
    private ChasBaqrefService chasBaqrefService;
    @Autowired
    private ChasYjxxService apiService;


    @Override
    public ApiReturnResult<?> getSxsInfo(String dwdm, String appSfzh, int page, int rows) {
        List<SxsBean> list ;
        String baqid = "";
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("sydwdm", dwdm);
        List<ChasBaq> baqList = chasBaqService.findList(param, null);
        if (baqList != null && !baqList.isEmpty()) {
            baqid = baqList.get(0).getId();
        }else {
            return ResultUtil.ReturnError("找不到办案区");
        }
        result.put("total", 0);
        result.put("rows", new ArrayList<SxsBean>());
        if(StringUtil.isEmpty(baqid)){
            list= getSxsSyqkByUser();
        }else{
            list = getSxsSyqk(baqid,appSfzh);
        }
        if(list!=null&&!list.isEmpty()){
            result.put("total", list.size());
            if(page>=1&&rows>0){
                list=list.stream().skip((page-1)*rows).limit(rows)
                        .collect(Collectors.toList());
            }

            result.put("rows", list);
        }
        return ResultUtil.ReturnSuccess(result);
    }

    @Override
    public ApiReturnResult<?> getBaqryxxDataGrid(String dwdm, String rssj1, String rssj2, int page, int rows) {
        String baqid = "";  //第三方接入
        Map<String, Object> params = new HashMap<String, Object>();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        params.put("dwdm", dwdm);
        ChasBaq baq = chasBaqService.findByParams(params);
        if (baq != null) {
            baqid = baq.getId();
        }else {
            return ResultUtil.ReturnError("找不到办案区");
        }
        params.clear();
        params.put("baqid", baqid);
        params.put("ryzt", "01");
        /*if (StringUtil.isEmpty(baqid)) {
            DataQxbsUtil.getSelectAll(baqryxxService, params);
        }*/
        DataQxbsUtil.getSelectAll(baqryxxService, params);
        try {
            Calendar c = Calendar.getInstance();
            if (StringUtils.isEmpty(rssj1) && StringUtils.isEmpty(rssj2)) {
                c.setTime(new Date());
                c.add(Calendar.DAY_OF_MONTH, -15);
                params.put("rssj1", sf.format(c.getTime()));
            } else if (StringUtils.isEmpty(rssj1) && StringUtils.isNotEmpty(rssj2)) {
                params.put("rssj2", rssj2);
                Date end = sf.parse(rssj2);
                c.setTime(end);
                c.add(Calendar.DAY_OF_MONTH, -15);
                params.put("rssj1", sf.format(c.getTime()));
            }else {
                params.put("rssj1", rssj1);
                Date start = sf.parse(rssj1);
                c.setTime(start);
                c.add(Calendar.DAY_OF_MONTH, 15);
                params.put("rssj2", sf.format(c.getTime()));
            }
        }catch (Exception e){
            e.printStackTrace();

        }
        PageDataResultSet<ChasBaqryxxBq> qt = baqryxxService.getEntityPageDataBecauseQymc(page, rows, params, " r_rssj desc,id ");
        //设置心率状态
        List<Map<String, Object>> data = DicUtil.translate(qt.getData(), new String[]{
                        "CHAS_ZD_ZB_XB", "CHAS_ZD_CASE_TSQT", "RSYY", "RYZT",
                        "ZD_SYS_ORG_CODE_SNAME", "CSYY", "BAQRYSSWPZT", "DAFS", "CHAS_ZD_CASE_ZJLX", "CHAS_ZD_CASE_RYLB"},
                new String[]{"xb", "tsqt", "ryZaybh", "ryzt", "zbdwBh", "cCsyy", "sswpzt", "dafs", "zjlx", "rylx"});
        for(Map<String, Object> item : data){
            if(StringUtils.isNotEmpty((String) item.get("zpid"))){
                FileInfoObj fileInfoObj = FrwsApiForThirdPart.getFileInfoByBizId((String) item.get("zpid"));
                if(null != fileInfoObj) {
                    item.put("zpUrl", fileInfoObj.getDownUrl());
                }
            }else{
                item.put("zpUrl", "");
            }
        }
        return ResultUtil.ReturnSuccess(ParamUtil.getDataGrid(qt.getTotal(), data));
    }

    @Override
    public ApiReturnResult<?> getAlarmData(String dwdm) {
        try {
            Map<String,Object> map= new HashMap<>();
            map.put("dwdm", dwdm);
            ChasBaq baq = null;
            List<ChasBaq> baqList = chasBaqService.findListByParams(map);
            if (baqList.size() > 0) {
                baq = baqList.get(0);
            }
            if(baq==null){
                return ResultUtil.ReturnError(dwdm+"单位编号没有关联办案区");
            }
            map.clear();
            map.put("baqid", baq.getId());
            map.put("yjzt","0");
            List<ChasYjxx> yjxxList = apiService.findList(map, "xgsj desc");
            return ResultUtil.ReturnSuccess(yjxxList.size());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取大屏预警统计数据出错", e);
        }
        return ResultUtil.ReturnError("获取大屏预警统计数据出错");
    }

    @Override
    public ApiReturnResult<?> getRyxxDataTotal(String dwdm) {
        try {
            String baqid = "";  //第三方接入
            Map<String, Object> params = new HashMap<String, Object>();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            params.put("dwdm", dwdm);
            ChasBaq baq = chasBaqService.findByParams(params);
            if (baq != null) {
                baqid = baq.getId();
            }else {
                return ResultUtil.ReturnError("找不到办案区");
            }
            params.clear();
            params.put("baqid", baqid);
            params.put("ryzt", "01");
        /*if (StringUtil.isEmpty(baqid)) {
            DataQxbsUtil.getSelectAll(baqryxxService, params);
        }*/
            DataQxbsUtil.getSelectAll(baqryxxService, params);
            List<ChasBaqryxx> qt = baqryxxService.findList(params, " r_rssj desc,id ");
            return ResultUtil.ReturnSuccess(qt.size());
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取大屏人员统计数据出错", e);
        }
        return ResultUtil.ReturnError("获取大屏人员统计数据出错");
    }


    public List<SxsBean> getSxsSyqkByUser(){
        SessionUser user = WebContext.getSessionUser();
        if(user==null){
            return new ArrayList<>();
        }
        String baqid=chasBaqService.getZrBaqid();
        if(StringUtil.isEmpty(baqid)){
            return new ArrayList<>();
        }
        return getSxsSyqk(baqid,"");
//        Map<String,Object> params = new HashMap<>();
//        params.put("sydwdm", user.getOrgCode());
//        List<ChasBaqref> list = chasBaqrefService.findList(params, null);
//        if(list==null||list.isEmpty()){
//            return new ArrayList<>();
//        }
//        List<SxsBean> result= new ArrayList<>();
//        list.stream().forEach(baq->{
//            List<SxsBean> dtoList = getSxsSyqk(baq.getBaqid());
//            if(dtoList!=null&&!dtoList.isEmpty()){
//                result.addAll(dtoList);
//            }
//        });
//        return result;
    }
    public List<SxsBean> getSxsSyqk(String baqid,String appSfzh){
        List<SxsBean> result=new ArrayList<>();
        //1、查找办案区所有审讯室
        Map<String,Object> map = new HashMap<>();
        map.put("baqid",baqid);
        map.put("fjlx","3");
        map.put("isdel",0);
        map.put("cusorder",0);
        List<ChasXtQy> sxsList = qyService.findByParams(map);
        Collections.sort(sxsList, (qy1, qy2)->{
            try{
                int ysbh1=Integer.parseInt(qy1.getYsbh());
                int ysbh2=Integer.parseInt(qy2.getYsbh());
                return ysbh1-ysbh2;
            }catch (Exception e){

            }
            return -1;
        });
        if(sxsList!=null&&!sxsList.isEmpty()){
            //2、查询每个审讯室分配情况
            for(ChasXtQy sxs:sxsList){
                map.clear();
                map.put("baqid",baqid);
                map.put("isdel",0);
                map.put("qyid",sxs.getYsid());
                List<ChasSxsKz> sxsKzList = sxsKzService.findList(map,"kssj desc");
                if(sxsKzList==null||sxsKzList.isEmpty()){//审讯室空闲
                    result.add(kxzt(sxs));
                }else{
                    ChasSxsKz sxskz = sxsKzList.get(0);
                    if(StringUtil.isNotEmpty(sxskz.getRybh())){
                        //使用中
                        ChasBaqryxx ryxx = baqryxxService.findByRybh(sxskz.getRybh());
                        if(ryxx!=null){
                            if(!"04".equals(ryxx.getRyzt())){
                                if(StringUtils.isNotEmpty(appSfzh)){
                                    boolean b = false;
                                    JdoneSysUser user = userService.findSysUserByIdCard(appSfzh);
                                    String orgSysCode = user.getOrgSysCode();
                                    String currentUserRoleId = user.getDefaultUserRoleId();
                                    JdoneSysUserRole userRole = userRoleService.findById(currentUserRoleId);
                                    String role = userRole.getRoleCode();
                                    log.info("getSxsSyqk,角色代码role"+role);
                                    if("400001".equals(role)){//办案民警
                                        if(user.getIdCard().equals(ryxx.getMjSfzh())){
                                            b = true;
                                        }
                                    }else if("400003".equals(role)){//办案单位领导
                                        String substring = orgSysCode.substring(0, 8);
                                        if(substring.equals(ryxx.getZbdwBh().substring(0,8))){
                                            b = true;
                                        }
                                    }else if("300002".equals(role) || "200002".equals(role) || "100002".equals(role) || "300003".equals(role) || "200003".equals(role)){
                                        String substring = orgSysCode.substring(0, 6);
                                        if(substring.equals(ryxx.getZbdwBh().substring(0,6))){
                                            b = true;
                                        }
                                    }
                                    if(b){
                                        result.add(syzt(sxs,ryxx,sxskz));
                                    }else{
                                        result.add(zyzt(sxs, sxskz));
                                    }
                                }else{
                                    result.add(syzt(sxs,ryxx,sxskz));
                                }
                            }else{
                                //人员出区，审讯室变为空闲
                                result.add(kxzt(sxs));
                            }
                        }else{
                            //找不到人员信息，审讯室变为空闲
                            result.add(kxzt(sxs));
                        }
                    }else{
                        //占用(继续用电)
                        result.add(zyzt(sxs, sxskz));
                    }
                }
            }
        }
        return result;
    }

    private SxsBean kxzt(ChasXtQy sxs){
        SxsBean info = new SxsBean();
        info.setId(sxs.getYsid());
        info.setQymc(sxs.getQymc());
        info.setFjlx(sxs.getFjlx());
        info.setKzlx(sxs.getKzlx());
        info.setYsbh(sxs.getYsbh());
        info.setRysl(0);
        info.setKssj("");
        info.setPfszt(getPfszt(sxs));
        info.setStartTime(-1);
        return info;
    }

    private String getPfszt(ChasXtQy sxs){
        Map<String, Object> map = new HashMap<>();
        map.put("baqid", sxs.getBaqid());
        map.put("sblx", SYSCONSTANT.SBLX_JDQ);
        map.put("qyid", sxs.getYsid());
        map.put("sbgn","10");//排风扇
        List<ChasSb> chasSblist = sbService.findByParams(map);
        if (chasSblist != null&&!chasSblist.isEmpty()) {
            String zt= chasSblist.get(0).getKzcs3();
            if(StringUtil.isEmpty(zt)||!"0".equals(zt)){
                zt="1";
            }
            return zt;
        }
        return "1";
    }

    private SxsBean syzt(ChasXtQy sxs,ChasBaqryxx ryxx,ChasSxsKz sxskz){
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SxsBean info = new SxsBean();
        info.setId(sxs.getYsid());
        info.setQymc(sxs.getQymc());
        info.setFjlx(sxs.getFjlx());
        info.setKzlx(sxs.getKzlx());
        info.setYsbh(sxs.getYsbh());
        info.setRysl(1);
        info.setRyid(ryxx.getId());
        info.setRyxm(ryxx.getRyxm());
        info.setRybh(ryxx.getRybh());
        info.setRyxb(DicUtil.translate("CHAS_ZD_ZB_XB", ryxx.getXb()));
        info.setRqyy(ryxx.getRyzaymc());
        info.setZbmj(ryxx.getMjXm());
        if(ryxx.getRRssj()!=null){
            info.setRssj(sdf.format(ryxx.getRRssj()));
        }else{
            info.setRssj("");
        }
        info.setPfszt(getPfszt(sxs));
        info.setRylx(DicUtil.translate("CHAS_ZD_CASE_RYLB", ryxx.getRylx()));
        info.setKssj(sdf.format(sxskz.getKssj()));
        info.setStartTime(sxskz.getKssj().getTime());
        info.setRyzp(getRyzpUrl(ryxx.getZpid()));
        info.setTsqt(DicUtil.translate("CHAS_ZD_CASE_TSQT", ryxx.getTsqt()));
        return info;
    }

    public String getRyzpUrl(String zpid){
        //String defaultUrl="/static/framework/plugins/com/images/assets/nopic.jpg";
        String defaultUrl="";
        if(StringUtils.isEmpty(zpid)){
            return defaultUrl;
        }
        try {
            FileInfoObj fileList = FrwsApiForThirdPart.getFileInfoByBizId(zpid);
            if(fileList != null){
                return fileList.getDownUrl();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultUrl;
    }

    private SxsBean zyzt(ChasXtQy sxs,ChasSxsKz sxskz){
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SxsBean info = new SxsBean();
        info.setId(sxs.getYsid());
        info.setQymc(sxs.getQymc());
        info.setFjlx(sxs.getFjlx());
        info.setKzlx(sxs.getKzlx());
        info.setYsbh(sxs.getYsbh());
        info.setRysl(1);
        info.setRyid("");
        info.setRyxm("");
        info.setRybh("");
        info.setRyxb("");
        info.setRqyy("");
        info.setRssj("");
        info.setZbmj("");
        info.setRssj("");
        info.setRylx("");
        info.setKssj(sdf.format(sxskz.getKssj()));
        info.setStartTime(sxskz.getKssj().getTime());
        info.setRyzp("");
        info.setTsqt("");
        info.setPfszt(getPfszt(sxs));
        return info;
    }
}
