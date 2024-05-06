package com.wckj.chasstage.api.server.release.rest.map;


import com.wckj.chasstage.api.def.map.model.MapPosInfo;
import com.wckj.chasstage.api.server.imp.map.ChasRygjmapService;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.entity.ChasBaqref;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.modules.baq.service.ChasBaqrefService;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.fkgl.entity.ChasYwFkdj;
import com.wckj.chasstage.modules.fkgl.service.ChasYwFkdjService;
import com.wckj.chasstage.modules.mjgl.entity.ChasYwMjrq;
import com.wckj.chasstage.modules.mjgl.service.ChasYwMjrqService;
import com.wckj.chasstage.modules.qygl.service.ChasXtQyService;
import com.wckj.chasstage.modules.rygj.dao.ChasRygjSnapMapper;
import com.wckj.chasstage.modules.rygj.entity.ChasRygj;
import com.wckj.chasstage.modules.rygj.entity.ChasRygjSnap;
import com.wckj.chasstage.modules.rygj.service.ChasYwRygjService;
import com.wckj.chasstage.modules.sbgl.entity.ChasSb;
import com.wckj.chasstage.modules.sbgl.service.ChasSbService;
import com.wckj.chasstage.modules.sswp.entity.ChasSswpxx;
import com.wckj.chasstage.modules.sswp.service.ChasSswpxxService;
import com.wckj.chasstage.modules.sxsgl.entity.ChasSxsKz;
import com.wckj.chasstage.modules.sxsgl.service.ChasSxsKzService;
import com.wckj.chasstage.modules.yjxx.service.ChasYjxxService;
import com.wckj.chasstage.modules.ythcjqk.entity.ChasythcjQk;
import com.wckj.chasstage.modules.ythcjqk.service.ChasYthcjQkService;
import com.wckj.framework.api.annotation.ApiAccessNotValidate;
import com.wckj.framework.core.data.set.PageDataResultSet;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.jdone.modules.sys.util.DicUtil;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 人员轨迹地图控制器
 */
@Controller
@RequestMapping("/api/rest/chasstage/rygjMap/apiService")
public class ApiRygjMapController {
    private static final Logger log = LoggerFactory.getLogger(ApiRygjMapController.class);

    @Autowired
    private ChasYwRygjService rygjService;
    @Autowired
    private ChasBaqryxxService baqryxxService;
    @Autowired
    private ChasRygjmapService rygjmapService;
    @Autowired
    private ChasBaqrefService baqrefService;
    @Autowired
    private ChasBaqService chasBaqService;
    @Autowired
    private ChasYwMjrqService mjrqService;
    @Autowired
    private ChasYwFkdjService fkdjService;
    @Autowired
    private ChasXtQyService qyService;
    @Autowired
    private ChasSbService sbService;
    @Autowired
    protected ChasYjxxService chasYjxxService;
    @Autowired
    private ChasSswpxxService sswpxxService;
    @Autowired
    private ChasYthcjQkService ythcjQkService;
    @Autowired
    private ChasRygjSnapMapper snapMapper;
    @Autowired
    private ChasSxsKzService sxsKzService;


    //根据当前登录用户信息获取办案区
    private ChasBaq getBaqByOrgCode(String org){
        if(StringUtils.isEmpty(org)){
            log.error("当前单位编号为空,无法获取办案区");
            return null;
        }
        // 办案区字典 后台获取
        Map<String, Object> param = new HashMap<>(16);
        param.put("sydwdm", org);
        List<ChasBaqref> baqList = baqrefService.findList(param, "");
        if(baqList == null||baqList.isEmpty()){
            log.error("当前单位"+org+"没有关联办案区,无法获取办案区");
            return null;
        }
        ChasBaqref chasBaqref = baqList.get(0);
        ChasBaq baqTemp = chasBaqService.findById(chasBaqref.getBaqid());
        return baqTemp;
    }
    /**
     * 通用大屏界面入口
     * @param orgCode 单位编号 必须关联办案区
     * @param name jsp页面上级目录名称（不含中文）
     * @param model
     * @return
     */
    @RequestMapping("/showCommonBigscreen")
    @ApiAccessNotValidate
    public String showCommonBigscreen(String orgCode, String name, Model model){
        ChasBaq baq=getBaqByOrgCode(orgCode);
        model.addAttribute("orgCode", orgCode);
        model.addAttribute("baqid", baq.getId());
//        String totalRS = SysUtil.getParamValue("BAQ_TOTAL_ZSRS");
//        model.addAttribute("totalRS", totalRS);
        return "chasstage/htgl/bigscreen/"+name+"/map";
    }

    /**
     * 通用大屏界面入口
     * @param baqid 单位编号 必须关联办案区
     * @param name jsp页面上级目录名称（不含中文）
     * @param model
     * @return
     */
    @RequestMapping("/showBigscreen")
    @ApiAccessNotValidate
    public String showBigscreen(String baqid,String qy ,String name, Model model){
        model.addAttribute("baqid", baqid);
        return "chasstage/htgl/bigscreen/"+qy+"/"+name;
    }

    @RequestMapping("/getPosBybaqid")
    @ApiAccessNotValidate
    @ResponseBody
    public Map<String,Object> getPosBybaqid(HttpServletRequest request){
        String baqid = request.getParameter("baqid");
        Map<String,Object> result = new HashMap<>();
        try {
            if(StringUtil.isNotEmpty(baqid)){
                List<MapPosInfo> ryList = new ArrayList<>();
                Map<String, List<Map<String, Object>>> loc = new HashMap<>();
                Map<String, List<ChasRygjSnap>> rydwxx = rygjmapService.getBaqRydwxx(baqid);
                //process(rydwxx,ryList);
                Map<String, List<ChasRygjSnap>> mjdwxx = rygjmapService.getBaqMjdwxx(baqid);
                //process(mjdwxx,ryList);
                Map<String, List<ChasRygjSnap>> fkdwxx = rygjmapService.getBaqFkdwxx(baqid);
                megerMap(mjdwxx,loc);
                megerMap(rydwxx,loc);
                megerMap(fkdwxx,loc);
                process(loc,ryList);
                result.put("success",true);
                result.put("data",ryList);
                Map<String, Object> param = new HashMap<>(16);
                param.put("ryzt", "01");//在区
                param.put("baqid", baqid);
                List<ChasBaqryxx> ryxxList = baqryxxService.findList(param, "r_rssj asc");
                int zsrs = 0;
                if(ryxxList!= null&&!ryxxList.isEmpty()){
                    zsrs =ryxxList.size();
                }
                result.put("zsrs",zsrs);
            }else{
                result.put("success",false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success",false);
        }
        return result;
    }

    //获取摄像头信息进行预览
    @RequestMapping("/getSxtByQyid")
    @ApiAccessNotValidate
    @ResponseBody
    public Map<String,Object> getSxtByQyid(HttpServletRequest request){
        String qyid = request.getParameter("qyid");
        Map<String,Object> result = new HashMap<>();
        Map map = new HashMap();
        map.put("qyid",qyid);
        map.put("sblx", SYSCONSTANT.SBLX_SXT);
        map.put("sbgn","42");
        List<ChasSb> list = sbService.findByParams(map);
        if(list != null && !list.isEmpty()){
            if(StringUtils.isNotEmpty(list.get(0).getKzcs1())){//配置了nvr,使用nvr播放
                map.clear();
                map.put("sbbh", list.get(0).getKzcs1());
                List<ChasSb> nvrList = sbService.findByParams(map);
                if(nvrList != null&&!nvrList.isEmpty()){
                    ChasSb nvr=nvrList.get(0);
                    HashMap sbResult = new HashMap<>();
                    sbResult.put("kzcs1", nvr.getKzcs1());
                    sbResult.put("kzcs2",nvr.getKzcs2() );
                    sbResult.put("kzcs3", nvr.getKzcs3());
                    sbResult.put("kzcs4", nvr.getKzcs4());
                    sbResult.put("kzcs5",list.get(0).getKzcs5());
                    result.put("data", sbResult);
                    result.put("playType", "nvr");
                    result.put("success",true);
                }
            }else{//使用摄像头
                result.put("success",true);
                result.put("data",list.get(0));
                result.put("playType", "sxt");
            }
        }else{
            result.put("success",false);
        }
        return result;
    }



    private void megerMap(Map<String, List<ChasRygjSnap>> fkdwxx,Map<String, List<Map<String, Object>>> dwxx){
        if(fkdwxx !=null){
            Set<Map.Entry<String, List<ChasRygjSnap>>> entrySet = fkdwxx.entrySet();
            entrySet.stream().forEach(entry->{
                List<Map<String, Object>> list = dwxx.get(entry.getKey());
                if(list==null){
                    list = new ArrayList<>();
                }
                list.addAll(DicUtil.translate(entry.getValue(), new String[]{"CHAS_ZD_CASE_ZJLX", "RSYY","DAFS","CHAS_ZD_CASE_RYLB","CHAS_ZD_ZB_XB"}, new String[]{"zjlx", "ryZaybh", "dafs", "ryRylx","xb"}));
                dwxx.put(entry.getKey(),list );
            });
        }
    }
    private void process(Map<String, List<Map<String, Object>>> fkdwxx,List<MapPosInfo> ryList){
        if(fkdwxx !=null){
            Set<Map.Entry<String, List<Map<String, Object>>>> entrySet = fkdwxx.entrySet();
            List<MapPosInfo> list = entrySet.stream().map(set -> {
                MapPosInfo info = new MapPosInfo();
                info.setQyid(set.getKey());
                info.getList().addAll(set.getValue());
                return info;
            }).collect(Collectors.toList());
            if(list!=null&&!list.isEmpty()){
                ryList.addAll(list);
            }
        }
    }

    /**
     * 根据办案区获取摄像头数据
     * @param request
     * @return
     */
    @RequestMapping("/getSxtBybaqid")
    @ApiAccessNotValidate
    @ResponseBody
    public Map<String,Object> getSxtBybaqid(HttpServletRequest request){
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        Map<String, Object> sxtMap = new HashMap<>();
        try {
            String baqid = request.getParameter("baqid");
            param.put("baqid",baqid);
            param.put("sblx", SYSCONSTANT.SBLX_SXT);
            param.put("sbgn","42");
            List<ChasSb> list = sbService.findByParams(param);
            List<Map<String,Object>> sxtList = new ArrayList<>();
            for (ChasSb chasSb : list) {
                if(StringUtils.isEmpty((String) sxtMap.get(chasSb.getQyid()))){
                    ArrayList<ChasSb> arrayList = new ArrayList<>();
                    arrayList.add(chasSb);
                    sxtMap.put("qyid",chasSb.getQyid());
                    sxtMap.put("list",arrayList);
                    sxtList.add(sxtMap);
                }
            }
            result.put("data",sxtList);
            result.put("success",true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success",false);
        }
        return result;
    }

    /**
     * 大屏人员数据获取
     * @param request
     * @return
     */
    @RequestMapping("/getMapRyxxByRybh")
    @ApiAccessNotValidate
    @ResponseBody
    public Map<String,Object> getMapRyxxByRybh(HttpServletRequest request){
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        String rybh = request.getParameter("rybh");
        ChasBaqryxx baqryxx = baqryxxService.findByRybh(rybh);
        String xbName = DicUtil.translate("CHAS_ZD_ZB_XB", baqryxx.getXb());
        String zjlxName = DicUtil.translate("CHAS_ZD_CASE_ZJLX", baqryxx.getZjlx());
        result.put("xb",xbName);
        result.put("zjlx",zjlxName);
        result.put("ryxx",baqryxx);
        param.put("rybh", rybh);
        List<ChasSswpxx> sswps = sswpxxService.findList(param, "lrsj desc");
        List<Map<String, Object>> sswplist = DicUtil.translate(sswps, new String[]{"WPLB", "ZD_CFWZ"}, new String[]{"lb", "cfwz"});
        result.put("sswps",sswplist);
        List<ChasythcjQk> chasythcjQks = ythcjQkService.findList(param, "lrsj desc");
        result.put("chasythcjQk",null);
        if(chasythcjQks.size() > 0){
            ChasythcjQk chasythcjQk = chasythcjQks.get(0);
            result.put("chasythcjQk",chasythcjQk);
        }
        return result;
    }

    @RequestMapping("/getMapMjxxByRybh")
    @ApiAccessNotValidate
    @ResponseBody
    public Map<String,Object> getMapMjxxByRybh(HttpServletRequest request){
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        String rybh = request.getParameter("rybh");
        String baqid = request.getParameter("baqid");
        ChasYwMjrq chasYwMjrq = mjrqService.findMjrqByRybh(baqid, rybh);
        String xbName = DicUtil.translate("CHAS_ZD_ZB_XB", chasYwMjrq.getXb());
        String zjlxName = DicUtil.translate("CHAS_ZD_CASE_ZJLX", chasYwMjrq.getZjlx());
        result.put("xb",xbName);
        result.put("zjlx",zjlxName);
        result.put("mjrq",chasYwMjrq);
        return result;
    }

    @RequestMapping("/getMapFkxxByRybh")
    @ApiAccessNotValidate
    @ResponseBody
    public Map<String,Object> getMapFkxxByRybh(HttpServletRequest request){
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        String rybh = request.getParameter("rybh");
        String baqid = request.getParameter("baqid");
        ChasYwFkdj fkdj = fkdjService.findFkdjByRybh(baqid, rybh);
        String xbName = DicUtil.translate("CHAS_ZD_ZB_XB", fkdj.getXb());
        String zjlxName = DicUtil.translate("CHAS_ZD_CASE_ZJLX", fkdj.getZjlx());
        result.put("xb",xbName);
        result.put("zjlx",zjlxName);
        result.put("fkdj",fkdj);
        return result;
    }

    @RequestMapping("/getSxjlData")
    @ApiAccessNotValidate
    @ResponseBody
    public Map<String,Object> getSxjlData(HttpServletRequest request,int page,int pagesize){
        Map<String, Object> param = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        String rybh = request.getParameter("rybh");
        if(StringUtils.isEmpty(rybh)){
            return result;
        }
        param.put("rybh",rybh);
        PageDataResultSet<ChasSxsKz> resultSet = sxsKzService.getEntityPageData(page, pagesize, param, "kssj desc");
//        Map<String, Object> map = ParamUtil.getDataGrid(resultSet.getTotal(), resultSet.getData());
        result.put("rows", resultSet.getData());
        result.put("total",resultSet.getTotal());
        return result;
    }

    @RequestMapping("/goGjxlMap")
    @ApiAccessNotValidate
    public String goGjxlMap(String rybh,String qy ,String name, Model model){
        Map<String, Object> params = new HashMap<>();
        try {
            if(StringUtils.isEmpty(rybh)){
                return "chasstage/htgl/bigscreen/"+qy+"/"+name;
            }
//            ChasBaqryxx ryxx = baqryxxService.findByRybh(rybh);
            List<Map<String,Object>> list1 = new ArrayList<>();
            params.put("rybh",rybh);
            List<ChasRygj> list = rygjService.findList(params, "kssj asc");
//            List<ChasRygj> list = rygjService.findList(params, "kssj asc");
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            /*Date rRssj = ryxx.getRRssj();
            Date cCssj = ryxx.getCCssj();
            String tlsc = "";
            String cssj = "";
            if(!ObjectUtils.isEmpty(cCssj)){
                long l = cCssj.getTime() - rRssj.getTime();
                tlsc = durationConvert(l/1000);
                cssj = format.format(cCssj);
            }
            String rssj = format.format(rRssj);*/
//            int i = 0;
            for (ChasRygj chasRygj : list) {
                Map<String, Object> map = new HashMap<>();
//                i++;
                /*Date kssj = chasRygj.getKssj();
                Date jssj = chasRygj.getJssj();
                if(!ObjectUtils.isEmpty(kssj)){
                    if(i == 1){
                        map.put("kssj", DateUtil.getDateFormat(kssj,"yyyy.MM.dd HH.mm.ss"));
                    }else if(i == list.size()){
                        map.put("kssj",DateUtil.getDateFormat(kssj,"yyyy.MM.dd HH.mm.ss"));
                    }else{
                        map.put("kssj",DateUtil.getDateFormat(kssj,"HH.mm.ss"));
                    }
                    if(!ObjectUtils.isEmpty(jssj)){
                        map.put("tlsc",durationConvert((jssj.getTime()-kssj.getTime())/1000));
                    }
                }*/
                map.put("id",chasRygj.getId());
                map.put("qyid",chasRygj.getQyid());
                map.put("qymc",chasRygj.getQymc());
                list1.add(map);
                model.addAttribute("ryxm",chasRygj.getXm());
            }
            model.addAttribute("gjData",list1);
            model.addAttribute("gjDataObj", JSONArray.fromObject(list1));

            /*model.addAttribute("tlsc",tlsc);
            model.addAttribute("rssj",rssj);
            model.addAttribute("cssj",cssj);*/
        } catch (Exception e) {
            e.printStackTrace();
            log.error("goGjxlMap",e.getMessage());
        }
        return "chasstage/htgl/bigscreen/"+qy+"/"+name;
    }

}
