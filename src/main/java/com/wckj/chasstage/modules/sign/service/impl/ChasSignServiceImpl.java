package com.wckj.chasstage.modules.sign.service.impl;

import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.common.util.SignbsUtil;
import com.wckj.chasstage.common.util.SignqmnyConfig;
import com.wckj.chasstage.common.util.StringUtil;
import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import com.wckj.chasstage.modules.signconfig.entity.ChasXtSignConfig;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.chasstage.modules.signconfig.service.ChasXtSignConfigService;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.sign.dao.ChasSignMapper;
import com.wckj.chasstage.modules.sign.entity.ChasSign;
import com.wckj.chasstage.modules.sign.service.ChasSignService;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.orm.mybatis.service.BaseService;
import com.wckj.jdone.modules.sys.util.SysUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChasSignServiceImpl extends BaseService<ChasSignMapper, ChasSign> implements ChasSignService {

    private static final Logger log = Logger.getLogger(ChasSignServiceImpl.class);

    @Autowired
    private ChasXtSignConfigService configService;
    @Autowired
    private ChasXtBaqznpzService baqznpzService;
    @Autowired
    private ChasBaqryxxService baqryxxService;

    @Override
    public int deleteByPrimaryRybh(String rybh,String signType) {
        return baseDao.deleteByPrimaryRybh(rybh,signType);
    }

    @Override
    public int saveSignData(String rybh, String type, String imgBody, String signName) {
        ChasSign chasSign = new ChasSign();
        int b = 0;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("rybh", rybh);
            if (StringUtils.isNotEmpty(rybh)) {
                ChasBaqryxxService chasBaqryxxService = ServiceContext.getServiceByClass(ChasBaqryxxService.class);
                List<ChasBaqryxx> ryxxlist = chasBaqryxxService.findList(params, null);
                if (!ryxxlist.isEmpty()) {
                    ChasBaqryxx ryxx = ryxxlist.get(0);
                    chasSign.setRyxm(ryxx.getRyxm());
                    chasSign.setZbdwBh(ryxx.getZbdwBh());
                    chasSign.setLrrSfzh(ryxx.getRysfzh());
                }
            }
            params.put("signType", type);
            if (StringUtils.isNotEmpty(type)) {
                List<ChasSign> signlist = findList(params, null);

                if (signlist.isEmpty()) {
                    chasSign.setId(StringUtils.getGuid32());
                    chasSign.setIsdel(SYSCONSTANT.ALL_DATA_MARK_NORMAL_I);
                    chasSign.setLrsj(new Date());
                    chasSign.setRybh(rybh);
                    chasSign.setSignType(type);
                    chasSign.setImgBody(imgBody);
                    chasSign.setSignName(signName);
                    b = save(chasSign);
                } else {
                    ChasSign cs = signlist.get(0);
                    cs.setImgBody(imgBody);
                    cs.setSignName(signName);
                    b = update(cs);
                }


            }
        } catch (Exception e) {
            log.error("saveSignData:",e);
        }
        return b;
    }

    public Map<String, Object> getSignData(String rybh, String type) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String offsetX = SysUtil.getParamValue("OffsetX");
            String offsetY = SysUtil.getParamValue("OffsetY");
            result.put("success", true);
            Map<String, Object> params = new HashMap<>();
            params.put("rybh", rybh);
            if (StringUtil.isNotEmpty(rybh)) {
                ChasBaqryxx baqryxx = baqryxxService.findByRybh(rybh);
                ChasXtSignConfig signConfig = configService.findByBaqid(baqryxx.getBaqid());
                SignqmnyConfig config = SignbsUtil.getSignByConfiguration(baqryxx.getBaqid());

                String qmts[] = config.getQmts();
                String qmType[] = config.getQmny();

                List<ChasSign> signlist = findList(params, null);
                Map<String,String> split = new HashMap<>();
                for (ChasSign sign : signlist) {
                    String sign_type = sign.getSignType();
                    String[] s_type = sign_type.split("\\+");
                    for (String s : s_type) {
                        ChasSign sign1 = findByType(s,baqryxx.getRybh());
                        if(sign1 != null){
                            split.put(s,sign1.getImgBody());
                        }else{
                            split.put(s,"");
                        }
                    }
                }

                List<Map<String, Object>> listSignInfo = new ArrayList<>();
                Integer[] config_qm = getInxByType(baqryxx,signConfig,config,type,"qm");
                for (Integer integer : config_qm) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("Id", integer);
                    map.put("Name", qmts[integer]);
                    map.put("SignSize", "2");
                    map.put("Describe", "当前操作:" + qmts[integer] + "!");
                    List<Map<String, Object>> listInputInfo = new ArrayList<>();
                    String Sname = qmType[integer];
                    String[] Snames = Sname.split("\\+");
                    boolean flag = false;
                    String base64 = "";
                    for (String sname : Snames) {
                        Map<String, Object> children = new HashMap<>();
                        children.put("SName", sname);
                        children.put("Mode", "5");
                        children.put("ImgWidth", "2.5");
                        children.put("ImgHeight", "1.3");
                        children.put("IsURL", "0");
                        if(StringUtil.isNotEmpty(split.get(sname))){
                            flag = true;
                            children.put("ImgData",split.get(sname));
                            base64 = split.get(sname);
                        }else{
                            children.put("ImgData",base64);
                        }
                        listInputInfo.add(children);
                    }
                    for (Map<String, Object> objectMap : listInputInfo) {
                        if(StringUtil.isEmpty((String) objectMap.get("ImgData"))){
                            objectMap.put("ImgData",base64);
                        }
                    }
                    map.put("OffsetX",StringUtil.isNotEmpty(offsetX) ? offsetX : "0");
                    map.put("OffsetY",StringUtil.isNotEmpty(offsetY) ? offsetY : "0");
                    map.put("ISuccess", flag);
                    map.put("ListInputInfo", listInputInfo);
                    listSignInfo.add(map);
                }
                result.put("ListSignInfo", listSignInfo);
                List<Map<String, Object>> listFingerInfo = new ArrayList<>();
                Integer[] config_ny = getInxByType(baqryxx,signConfig,config,type,"ny");
                for (Integer integer : config_ny) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("Id", integer);
                    map.put("Name", qmts[integer]);
                    map.put("SignSize", "2");
                    map.put("Describe", "当前操作:" + qmts[integer] + "!");
                    List<Map<String, Object>> listInputInfo = new ArrayList<>();
                    String Sname = qmType[integer];
                    String[] Snames = Sname.split("\\+");
                    boolean flag = false;
                    String base64 = "";
                    for (String sname : Snames) {
                        Map<String, Object> children = new HashMap<>();
                        children.put("SName", sname);
                        children.put("Mode", "5");
                        children.put("ImgWidth", "1.5");
                        children.put("ImgHeight", "2");
                        children.put("IsURL", "0");
                        if(StringUtil.isNotEmpty(split.get(sname))){
                            flag = true;
                            children.put("ImgData",split.get(sname));
                            base64 = split.get(sname);
                        }else{
                            children.put("ImgData",base64);
                        }
                        listInputInfo.add(children);
                    }
                    for (Map<String, Object> objectMap : listInputInfo) {
                        if(StringUtil.isEmpty((String) objectMap.get("ImgData"))){
                            objectMap.put("ImgData",base64);
                        }
                    }
                    map.put("ISuccess", flag);
                    map.put("ListInputInfo", listInputInfo);
                    listFingerInfo.add(map);
                }
                result.put("ListFingerInfo", listFingerInfo);
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("msg", e.getMessage());
            log.error("getSignData:",e);
        }
        return result;
    }

    public Map<String, Object> getSignDataByRybhKhd(String rybh, String type) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("rybh", rybh);
        Map<String, Object> result = new HashMap<String, Object>();
        String offsetX = SysUtil.getParamValue("OffsetX");
        String offsetY = SysUtil.getParamValue("OffsetY");
        try {
            result.put("success", true);
            if (StringUtil.isNotEmpty(rybh)) {
                ChasBaqryxx baqryxx = baqryxxService.findByRybh(rybh);
                ChasXtSignConfig signConfig = configService.findByBaqid(baqryxx.getBaqid());
                SignqmnyConfig config = SignbsUtil.getSignByConfiguration(baqryxx.getBaqid());
                //获取办案区智能配置
                BaqConfiguration baqznpz = baqznpzService.findByBaqid(baqryxx.getBaqid());
                if (!baqznpz.getSfqyny()) {
                    removeNyConfig(config);
                }

                String qmts[] = config.getQmts();
                String qmType[] = config.getQmny();

                List<ChasSign> signlist = findList(params, null);
                Map<String,String> split = new HashMap<>();
                for (ChasSign sign : signlist) {
                    String sign_type = sign.getSignType();
                    String[] s_type = sign_type.split("\\+");
                    for (String s : s_type) {
                        ChasSign sign1 = findByType(s,baqryxx.getRybh());
                        if(sign1 != null){
                            split.put(s,sign1.getImgBody());
                        }else{
                            split.put(s,"");
                        }
                    }
                }

                List<Map<String, Object>> listSignInfo = new ArrayList<>();
                Integer[] config_qm = getInxByType(baqryxx,signConfig,config,type,"qm");
                for (Integer integer : config_qm) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("Id", integer);
                    map.put("Name", qmts[integer]);
                    map.put("SignSize", "2");
                    map.put("Describe", "当前操作:" + qmts[integer] + "!");
                    List<Map<String, Object>> listInputInfo = new ArrayList<>();
                    String Sname = qmType[integer];
                    String[] Snames = Sname.split("\\+");
                    boolean flag = false;
                    String base64 = "";
                    for (String sname : Snames) {
                        Map<String, Object> children = new HashMap<>();
                        children.put("SName", sname);
                        children.put("Mode", "5");
                        children.put("ImgWidth", "2.5");
                        children.put("ImgHeight", "1.3");
                        children.put("IsURL", "0");
                        if(StringUtil.isNotEmpty(split.get(sname))){
                            flag = true;
                            children.put("ImgData",split.get(sname));
                            base64 = split.get(sname);
                        }else{
                            children.put("ImgData",base64);
                        }
                        listInputInfo.add(children);
                    }
                    for (Map<String, Object> objectMap : listInputInfo) {
                        if(StringUtil.isEmpty((String) objectMap.get("ImgData"))){
                            objectMap.put("ImgData",base64);
                        }
                    }
                    map.put("OffsetX",StringUtil.isNotEmpty(offsetX) ? offsetX : "0");
                    map.put("OffsetY",StringUtil.isNotEmpty(offsetY) ? offsetY : "0");
                    map.put("ISuccess", flag);
                    map.put("ListInputInfo", listInputInfo);
                    listSignInfo.add(map);
                }
                result.put("ListSignInfo", listSignInfo);
                List<Map<String, Object>> listFingerInfo = new ArrayList<>();
                Integer[] config_ny = getInxByType(baqryxx,signConfig,config,type,"ny");
                for (Integer integer : config_ny) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("Id", integer);
                    map.put("Name", qmts[integer]);
                    map.put("SignSize", "2");
                    map.put("Describe", "当前操作:" + qmts[integer] + "!");
                    List<Map<String, Object>> listInputInfo = new ArrayList<>();
                    String Sname = qmType[integer];
                    String[] Snames = Sname.split("\\+");
                    boolean flag = false;
                    String base64 = "";
                    for (String sname : Snames) {
                        Map<String, Object> children = new HashMap<>();
                        children.put("SName", sname);
                        children.put("Mode", "5");
                        children.put("ImgWidth", "1.5");
                        children.put("ImgHeight", "2");
                        children.put("IsURL", "0");
                        if(StringUtil.isNotEmpty(split.get(sname))){
                            flag = true;
                            children.put("ImgData",split.get(sname));
                            base64 = split.get(sname);
                        }else{
                            children.put("ImgData",base64);
                        }
                        listInputInfo.add(children);
                    }
                    for (Map<String, Object> objectMap : listInputInfo) {
                        if(StringUtil.isEmpty((String) objectMap.get("ImgData"))){
                            objectMap.put("ImgData",base64);
                        }
                    }
                    map.put("OffsetX",StringUtil.isNotEmpty(offsetX) ? offsetX : "0");
                    map.put("OffsetY",StringUtil.isNotEmpty(offsetY) ? offsetY : "0");
                    map.put("ISuccess", flag);
                    map.put("ListInputInfo", listInputInfo);
                    listFingerInfo.add(map);
                }
                result.put("ListFingerInfo", listFingerInfo);
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("msg", "获取签字捺印数据异常:"+e.getMessage());
            log.error("getSignDataByRybhKhd:",e);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getSignDataComprehensive(String rybh, String type,boolean isRemoveNy,boolean isIgnore) {
        List<Map<String, Object>> listKeyWord = new ArrayList<>();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("rybh", rybh);
            if (StringUtil.isNotEmpty(rybh)) {
                ChasBaqryxx baqryxx = getBaqryxxByRybh(rybh);
                ChasXtSignConfig signConfig = configService.findByBaqid(baqryxx.getBaqid());
                SignqmnyConfig config = SignbsUtil.getSignByConfiguration(baqryxx.getBaqid());
                if(isRemoveNy){
                    //获取办案区智能配置
                    BaqConfiguration baqznpz = baqznpzService.findByBaqid(baqryxx.getBaqid());
                    if (!baqznpz.getSfqyny()) {
                        removeNyConfig(config);
                    }
                }

                String qmts[] = config.getQmts();
                String qmType[] = config.getQmny();

                List<ChasSign> signList = findList(params, null);
                Map<String,String> split = splitSign(signList,baqryxx.getRybh());

                Integer[] config_qm = getInxByType(baqryxx,signConfig,config,type,"qm");
                listKeyWord.addAll(tryGetKeyWordByType(config_qm,qmts,qmType,split,"qm",isIgnore));
                Integer[] config_ny = getInxByType(baqryxx,signConfig,config,type,"ny");
                listKeyWord.addAll(tryGetKeyWordByType(config_ny,qmts,qmType,split,"ny",isIgnore));
            }
        } catch (Exception e) {
            log.error("getSignDataByRybhKhdZz:",e);
        }
        return listKeyWord;
    }

    private ChasBaqryxx getBaqryxxByRybh(String rybh) {
        return baqryxxService.findByRybh(rybh);
    }

    /**
     * 尝试获取签字捺印类型
     * @param config_type 签字捺印的下标
     * @param qmts  签名提示
     * @param qmType  前面类型
     * @param split  签字捺印图片数据
     * @param signal 当前获取的下标类型
     * @param isIgnore  是否过滤图片数据
     * @return json 格式签字捺印数据
     */
    private Collection<? extends Map<String, Object>> tryGetKeyWordByType(Integer[] config_type, String[] qmts, String[] qmType, Map<String, String> split, String signal, boolean isIgnore) {
        List<Map<String,Object>> listKeyWord = new ArrayList<>();
        String offsetX = SysUtil.getParamValue("OffsetX");
        String offsetY = SysUtil.getParamValue("OffsetY");
        for (Integer integer : config_type) {
            Map<String, Object> map = new HashMap<>();
            map.put("Id", integer);
            map.put("Name", qmts[integer]);
            map.put("SignSize", "2");
            map.put("ObjType","0"); //签名
            if(StringUtil.equals(signal,"ny")){
                map.put("ObjType","1"); //指纹
            }
            map.put("Describe", "当前操作:" + qmts[integer] + "!");
            map.put("Btntext",qmts[integer]);
            map.put("PageNoStart","1");
            map.put("PageNoEnd","0");
            map.put("Orientation","3");
            map.put("OffsetX",StringUtil.isNotEmpty(offsetX) ? offsetX : "0");
            map.put("OffsetY",StringUtil.isNotEmpty(offsetY) ? offsetY : "0");
            if(StringUtil.equals(signal,"ny")){
                map.put("Zoomfactor","0.15");
            }else{
                map.put("Zoomfactor","0.1");
            }
            map.put("DeviceType","2");
            String Sname = qmType[integer];
            String[] Snames = Sname.split("\\+");
            boolean flag = false;
            String base64 = "";
            String merge = "";
            for (String sname : Snames) {
                base64 = split.get(sname);
                if(StringUtil.isNotEmpty(base64)){
                    flag = true;
                }
                merge += sname+",";
            }
            if(isIgnore && StringUtil.isNotEmpty(base64)){
                continue;
            }
            if(StringUtil.isNotEmpty(merge)){
                merge = merge.substring(0,merge.length()-1);
                map.put("LocationOrKeyWord",merge.replaceAll(",","|"));
            }
            map.put("Mode", "3");
            map.put("StreamType","1");
            map.put("ImgWidth", "50");
            map.put("ImgHeight", "30");
            map.put("IsURL", "0");
            map.put("ImgData",base64);
            map.put("ISuccess", flag);
            listKeyWord.add(map);
        }
        return listKeyWord;
    }

    private Map<String, String> splitSign(List<ChasSign> signList,String rybh) {
        Map<String,String> split = new HashMap<>();
        for (ChasSign sign : signList) {
            String sign_type = sign.getSignType();
            String[] s_type = sign_type.split("\\+");
            for (String s : s_type) {
                ChasSign sign1 = findByType(s,rybh);
                if(sign1 != null){
                    split.put(s,sign1.getImgBody());
                }else{
                    split.put(s,"");
                }
            }
        }
        return split;
    }

    @Override
    public Map<String, Object> getSignDataByRybhKhdIgnoreOfBase64(String rybh, String type) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("rybh", rybh);
        Map<String, Object> result = new HashMap<String, Object>();
        String offsetX = SysUtil.getParamValue("OffsetX");
        String offsetY = SysUtil.getParamValue("OffsetY");
        try {
            result.put("success", true);
            if (StringUtil.isNotEmpty(rybh)) {
                ChasBaqryxx baqryxx = baqryxxService.findByRybh(rybh);
                ChasXtSignConfig signConfig = configService.findByBaqid(baqryxx.getBaqid());
                SignqmnyConfig config = SignbsUtil.getSignByConfiguration(baqryxx.getBaqid());
                //获取办案区智能配置
                BaqConfiguration baqznpz = baqznpzService.findByBaqid(baqryxx.getBaqid());
                if (!baqznpz.getSfqyny()) {
                    removeNyConfig(config);
                }

                String qmts[] = config.getQmts();
                String qmType[] = config.getQmny();

                List<ChasSign> signlist = findList(params, null);
                Map<String,String> split = new HashMap<>();
                for (ChasSign sign : signlist) {
                    String sign_type = sign.getSignType();
                    String[] s_type = sign_type.split("\\+");
                    for (String s : s_type) {
                        ChasSign sign1 = findByType(s,baqryxx.getRybh());
                        if(sign1 != null){
                            split.put(s,sign1.getImgBody());
                        }else{
                            split.put(s,"");
                        }
                    }
                }

                List<Map<String, Object>> listSignInfo = new ArrayList<>();
                Integer[] config_qm = getInxByType(baqryxx,signConfig,config,type,"qm");
                for (Integer integer : config_qm) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("Id", integer);
                    map.put("Name", qmts[integer]);
                    map.put("SignSize", "2");
                    map.put("Describe", "当前操作:" + qmts[integer] + "!");
                    List<Map<String, Object>> listInputInfo = new ArrayList<>();
                    String Sname = qmType[integer];
                    String[] Snames = Sname.split("\\+");
                    boolean flag = false;
                    String base64 = "";
                    for (String sname : Snames) {
                        Map<String, Object> children = new HashMap<>();
                        children.put("SName", sname);
                        children.put("Mode", "5");
                        children.put("ImgWidth", "2.5");
                        children.put("ImgHeight", "1.3");
                        children.put("IsURL", "0");
                        children.put("ImgData",base64);
                        if(StringUtil.isNotEmpty(split.get(sname))){
                            flag = true;
                           continue;
                        }
                        listInputInfo.add(children);
                    }
                    if(flag){
                        continue;
                    }
                    map.put("OffsetX",StringUtil.isNotEmpty(offsetX) ? offsetX : "0");
                    map.put("OffsetY",StringUtil.isNotEmpty(offsetY) ? offsetY : "0");
                    map.put("ISuccess", flag);
                    map.put("ListInputInfo", listInputInfo);
                    listSignInfo.add(map);
                }
                result.put("ListSignInfo", listSignInfo);
                List<Map<String, Object>> listFingerInfo = new ArrayList<>();
                Integer[] config_ny = getInxByType(baqryxx,signConfig,config,type,"ny");
                for (Integer integer : config_ny) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("Id", integer);
                    map.put("Name", qmts[integer]);
                    map.put("SignSize", "2");
                    map.put("Describe", "当前操作:" + qmts[integer] + "!");
                    List<Map<String, Object>> listInputInfo = new ArrayList<>();
                    String Sname = qmType[integer];
                    String[] Snames = Sname.split("\\+");
                    boolean flag = false;
                    String base64 = "";
                    for (String sname : Snames) {
                        Map<String, Object> children = new HashMap<>();
                        children.put("SName", sname);
                        children.put("Mode", "5");
                        children.put("ImgWidth", "1.5");
                        children.put("ImgHeight", "2");
                        children.put("IsURL", "0");
                        children.put("ImgData",base64);
                        if(StringUtil.isNotEmpty(split.get(sname))){
                            flag = true;
                            continue;
                        }
                        listInputInfo.add(children);
                    }
                    if(flag){
                        continue;
                    }
                    map.put("OffsetX",StringUtil.isNotEmpty(offsetX) ? offsetX : "0");
                    map.put("OffsetY",StringUtil.isNotEmpty(offsetY) ? offsetY : "0");
                    map.put("ISuccess", flag);
                    map.put("ListInputInfo", listInputInfo);
                    listFingerInfo.add(map);
                }
                result.put("ListFingerInfo", listFingerInfo);
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("msg", "获取签字捺印数据异常:"+e.getMessage());
            log.error("getSignDataByRybhKhdIgnoreOfBase64:",e);
        }
        return result;
    }

    private void removeNyConfig(SignqmnyConfig config) {
        List<Object> qmbList = new ArrayList<>();
        List<String> qmtsList = new ArrayList<>();
        List<String> qmTypeList = new ArrayList<>();
        Boolean qmb[] = config.getQmbn();
        String qmts[] = config.getQmts();
        String qmType[] = config.getQmny();
        for (int i = 0; i < qmb.length; i++) {
            Boolean aBoolean = qmb[i];
            String qmt = qmts[i];
            String qm = qmType[i];
            if (!("bechecked".equals(qm) || "involved".equals(qm) || "recipients".equals(qm))) {
                qmbList.add(aBoolean);
                qmtsList.add(qmt);
                qmTypeList.add(qm);
            }
        }
        int size = qmtsList.size();
        Boolean qmb2[] = new Boolean[size];
        String qmts2[] = new String[size];
        String qmType2[] = new String[size];
        for (int i = 0; i < size; i++) {
            qmb2[i] = (Boolean) qmbList.get(i);
            qmts2[i] = qmtsList.get(i);
            qmType2[i] = qmTypeList.get(i);
        }
        config.setQmbn(qmb2);
        config.setQmts(qmts2);
        config.setQmny(qmType2);
    }

    @Override
    public ChasSign findByType(String type, String rybh) {
        return baseDao.findByType(type, rybh);
    }

    public Integer[] getInxByType(ChasBaqryxx baqryxx,ChasXtSignConfig signConfig,SignqmnyConfig config,String type,String callType){
        boolean flag = true;
        if (signConfig != null) {
            if (StringUtil.equals(baqryxx.getRyzt(), SYSCONSTANT.BAQRYZT_DCS) && StringUtil.equals(signConfig.getCsqz(), "1")
                || (StringUtil.equals(baqryxx.getRyzt(),SYSCONSTANT.BAQRYZT_YCS))) {
                flag = false;
            }
            if(StringUtil.equals(type,"ythcj")){
                flag = true;
            }
            /*if (!StringUtil.equals(baqryxx.getRyzt(), SYSCONSTANT.BAQRYZT_DCS)) {
                flag = false;
            }
            //如果是岗位一  或者 岗位二 那么就不允许 领取人 和 出所管理员  签字捺印
            SessionUser user = WebContext.getSessionUser();
            if (user != null) {
                if (StringUtil.equals(user.getRoleCode(), "400011") || StringUtil.equals(user.getRoleCode(), "400012")) {
                    flag = true;
                }
            }*/
        }
        Boolean qmb[] = config.getQmbn();
        String qmType[] = config.getQmny();
        String[] qmData = new String[qmType.length];
        Integer[] config_qm = SignbsUtil.getSignInxByRule(config, type, callType, signConfig, flag);
        if(StringUtil.equals(callType,"qm")){
            if(StringUtil.equals(type,"ythcj")){
                List<Integer> add = new ArrayList<>();
                for (int i = 0; i < qmType.length; i++) {
                    if(qmType[i].contains("bamj") || qmType[i].contains("police_xb") || qmType[i].contains("jbqkgly")){
                        if(StringUtil.isNotEmpty(qmData[i])){
                            qmb[i] = true;
                        }
                        if(!ArrayUtils.contains(config_qm,i)){
                            add.add(i);
                        }
                    }
                }
                config_qm = (Integer[]) ArrayUtils.addAll(config_qm,add.toArray());
            }
        }
        return config_qm;
    }
}
