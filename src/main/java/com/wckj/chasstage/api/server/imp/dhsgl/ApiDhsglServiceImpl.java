package com.wckj.chasstage.api.server.imp.dhsgl;

import com.wckj.chasstage.api.def.dhsgl.model.*;
import com.wckj.chasstage.api.def.dhsgl.service.ApiDhsglService;
import com.wckj.chasstage.common.util.ResultUtil;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.entity.ChasBaqref;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baq.service.ChasBaqrefService;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.gnpz.entity.ChasXtGnpz;
import com.wckj.chasstage.modules.gnpz.service.ChasXtGnpzService;
import com.wckj.chasstage.modules.ryjl.entity.ChasRyjl;
import com.wckj.chasstage.modules.ryjl.service.ChasRyjlService;
import com.wckj.chasstage.modules.ryxl.entity.ChasYwRyxl;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.ryxl.service.ChasYwRyxlService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.modules.dhsgl.entity.ChasDhsKz;
import com.wckj.chasstage.modules.dhsgl.service.ChasDhsKzService;
import com.wckj.chasstage.modules.dhsgl.service.ChasDhsglService;
import com.wckj.chasstage.modules.qygl.entity.ChasXtQy;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.yy.service.ChasYwYyService;
import com.wckj.chasstage.modules.znpz.entity.ChasXtBaqznpz;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import com.wckj.jdone.modules.sys.entity.JdoneSysRole;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.entity.JdoneSysUserRole;
import com.wckj.jdone.modules.sys.service.JdoneSysOrgService;
import com.wckj.jdone.modules.sys.service.JdoneSysRoleService;
import com.wckj.jdone.modules.sys.service.JdoneSysUserRoleService;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import com.wckj.jdone.modules.sys.util.DicUtil;
import com.wckj.jdone.modules.sys.util.SysUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.annotation.meta.param;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 等候室管理
 */
@Service
public class ApiDhsglServiceImpl implements ApiDhsglService {
    private static Logger log = Logger.getLogger(ApiDhsglServiceImpl.class);
    @Autowired
    private ChasBaqService chasBaqService;
    @Autowired
    private ChasBaqrefService chasBaqrefService;
    @Autowired
    private JdoneSysOrgService jdoneSysOrgService;
    @Autowired
    private ChasXtQyService qyService;
    @Autowired
    private ChasDhsKzService dhsKzService;
    @Autowired
    private ChasDhsglService dhsglService;
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private ChasRyjlService ryjlService;
    @Autowired
    private ChasYwRyxlService ryxlService;
    @Autowired
    private ChasYwYyService chasYwYyService;
    @Autowired
    private JdoneSysUserService userService;
    @Autowired
    private JdoneSysUserRoleService userRoleService;
    @Autowired
    private ChasXtBaqznpzService baqznpzService;
    @Autowired
    private ChasXtGnpzService gnpzService;
    @Override
    public ApiReturnResult<?> getDhsInfo(String baqid) {
        try {
            if(StringUtil.isEmpty(baqid)){
                List<DhsBean> list = getDhsListByUser();
                return ResultUtil.ReturnSuccess(list);
            }else{
                List<DhsBean> list = getDhsListByBaqid(baqid);
                return ResultUtil.ReturnSuccess(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取等候室分配情况出错", e);
        }
        return ResultUtil.ReturnError("获取等候室分配情况出错");
    }

    @Override
    public ApiReturnResult<?> getDhsRyInfo(DhsParam param) {
        List<DhsRyBean> list = getRyxxListByQyid(param);
        return ResultUtil.ReturnSuccess(list);
    }

    @Override
    public ApiReturnResult<?> getYfpInfo(DhsFpParam param) {
        DhsYfpBean yfpBean = getDhsFpxx(param.getQyid(), param.getRyid());
        return ResultUtil.ReturnSuccess(yfpBean);
    }

    @Override
    public ApiReturnResult<?> dhsfp(DhsFpParam param) {
        return assign(param.getQyid(),param.getRyid());
    }

    /**
     * 根据当前用户获取关联办案区id
     * @return
     */
    public String getBaqidByUser(){
        SessionUser user = WebContext.getSessionUser();
        if(user==null){
            return "notlogin";
        }
        Map<String,Object> params = new HashMap<>();
        params.put("sydwdm", user.getOrgCode());
        List<ChasBaqref> list = chasBaqrefService.findList(params, null);
        if(list==null||list.isEmpty()){
            return "notrefbaq";
        }
        return list.get(0).getBaqid();
    }

    /**
     * 获取办案区等候室列表
     * @return
     */
    public List<DhsBean> getDhsListByUser(){
        SessionUser user = WebContext.getSessionUser();
        if(user==null){
            return new ArrayList<>();
        }
        String baqid=chasBaqService.getZrBaqid();
        if(StringUtil.isEmpty(baqid)){
            return new ArrayList<>();
        }
        return getDhsListByBaqid(baqid);
//        Map<String,Object> params = new HashMap<>();
//        params.put("sydwdm", user.getOrgCode());
//        List<ChasBaqref> list = chasBaqrefService.findList(params, null);
//        if(list==null||list.isEmpty()){
//            return new ArrayList<>();
//        }
//        List<DhsBean> result= new ArrayList<>();
//        list.stream().forEach(baq->{
//            List<DhsBean> dtoList = getDhsListByBaqid(baq.getBaqid());
//            if(dtoList!=null&&!dtoList.isEmpty()){
//                result.addAll(dtoList);
//            }
//        });
 //       return result;
    }
    public List<DhsBean> getDhsListByBaqid(String baqid){
        if(StringUtils.isEmpty(baqid)){
            return null;
        }
        List<DhsBean> result= new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("baqid", baqid);
        map.put("fjlx", SYSCONSTANT.FJLX_DHS);
        List<ChasXtQy> qylist = qyService.findList(map, "ysbh asc");
        if(qylist!=null&&!qylist.isEmpty()){
            Collections.sort(qylist, (qy1,qy2)->{
                try{
                    int ysbh1=Integer.parseInt(qy1.getYsbh());
                    int ysbh2=Integer.parseInt(qy2.getYsbh());
                    return ysbh1-ysbh2;
                }catch (Exception e){

                }
                return -1;
            });
            List<DhsBean> dtoList = qylist.stream().map(qy -> {
                DhsBean dto = new DhsBean();
                dto.setId(qy.getYsid());
                dto.setQymc(qy.getQymc());
                dto.setFjlx(qy.getFjlx());
                dto.setKzlx(qy.getKzlx());
                dto.setRysl(qy.getRysl()==null?0:qy.getRysl());
                Map<String,Integer> rsmap=getDhsSyztNew(qy);
                if(rsmap !=null){
                    dto.setDqrs(rsmap.get("dqrs"));
                    dto.setWcnrs(rsmap.get("wcns"));
                    dto.setTsqtrs(rsmap.get("tsrqs"));
                    dto.setZdjhrs(rsmap.get("zdjhs"));
                }else{
                    dto.setDqrs(0);
                    dto.setWcnrs(0);
                    dto.setTsqtrs(0);
                    dto.setZdjhrs(0);
                }

                return dto;
            }).collect(Collectors.toList());
            if(dtoList!=null&&!dtoList.isEmpty()){
                result.addAll(dtoList);
            }
        }
        return result;
    }
    //获取等候室当前人数和特殊人群数
    /*
    public Map<String,Integer> getDhsSyzt(String dhsId){
        ChasXtQy qy = qyService.findByYsid(dhsId);
        Map<String,Integer> map = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("isdel", SYSCONSTANT.N_I);
        params.put("qyid", qy.getYsid());
        //params.put("fpzt", "1");
        params.put("baqid", qy.getBaqid());
        List<ChasDhsKz> dhsKzs = dhsKzService.findByParams(params);
        //map.put("dqrs", dhsKzs == null? 0:dhsKzs.size());
        List<String> tsqtList = new ArrayList<>();
        List<String> wcnList = new ArrayList<>();
        List<String> zdjhList = new ArrayList<>();
        map.put("dqrs", 0);
        dhsKzs.stream().forEach(dhskz->{
            //String baqid= dhskz.getBaqid();
            String rybh = dhskz.getRybh();
            //String wdbhL = dhskz.getWdbh();
            if(StringUtils.isNotEmpty(rybh)){
                ChasBaqryxx ryxx = baqryxxService.findByRybh(rybh);
                if(ryxx!=null&&ryxx.getBaqid().equals(dhskz.getBaqid())){
                    Integer dqrs = map.get("dqrs");
                    map.put("dqrs", dqrs+1);
                    if(StringUtils.isNotEmpty(ryxx.getTsqt())){
                        if(ryxx.getTsqt().contains(SYSCONSTANT.TSQT_WCN)){//未成年
                            wcnList.add(ryxx.getId());
                        }
                        if(ryxx.getTsqt().contains(SYSCONSTANT.TSQT_ZDJH)){//未成年
                            zdjhList.add(ryxx.getId());
                        }
                        if(!ryxx.getTsqt().contains("99")){//特殊群体
                            tsqtList.add(ryxx.getId());
                        }
                    }


                }
            }

        });
        map.put("tsrqs", tsqtList.size());
        map.put("wcns", wcnList.size());
        map.put("zdjhs", zdjhList.size());
        return map;
    }*/
    public Map<String,Integer> getDhsSyztNew(ChasXtQy qy){
        if(qy==null){
            return null;
        }
        Map<String,Integer> map = new HashMap<>();
        //DhsParam param = new DhsParam();
        //param.setDhsId(qy.getYsid());
        List<String> tsqtList = new ArrayList<>();
        List<String> wcnList = new ArrayList<>();
        List<String> zdjhList = new ArrayList<>();
        map.put("dqrs", 0);
        Map<String,Object> dhsMap = new HashMap<>();
        dhsMap.put("qyid", qy.getYsid());
        dhsMap.put("isdel", 0);
        List<ChasDhsKz> dhsKzs = dhsKzService.findList(dhsMap, "lrsj asc");
        if(dhsKzs!=null&&!dhsKzs.isEmpty()){
        	dhsKzs.stream().forEach(dhskz->{
                ChasBaqryxx ryxx = baqryxxService.findByRybh(dhskz.getRybh());
                if(ryxx!=null){
                    Integer dqrs = map.get("dqrs");
                    map.put("dqrs", dqrs+1);
                    if(StringUtils.isNotEmpty(ryxx.getTsqt())){
                        if(ryxx.getTsqt().contains(SYSCONSTANT.TSQT_WCN)){//未成年
                            wcnList.add(ryxx.getId());
                        }
                        if(ryxx.getTsqt().contains(SYSCONSTANT.TSQT_ZDJH)){//重点监护
                            zdjhList.add(ryxx.getId());
                        }
                        if(!ryxx.getTsqt().contains("99")){//特殊群体
                            tsqtList.add(ryxx.getId());
                        }
                    }
                }
        		
        	});
        }
//        List<DhsRyBean> baqryxxList = getRyxxListByQyid(param);
//        if(baqryxxList!=null&&!baqryxxList.isEmpty()){
//            baqryxxList.stream().forEach(ry->{
//                ChasBaqryxx ryxx = baqryxxService.findByRybh(ry.getRybh());
//                if(ryxx!=null){
//                    Integer dqrs = map.get("dqrs");
//                    map.put("dqrs", dqrs+1);
//                    if(StringUtils.isNotEmpty(ryxx.getTsqt())){
//                        if(ryxx.getTsqt().contains(SYSCONSTANT.TSQT_WCN)){//未成年
//                            wcnList.add(ryxx.getId());
//                        }
//                        if(ryxx.getTsqt().contains(SYSCONSTANT.TSQT_ZDJH)){//重点监护
//                            zdjhList.add(ryxx.getId());
//                        }
//                        if(!ryxx.getTsqt().contains("99")){//特殊群体
//                            tsqtList.add(ryxx.getId());
//                        }
//                    }
//                }
//            });
//        }
        map.put("tsrqs", tsqtList.size());
        map.put("wcns", wcnList.size());
        map.put("zdjhs", zdjhList.size());
        return map;
    }
    //根据等候室id查询当前等候室人员信息列表
    public List<DhsRyBean> getRyxxListByQyid(DhsParam param){
        if(StringUtil.isEmpty(param.getDhsId())){
            return null;
        }
        ChasXtQy qy = qyService.findByYsid(param.getDhsId());
        if(qy==null){
            return null;
        }
        param.setDhsId(qy.getYsid());
        List<ChasBaqryxx> baqryxxList = dhsKzService.selectRyxxBydhs(param);
        if(baqryxxList!=null&&!baqryxxList.isEmpty()){
            String minxlStr = SysUtil.getParamValue("RYXL_MIN_VALUE");
            String maxxlStr = SysUtil.getParamValue("RYXL_MAX_VALUE");
            ChasXtBaqznpz baqznpz = baqznpzService.findByBaqid2(qy.getBaqid());
            if(baqznpz != null && StringUtils.isNotEmpty(baqznpz.getGnpzid())){
                ChasXtGnpz gnpz = gnpzService.findById(baqznpz.getGnpzid());
                minxlStr = gnpz.getZdypzVule("RYXL_MIN_VALUE") == null ? minxlStr : gnpz.getZdypzVule("RYXL_MIN_VALUE");
                maxxlStr = gnpz.getZdypzVule("RYXL_MAX_VALUE") == null ? maxxlStr : gnpz.getZdypzVule("RYXL_MAX_VALUE");
            }
            if(StringUtils.isEmpty(minxlStr)){
                minxlStr="60";
            }
            if(StringUtils.isEmpty(maxxlStr)){
                maxxlStr="100";
            }
            int minval = Integer.parseInt(minxlStr);
            int maxval = Integer.parseInt(maxxlStr);
            SimpleDateFormat csrqf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return baqryxxList.stream()
                    .filter(ryxx->"01".equals(ryxx.getRyzt())||"02".equals(ryxx.getRyzt())
                    ||"03".equals(ryxx.getRyzt())||"05".equals(ryxx.getRyzt())||"06".equals(ryxx.getRyzt()))
                    .map(ryxx->{
                DhsRyBean info = new DhsRyBean();
                ChasRyjl ryjl = ryjlService.findRyjlByRybh(ryxx.getBaqid(), ryxx.getRybh());
                if(ryjl==null|| StringUtil.isEmpty(ryjl.getRybh())){
                    return info;
                }
                String appSfzh = param.getAppSfzh();
                if(StringUtils.isNotEmpty(appSfzh)){
                    JdoneSysUser user = userService.findSysUserByIdCard(appSfzh);
                    if(user != null){
                        String orgSysCode = user.getOrgSysCode();
                        String currentUserRoleId = user.getDefaultUserRoleId();
                        JdoneSysUserRole userRole = userRoleService.findById(currentUserRoleId);
                        String role = userRole.getRoleCode();
                        log.info("getRyxxListByQyid,角色代码role"+role);
                        if("400001".equals(role)){//办案民警
                            if(!user.getIdCard().equals(ryxx.getMjSfzh())){
                                return info;
                            }
                        }else if("400003".equals(role)){//办案单位领导
                            String substring = orgSysCode.substring(0, 8);
                            if(!substring.equals(ryxx.getZbdwBh().substring(0,8))){
                                return info;
                            }
                        }else if("300002".equals(role) || "200002".equals(role) || "100002".equals(role) || "300003".equals(role) || "200003".equals(role)){
                            String substring = orgSysCode.substring(0, 6);
                            if(!substring.equals(ryxx.getZbdwBh().substring(0,6))){
                                return info;
                            }
                        }
                    }
                }
                info.setRyid(ryxx.getId());
                info.setRybh(ryxx.getRybh());

                if(ryjl!=null){
                    info.setRyjlid(ryjl.getId());
                    info.setTagNo(ryjl.getWdbhL());
                    info.setYwbh(ryjl.getYwbh());
                }else{
                    info.setRyjlid("");
                    info.setTagNo("");
                    info.setYwbh("");
                }
                info.setImgUrl(getRyzpUrl(ryxx.getZpid()));
                info.setXl(getRyxl(ryxx.getRybh()));
                if(StringUtils.isNotEmpty(info.getXl())){
                    int xl = Integer.parseInt(info.getXl());
                    if(xl>=minval&&xl<=maxval){
                        info.setXlsfzc("1");
                    }else{
                        info.setXlsfzc("0");
                    }

                }else{
                    info.setXl("未知");
                    info.setXlsfzc("1");
                }
                info.setRyxm(ryxx.getRyxm());
                info.setRyxb(DicUtil.translate("CHAS_ZD_ZB_XB", ryxx.getXb()));
                info.setZjlx(DicUtil.translate("CHAS_ZD_CASE_ZJLX", ryxx.getZjlx()));
                info.setZjhm(ryxx.getRysfzh());
                if(ryxx.getCsrq()!=null){
                    info.setCsrq(csrqf.format(ryxx.getCsrq()));
                }else{
                    info.setCsrq("");
                }
                info.setHjszd(ryxx.getHjdxz());
                info.setRqyy(ryxx.getRyzaymc());
                info.setRqsj(sdf.format(ryxx.getRRssj()));
                info.setDafs(DicUtil.translate("DAFS", ryxx.getDafs()));
                info.setRylx(DicUtil.translate("CHAS_ZD_CASE_RYLB", ryxx.getRylx()));
                info.setTsqt(DicUtil.translate("CHAS_ZD_CASE_TSQT", ryxx.getTsqt()));
                info.setZbmj(ryxx.getMjXm());
                info.setRyzt(ryxx.getRyzt());
                ChasDhsKz dhsKz = dhsKzService.findById(ryjl.getDhsBh());
                if(dhsKz!=null){
                    info.setFpzt(dhsKz.getFpzt());
                }
                if(StringUtils.isEmpty(info.getFpzt())){
                    info.setFpzt("2");
                }
                return info;
            }).filter(info->info.getRybh()!=null&&!"".equals(info.getRybh())).collect(Collectors.toList());
        }
        return new ArrayList<>();
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
    public String getRyxl(String rybh){
        ChasYwRyxl ryxl = ryxlService.getLastestRyxlByRybh(rybh);
        if(ryxl!=null){
            return ryxl.getRyxl()==null?"":ryxl.getRyxl()+"";
        }
        return "";
    }

    public DhsYfpBean getDhsFpxx(String dhsid, String ryid){
        if(StringUtils.isEmpty(dhsid)|| StringUtils.isEmpty(ryid)){
            return DhsYfpBean.of(1,"参数错误");
        }
        ChasBaqryxx ryxx = baqryxxService.findById(ryid);
        ChasRyjl ryjl = ryjlService.findRyjlByRybh(ryxx.getBaqid(), ryxx.getRybh());
        if(ryjl==null){
            return DhsYfpBean.of(1,"人员记录信息为空");
        }
        String dhskzid=ryjl.getDhsBh();
        if(StringUtils.isNotEmpty(dhskzid)){
            ChasDhsKz dhsKz = dhsKzService.findById(dhskzid);
            if(dhsKz!=null &&dhsKz.getIsdel()==0){
                if(dhsid.equals(dhsKz.getQyid())){
                    ChasXtQy qy = qyService.findByYsid(dhsid);
                    return DhsYfpBean.of(2,ryxx.getRyxm()+"已经被分配在"+qy.getQymc()+",无需重复分配");
                }
            }
        }

        String ywbhs=ryxx.getAjbh();
        if(StringUtil.isEmpty(ywbhs)){
            ywbhs = ryxx.getJqbh();
        }
        if(StringUtil.isNotEmpty(ywbhs)){

            int tars=dhsKzService.getTaRs(dhsid, ywbhs, ryxx.getBaqid());
            if(tars>0){
                return DhsYfpBean.of(3,"同案人员不能看押在同一候问室");
            }

        }

        if("1".equals(ryxx.getXb())){//只有男才判断混关
            int hgrs=dhsKzService.getHgRs(dhsid, "2",ryxx.getBaqid());
            if(hgrs>0){
                return DhsYfpBean.of(4,"男女人员不能分配在同一候问室");
            }
        }else if("2".equals(ryxx.getXb())){//只有女才判断混关
            int hgrs=dhsKzService.getHgRs(dhsid, "1",ryxx.getBaqid());
            if(hgrs>0){
                return DhsYfpBean.of(4,"男女人员不能分配在同一候问室");
            }
        }
        ChasXtQy qy = qyService.findByYsid(dhsid);
        if(SYSCONSTANT.TSQT_WCN.equals(ryxx.getTsqt())&& SYSCONSTANT.DHSLX_PT.equals(qy.getKzlx())){
            return DhsYfpBean.of(6,"未成年人不能分配到此候问室");
        }
        if(!SYSCONSTANT.TSQT_WCN.equals(ryxx.getTsqt())&& SYSCONSTANT.DHSLX_WCN.equals(qy.getKzlx())){
            return DhsYfpBean.of(7,"成年人不能分配到未成年候问室");
        }
        if(!"99".equals(ryxx.getTsqt())&& SYSCONSTANT.DHSLX_PT.equals(qy.getKzlx())){
            return DhsYfpBean.of(5,"特殊人员不能分配到此普通候问室");
        }

        return DhsYfpBean.of(0,"人员可以正常分配到此等候室");
    }
    //手动调整等候室
    public ApiReturnResult<?> assign(String dhsid, String ryid) {
        if(StringUtils.isEmpty(dhsid)|| StringUtils.isEmpty(ryid)){
            return ResultUtil.ReturnError("参数错误");
        }
        ChasBaqryxx ryxx = baqryxxService.findById(ryid);
        if(!"01".equals(ryxx.getRyzt())){
            return ResultUtil.ReturnError("只能对在区人员分配等候室");
        }
        DevResult result = dhsglService.dhsFp(ryxx.getBaqid(), ryxx.getRybh(), dhsid);
        DhsYfpBean ajaxResult = new DhsYfpBean();
        if(result.getCode()==1){
            ajaxResult.setCode(0);
        }else if(result.getCode()==0){
            ajaxResult.setCode(1);
        }else{
            ajaxResult.setCode(result.getCode());
        }
        ajaxResult.setMessage(result.getMessage());
        ajaxResult.setData(result.getData());
        return ResultUtil.ReturnSuccess(ajaxResult);
    }
}
