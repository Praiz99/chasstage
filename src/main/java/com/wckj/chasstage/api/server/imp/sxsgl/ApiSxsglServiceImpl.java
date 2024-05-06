package com.wckj.chasstage.api.server.imp.sxsgl;

import com.wckj.chasstage.api.def.sxsgl.model.SxsBean;
import com.wckj.chasstage.api.def.sxsgl.model.SxsFpParam;
import com.wckj.chasstage.api.def.sxsgl.service.ApiSxsglService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.common.util.ParamUtil;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsKzService;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsglService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.dic.DicUtil;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import com.wckj.jdone.modules.sys.entity.JdoneSysRole;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.entity.JdoneSysUserRole;
import com.wckj.jdone.modules.sys.service.JdoneSysRoleService;
import com.wckj.jdone.modules.sys.service.JdoneSysUserRoleService;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 审讯室管理
 */
@Service
public class ApiSxsglServiceImpl implements ApiSxsglService {
    protected Logger log = LoggerFactory.getLogger(ApiSxsglServiceImpl.class);
    @Autowired
    private ChasSxsglService sxsglService;
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
    @Override
    public ApiReturnResult<?> getSxsInfo(String baqid, String appSfzh, int page, int rows) {
        List<SxsBean> list ;
        Map<String, Object> result = new HashMap<>();
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
    public ApiReturnResult<?> open(String id) {
        if(StringUtil.isEmpty(id)){
            return ResultUtil.ReturnError("参数错误");
        }
        try {
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map<String, Object> map = sxsglService.open(id);
            Date now= new Date();
            map.put("kssj",sdf.format(now));
            map.put("starttime",now.getTime());
            if((Boolean)map.get("success")){
                return ResultUtil.ReturnSuccess("打开成功!", map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.ReturnError("打开失败");
    }

    @Override
    public ApiReturnResult<?> close(String id) {
        if(StringUtil.isEmpty(id)){
            return ResultUtil.ReturnError("参数错误");
        }

        try {
            Map<String, Object> map = sxsglService.close(id);
            if((Boolean)map.get("success")){
                return ResultUtil.ReturnSuccess("关闭成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.ReturnError("关闭失败");
    }

    @Override
    public ApiReturnResult<?> getSxsSxtInfo(String id) {

        if(StringUtils.isEmpty(id)){
            return ResultUtil.ReturnError("参数错误");
        }
        try {
            Map map = new HashMap();
            map.put("qyid",id);
            map.put("sbgn","42");
            List<ChasSb> list = sbService.findByParams(map);
            if(list!=null&&!list.isEmpty()){
                map.clear();
                String userpwd=list.get(0).getKzcs4();
                String[] strings = userpwd.split(":");
                map.put("ip", list.get(0).getKzcs2());
                map.put("port", list.get(0).getKzcs3());
                map.put("username", strings[0]);
                map.put("password", strings[1]);
                return ResultUtil.ReturnSuccess("获取审讯室摄像头成功", map);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取审讯室摄像头出错", e);

        }
        return ResultUtil.ReturnError("获取审讯室摄像头失败");
    }

    @Override
    public ApiReturnResult<?> sxsfp(SxsFpParam param, HttpServletRequest request) {
        if(StringUtils.isEmpty(param.getQyid())|| StringUtils.isEmpty(param.getRyid())){
            return ResultUtil.ReturnError("参数错误");
        }
        ChasBaqryxx ryxx = baqryxxService.findById(param.getRyid());
        if(!"01".equals(ryxx.getRyzt())){
            return ResultUtil.ReturnError("只能对在区人员分配审讯室");
        }
        DevResult result = sxsglService.sxsFp(ryxx.getBaqid(), ryxx.getRybh(), param.getQyid(),param.getUseSxsMjxm(),request);
        if(result.getCode()==1){
            return ResultUtil.ReturnSuccess(result.getMessage(), result.getData());
        }
        return ResultUtil.ReturnError(result.getMessage());
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
        Collections.sort(sxsList, (qy1,qy2)->{
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

    @Override
    public ApiReturnResult<?> openPfs(String id) {
        try {
            Map<String, Object> map = sxsglService.openPfs(id);
            if(map!=null&&map.containsKey("success")){
                boolean success= (boolean) map.get("success");
                if(success){
                    return ResultUtil.ReturnSuccess("打开排风扇成功");
                }else{
                    return ResultUtil.ReturnError(map.get("msg").toString());
                }
            }
            return ResultUtil.ReturnError("打开排风扇失败");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.ReturnError("打开排风扇失败");
    }

    @Override
    public ApiReturnResult<?> closePfs(String id) {
        try {
            Map<String, Object> map = sxsglService.closePfs(id);
            if(map!=null&&map.containsKey("success")){
                boolean success= (boolean) map.get("success");
                if(success){
                    return ResultUtil.ReturnSuccess("关闭排风扇成功");
                }else{
                    return ResultUtil.ReturnError(map.get("msg").toString());
                }
            }
            return ResultUtil.ReturnError("关闭排风扇失败");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.ReturnError("关闭排风扇失败");
    }

    @Override
    public ApiReturnResult<?> findSxsqkByQyid(String qyid) {
        if(StringUtils.isEmpty(qyid)){
            return ResultUtil.ReturnError("qyid不能为空");
        }
        ChasXtQy sxs = qyService.findByYsid(qyid);
        if(sxs == null){
            return ResultUtil.ReturnSuccess("区域不存在");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("isdel",0);
        map.put("qyid",sxs.getYsid());
        List<ChasSxsKz> sxsKzList = sxsKzService.findList(map,"kssj desc");
        SxsBean sxsAreaSyqk = new SxsBean();
        if(sxsKzList==null||sxsKzList.isEmpty()){//审讯室空闲
            sxsAreaSyqk = kxzt(sxs);
        }else{
            ChasSxsKz sxskz = sxsKzList.get(0);
            if(StringUtil.isNotEmpty(sxskz.getRybh())){
                //使用中
                ChasBaqryxx ryxx = baqryxxService.findByRybh(sxskz.getRybh());
                if(ryxx!=null){
                    if(!"04".equals(ryxx.getRyzt())){
                        sxsAreaSyqk = syzt(sxs,ryxx,sxskz);
                    }else{
                        //人员出区，审讯室变为空闲
                        sxsAreaSyqk = kxzt(sxs);
                    }
                }else{
                    //找不到人员信息，审讯室变为空闲
                    sxsAreaSyqk = kxzt(sxs);
                }
            }else{
                //占用(继续用电)
                sxsAreaSyqk = zyzt(sxs,sxskz);
            }
        }
        if(sxsAreaSyqk == null){
            return ResultUtil.ReturnSuccess("区域不存在");
        }
        return ResultUtil.ReturnSuccess(sxsAreaSyqk.getRysl() == 0 ? "空闲":"占用");
    }

    @Override
    public ApiReturnResult<?> obtainPersonnelInformationBySxsId(String qyid) {
        if(StringUtils.isEmpty(qyid)){
            return ResultUtil.ReturnError("qyid参数不能为空");
        }
        List<ChasSxsKz> sxsKzs = sxsKzService.findByParams(ParamUtil.builder().accept("isdel", 0).accept("qyid",qyid).toMap());
        if (!sxsKzs.isEmpty()) {
            ChasSxsKz chasSxsKz = sxsKzs.get(0);
            ChasBaqryxx baqryxx = baqryxxService.findByRybh(chasSxsKz.getRybh());
            if(baqryxx == null){
                return ResultUtil.ReturnError("人员信息为空");
            }
            Map<String,Object> result = new HashMap<>();
            result.put("zbmjXm",baqryxx.getMjXm());
            result.put("zbmjSfzh",baqryxx.getMjSfzh());
            result.put("baqryxx",baqryxx);
            return ResultUtil.ReturnSuccess(result);
        }
        return ResultUtil.ReturnSuccess("该审讯室为空");
    }

    @Override
    public ApiReturnResult<?> getAreaByBaqid(int page, int rows, String baqid, String sxsZt) {
        if(StringUtils.isEmpty(baqid)){
            return ResultUtil.ReturnError("接口核心参数不能为空[baqid,page,rows]");
        }
        List<SxsBean> result= new ArrayList<>();
        List<SxsBean> sxsSyqk1 = getSxsSyqk(baqid, "");
        if (StringUtils.isNotEmpty(sxsZt)) {
            sxsSyqk1 = sxsSyqk1.stream().filter(sxsSyqk -> {
                if ("01".equals(sxsZt)) {
                    return sxsSyqk.getRysl() == 0;
                } else {
                    return sxsSyqk.getRysl() > 0;
                }
            }).collect(Collectors.toList());
        }
        if (sxsSyqk1 != null && !sxsSyqk1.isEmpty()) {
            result.addAll(sxsSyqk1);
        }
        Map<String,Object> resultMap = new HashMap<>();
        if(result.isEmpty()){
            resultMap.put("total", 0);
            resultMap.put("rows", new ArrayList<SxsBean>());
        }else{
            resultMap.put("total", result.size());
            long pagel = Long.parseLong(page+"");
            long rowsl = Long.parseLong(rows+"");
            result = result.stream().skip((pagel - 1) * rowsl).limit(rowsl).collect(Collectors.toList());
            resultMap.put("rows", result);
        }
        return ResultUtil.ReturnSuccess(resultMap);
    }

    /**
     * 校验所有参数值是否符合给定的值
     * @param request
     * @param key
     * @param values
     * @return
     */
    public String checkParameterValue(HttpServletRequest request,String key,String... values){
        String parameter = request.getParameter(key);
        if(StringUtils.isEmpty(parameter)){
            return "";
        }
        boolean greenLight = false;
        for (String value : values) {
            if(StringUtils.equals(value,parameter)){
                greenLight = true;
            }
        }
        return greenLight ? "" : key+"参数值未知";
    }
}
