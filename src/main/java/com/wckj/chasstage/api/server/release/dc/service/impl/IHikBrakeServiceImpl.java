package com.wckj.chasstage.api.server.release.dc.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.api.server.release.dc.service.IHikBrakeService;
import com.wckj.chasstage.common.util.PicUtils;
import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import com.wckj.chasstage.modules.mjzpk.entity.ChasXtMjzpk;
import com.wckj.chasstage.modules.mjzpk.service.ChasXtMjzpkService;
import com.wckj.chasstage.modules.znpz.entity.ChasXtBaqznpz;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.framework.core.frws.file.ByteFileObj;
import com.wckj.framework.core.frws.file.IByteFileObj;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import com.wckj.frws.sdk.core.obj.UploadParamObj;
import com.wckj.jdone.modules.sys.util.SysUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author:zengrk
 */
@Service
public class IHikBrakeServiceImpl  implements IHikBrakeService {
    @Autowired
    private ChasXtBaqznpzService baqznpzService;
    protected static Logger log = LoggerFactory.getLogger(IHikBrakeServiceImpl.class);

    @Autowired
    private ChasXtMjzpkService mjzpkService;

    @Autowired
    private ChasXtBaqznpzService chasXtBaqznpzService;

    /**
     * API网关的后端服务上下文为：/artemis
     */
    private static final String ARTEMIS_PATH = "/artemis";

    private static int mjsx= 24;//默认24小时
    private static String channelNos="";
    private static ArrayList<String> resourceIndexCodeList = new ArrayList<>();

    /**
     *
     * @param type 人员类型
     * @param ryid
     * @param ryxm
     * @param zpId
     * @param startTime
     * @return
     */
    public DevResult IssuedToBrakeByFaceAsyn(String type
            , String ryid, String ryxm, String zpId,String baqid, Date startTime){
        try {
            log.info(String.format("下发门禁:baqid:{%s},ryxm:{%s}",baqid,ryxm));
            ChasXtBaqznpz baqznpz = baqznpzService.findByBaqid2(baqid);
            BaqConfiguration configuration = baqznpz.getBaqConfiguration();
            init(configuration);
            //根据人员类型获取门禁信息
            init_BrakexxByRylx(configuration,type,ryid);
            String zpUrl = uploadMjxfzpByByte(zpId);
            String resourceIndexCodes = "";
            log.info("resourceIndexCodeList,size:"+resourceIndexCodeList.size()+"value:"+resourceIndexCodeList.toString());
            for(int i = 0; i < resourceIndexCodeList.size(); i++) {
                JSONObject jsonObject = IssuedToBrakeByFace(ryid, ryxm, zpUrl, startTime, resourceIndexCodeList.get(i));
                String code = jsonObject.get("code")+"";
//                String codes = Integer.toString(code);
                if(!"0".equals(code)){
                    log.error("下发门禁失败,code:"+code+",门禁编号:"+resourceIndexCodeList.get(i));
                    resourceIndexCodes = resourceIndexCodes + resourceIndexCodeList.get(i) + ",";
                }
            }
            if(StringUtils.isNotEmpty(resourceIndexCodes)){
                log.info("下发失败门禁:"+resourceIndexCodes);
            }
            log.info(ryxm+"人脸下发海康门禁成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("IssuedToBrakeByFaceAsyn异常:"+e);
            return DevResult.error("下发门禁失败");
        }
        return DevResult.success("下发门禁成功");
    }

    /**
     * 对接海康安防平台，下发门禁
     * 下发人脸到门禁
     * @return 下发结果
     */
    public JSONObject IssuedToBrakeByFace(String ryid, String ryxm, String zpUrl, Date startTime,String resourceIndexCode){
        log.info("海康安防平台，下发门禁开始");
        JSONObject object = new JSONObject();
        try{
            TimeZone tz = TimeZone.getDefault();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            df.setTimeZone(tz);
            Map<String, Object> params = new HashMap<>();
            List<Map<String, Object>> resourceInfos=new ArrayList<>();
            Map<String, Object> resourceInfo = new HashMap<>();
            List<Map<String, Object>> personInfos = new ArrayList<>();
            Map<String, Object> personInfo=new HashMap<>();
            Map<String, Object> face = new HashMap<>();
            List<Map<String, Object>> faceList = new ArrayList<>();
            Map<String, Object> faceInfo = new HashMap<>();
            params.put("priority",1);
            resourceInfo.put("channelNos",new int[]{Integer.parseInt(channelNos)});//资源通道号
            resourceInfo.put("resourceIndexCode",resourceIndexCode);//门禁设备编号
            resourceInfo.put("resourceType","acsDevice");
            resourceInfos.add(resourceInfo);
            params.put("resourceInfos",resourceInfos);
            personInfo.put("personId",ryid);//人员id
            personInfo.put("personStatus","ADD");
            personInfo.put("startTime",df.format(startTime));//开始时间
            personInfo.put("endTime",df.format(DateUtil.offset(startTime, DateField.HOUR,mjsx)));//结束时间
            personInfo.put("personType","COMMON");//
            personInfo.put("name",ryxm);//人员姓名

            faceInfo.put("deleteAllFace",false);
            face.put("faceId",ryid);
            face.put("picUrl",zpUrl);
            faceList.add(face);
            faceInfo.put("faceList",faceList);
            personInfo.put("faceInfo",faceInfo);
            personInfos.add(personInfo);
            params.put("personInfos",personInfos);

            String body = JSON.toJSONString(params);
            String getCamsApi = ARTEMIS_PATH + "/api/acps/v1/authDownload/special/person/diy";
            Map<String, String> path = new HashMap<>();
            path.put("https://", getCamsApi);
            log.info(String.format("海康门禁人脸下发接口地址：%s",getCamsApi));
            log.info(String.format("海康门禁人脸下发接口参数：%s",body));
            String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, "application/json");
            log.info(String.format("海康门禁人脸下发接口结果：%s",result));
            if(StringUtils.isNotEmpty(result)){
                Map<String,Object> map = JSON.parseObject(result, Map.class);
                object.put("data",map.get("data"));
                object.put("code",map.get("code"));
            }
        }catch (Exception e){
            log.error("IssuedToBrakeByFace:",e);
            object.put("success",false);
            object.put("msg",e.getMessage());
            object.put("code","-1");
            return object;
        }

        object.put("success",true);
        object.put("msg","下发执行成功!");
        return object;
    }

    /**
     * 删除下发的海康人脸门禁
     * @param type 人员类型
     * @param ryid
     * @param ryxm
     * @param zpId
     * @param startTime
     * @return
     */
    public DevResult deleteIssuedToBrakeByFaceAsyn(String type,String ryid, String ryxm, String zpId,String baqid, Date startTime){
        try {
            ChasXtBaqznpz baqznpz = baqznpzService.findByBaqid2(baqid);
            BaqConfiguration baqConfiguration = baqznpz.getBaqConfiguration();
            init(baqConfiguration);
            //根据人员类型获取门禁信息
            init_BrakexxByRylx(baqConfiguration,type,ryid);
            FileInfoObj file = FrwsApiForThirdPart.getFileInfoByBizId(zpId);
            String url = "";
             if(Objects.nonNull(file)){
                 url=file.getDownUrl();
             }
//            String zpUrl = uploadMjxfzpByByte(zpId);
            String resourceIndexCodes = "";
            log.info("resourceIndexCodeList,size:"+resourceIndexCodeList.size()+"value:"+resourceIndexCodeList.toString());
            for(int i = 0; i < resourceIndexCodeList.size(); i++) {
                JSONObject jsonObject = deleteIssuedToBrakeByFace(ryid, ryxm,url,resourceIndexCodeList.get(i));
                String code = jsonObject.get("code")+"";
//                String codes = Integer.toString(code);
                if(!"0".equals(code)){
                    log.error("删除门禁失败,code:"+code+",门禁编号:"+resourceIndexCodeList.get(i));
                    resourceIndexCodes = resourceIndexCodes + resourceIndexCodeList.get(i) + ",";
                }
            }
            if(StringUtils.isNotEmpty(resourceIndexCodes)){
                log.info("删除失败门禁:"+resourceIndexCodes);
            }
            log.info(ryxm+"人脸删除海康门禁成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("deleteIssuedToBrakeByFaceAsyn异常:"+e);
            return DevResult.error("删除门禁失败");
        }
        return DevResult.success("删除门禁成功");
    }

    /**
     * 删除人脸门禁信息
     *
     * @return 下发结果
     */
    public JSONObject deleteIssuedToBrakeByFace(String ryid, String ryxm,String url,String resourceIndexCode){
        log.info("海康安防平台，删除门禁开始");
        JSONObject object = new JSONObject();
        try{
            TimeZone tz = TimeZone.getDefault();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            df.setTimeZone(tz);
            Map<String, Object> params = new HashMap<>();
            List<Map<String, Object>> resourceInfos=new ArrayList<>();
            Map<String, Object> resourceInfo = new HashMap<>();
            List<Map<String, Object>> personInfos = new ArrayList<>();
            Map<String, Object> personInfo=new HashMap<>();
            Map<String, Object> face = new HashMap<>();
            List<Map<String, Object>> faceList = new ArrayList<>();
            Map<String, Object> faceInfo = new HashMap<>();
            //params.put("priority",1);
            resourceInfo.put("channelNos",new int[]{Integer.parseInt(channelNos)});//资源通道号
            resourceInfo.put("resourceIndexCode",resourceIndexCode);//门禁设备编号
            resourceInfo.put("resourceType","acsDevice");
            resourceInfos.add(resourceInfo);
            params.put("resourceInfos",resourceInfos);
            personInfo.put("personId",ryid);//人员id
            personInfo.put("personStatus","DELETE");
            //personInfo.put("startTime",df.format(startTime));//开始时间
            //personInfo.put("endTime",df.format(DateUtil.offset(startTime, DateField.HOUR,mjsx)));//结束时间
            personInfo.put("personType","COMMON");//
            personInfo.put("name",ryxm);//人员姓名

            //faceInfo.put("deleteAllFace",true);
            face.put("faceId",ryid);
            face.put("picUrl",url);
            faceList.add(face);
            faceInfo.put("faceList",faceList);
            personInfo.put("faceInfo",faceInfo);
            personInfos.add(personInfo);
            params.put("personInfos",personInfos);

            String body = JSON.toJSONString(params);
            String getCamsApi = ARTEMIS_PATH + "/api/acps/v1/authDownload/special/person/diy";
            Map<String, String> path = new HashMap<>();
            path.put("https://", getCamsApi);
            log.info(String.format("海康门禁人脸删除接口地址：%s",getCamsApi));
            log.info(String.format("海康门禁人脸删除接口参数：%s",body));
            String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, "application/json");
            log.info(String.format("海康门禁人脸删除接口结果：%s",result));
            if(StringUtils.isNotEmpty(result)){
                Map<String,Object> map = JSON.parseObject(result, Map.class);
                object.put("data",map.get("data"));
                object.put("code",map.get("code"));
            }
        }catch (Exception e){
            log.error("deleteIssuedToBrakeByFace:",e);
            object.put("success",false);
            object.put("msg",e.getMessage());
            object.put("code","-1");
            return object;
        }
        object.put("success",true);
        object.put("msg","删除执行成功!");
        return object;
    }

    public static void init(BaqConfiguration baqConfiguration){
        System.out.println("订阅前配置Artemis环境...");
        String hikUrl =baqConfiguration.getHkafptUrl();
        String params = baqConfiguration.getHkafptParam();
        log.info("安防平台相关参数：" + params);
        log.info("安防平台url：" + hikUrl);
        if(StringUtils.isEmpty(hikUrl)||StringUtils.isEmpty(params)){
            throw new RuntimeException("对接海康安防平台配置参数错误");
        }
        Map<String, String> map = JSON.parseObject(params, Map.class);
        String appKey=map.get("HIK_App_Key");
        String appRecret=map.get("HIK_App_Recret");
//        String hikUrl = SysUtil.getParamValue("HIK_iS_C");
//        String appKey = SysUtil.getParamValue("HIK_App_Key");
//        String appRecret = SysUtil.getParamValue("HIK_App_Recret");
        ArtemisConfig.host = hikUrl; // 平台/nginx的IP和端口（https端口默认为443）
        ArtemisConfig.appKey = appKey; // 合作方Key
        ArtemisConfig.appSecret = appRecret;// 合作方Secret
    }



    public void init_BrakexxByRylx(BaqConfiguration baqConfiguration,String ryType,String ryid){
        //根据指定人员获取指定门禁设备,指定下发人脸时限
        //hikBrakeListCA: resourceIndexCode:123,123,123|mjsx:6
        try {
            String params=baqConfiguration.getHkafptxfrlParam();
            Map<String, String> map = JSON.parseObject(params, Map.class);
            log.info("安防平台下发人脸相关参数：" + params);
            channelNos=map.get("Hik_ChannelNos");
            //channelNos = SysUtil.getParamValue("Hik_ChannelNos");
            String hikBrakeListCA = "";
            if("R".equals(ryType)){
                hikBrakeListCA = map.get("Hik_BrakeListCAByR");
            }
            if("M".equals(ryType)){
                // 区分是否民警
                ChasXtMjzpk mjzpk = mjzpkService.findById(ryid);
                hikBrakeListCA = map.get("Hik_BrakeListCAByM");
//                if(mjzpk != null){
//                    if(StringUtils.isEmpty(mjzpk.getJzlx()) || StringUtils.equals(mjzpk.getJzlx(),"0")){
//                        hikBrakeListCA = SysUtil.getParamValue("Hik_BrakeListCAByM");
//                    }else{
//                        // 交警
//                        hikBrakeListCA = SysUtil.getParamValue("Hik_BrakeListCAByMTraffic");
//                    }
//                    hikBrakeListCA = map.get("Hik_BrakeListCAByM");
//                }else{
//                    hikBrakeListCA = map.get("Hik_BrakeListCAByM");
//                }
            }
            if("F".equals(ryType)){
                hikBrakeListCA = map.get("Hik_BrakeListCAByF");
            }
            String[] split1 = hikBrakeListCA.split("\\|");
            String[] resourceIndexCode = split1[0].split(":");
            if(resourceIndexCode.length>1){
                resourceIndexCode = resourceIndexCode[1].split(",");
                resourceIndexCodeList = ListUtil.toList(resourceIndexCode);
            }
            String[] mjsxs = split1[1].split(":");
            if(mjsxs.length>1){
                mjsx = Integer.parseInt(mjsxs[1]);
            }
            log.info("门禁设备编号:"+resourceIndexCodeList.toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据人员类型获取门禁信息错误");
        }
    }

    public String uploadMjxfzpByByte(String zpId){
        FileInfoObj fileObj = FrwsApiForThirdPart.getFileInfoByBizId(zpId);
        String zpUrl = fileObj.getDownUrl();
        try {
            IByteFileObj iByteFileObj = FrwsApiForThirdPart.downloadByteFileByFileId(fileObj.getId());
            byte[] bytes = iByteFileObj.getBytes();
            byte[] data = PicUtils.compressPicForScale(bytes, 190);

            UploadParamObj uploadParam = new UploadParamObj();
            uploadParam.setOrgSysCode(fileObj.getOrgSysCode());
            uploadParam.setBizId(StringUtils.getGuid32());
            uploadParam.setBizType("mjxfzp");
            log.info("文件上传参数:{}",uploadParam);
            if (data != null) {
                FileInfoObj o = FrwsApiForThirdPart.uploadByteFile(new ByteFileObj(uploadParam.getBizId()+uploadParam.getBizType() + ".jpg", data),uploadParam);
                if (o != null) {
                    Map<String,Object> map = new HashMap<>();
                    zpUrl = o.getDownUrl();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("uploadMjxfzpByByte图片压缩大小失败:"+e);
        }
        return zpUrl;
    }

}
