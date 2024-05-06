package com.wckj.chasstage.api.server.imp.rlbd;

import com.alibaba.csb.sdk.HttpCaller;
import com.alibaba.csb.sdk.HttpParameters;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wckj.api.def.zfba.model.ApiGgRyzjlb;
import com.wckj.api.def.zfba.service.ythba.ApiYthbaService;
import com.wckj.chasstage.api.def.rlbd.model.RlbdParam;
import com.wckj.chasstage.api.def.rlbd.model.RlbdResult;
import com.wckj.chasstage.api.def.rlbd.model.RlbdRyxx;
import com.wckj.chasstage.api.def.rlbd.model.Ytparam;
import com.wckj.chasstage.api.def.rlbd.service.ApiRlbdService;
import com.wckj.chasstage.common.util.*;
import com.wckj.chasstage.modules.baq.entity.BaqConfiguration;
import com.wckj.chasstage.modules.znpz.service.ChasXtBaqznpzService;
import com.wckj.chasstage.modules.zpxx.service.impl.ChasSswpZpxxServiceImpl;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.dic.DicObj;
import com.wckj.framework.core.frws.file.ByteFileObj;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.web.WebContext;
import com.wckj.framework.web.obj.SessionUser;
import com.wckj.frws.sdk.FrwsApiForThirdPart;
import com.wckj.frws.sdk.core.obj.FileInfoObj;
import com.wckj.frws.sdk.core.obj.UploadParamObj;
import com.wckj.jdone.common.util.SysUtil;
import com.wckj.jdone.modules.com.dic.core.ComDicManager;
import com.wckj.jdone.modules.com.dic.entity.JdoneComDicCode;
import com.wckj.jdone.modules.com.dic.service.JdoneComDicCodeService;
import com.wckj.jdone.modules.sys.entity.JdoneSysUser;
import com.wckj.jdone.modules.sys.service.JdoneSysUserService;
import com.wckj.jdone.modules.sys.util.DicUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.hutool.crypto.symmetric.SymmetricAlgorithm.AES;

/**
 * @author wutl
 * @Title: 人脸大数据比对
 * @Package
 * @Description:
 * @date 2020-12-1711:25
 */
@Service
public class ApiRlbdServiceImp implements ApiRlbdService {

    private static Logger logger = LoggerFactory.getLogger(ApiRlbdServiceImp.class);

    @Autowired
    private ChasXtBaqznpzService baqznpzService;
    @Autowired
    private JdoneComDicCodeService codeService;
    @Autowired
    private JdoneSysUserService userService;
    @Autowired
    private ComDicManager manager;

    /**
     * 获取人脸大数据平台数据
     *
     * @param rlbdParam
     * @return
     */
    @Override
    public ApiReturnResult<?> getRlbdData(RlbdParam rlbdParam) {
        ApiReturnResult<RlbdResult> returnResult = new ApiReturnResult<>();
        String baqid = rlbdParam.getBaqid();
        if (StringUtil.isEmpty(baqid)) {
            return ResultUtil.ReturnError("办案区id为NULL");
        }

        RlbdResult rlbdResult = new RlbdResult();
        List<RlbdRyxx> ckList = new ArrayList<>();
        BaqConfiguration configuration = baqznpzService.findByBaqid(baqid);
        String rlbdType = configuration.getRlbdType();
        if ("zz".equals(rlbdType)) {
            try {
                //众智平台
                String zzRlbdUrl = configuration.getZzRlbdUrl();
                String zzRlbdAckey = configuration.getZzRlbdAckey();
                String zzRlbdSekey = configuration.getZzRlbdSekey();
                String zzRlbdParam = configuration.getRlbdParam();
                HashMap hashMap = JSONHelper.toHashMap(zzRlbdParam);
                String zjhm = "";
                Object zjhmObj = hashMap.get("zjhm");
                if (zjhmObj != null) {
                    zjhm = zjhmObj.toString();
                }
                if (StringUtil.isEmpty(zjhm) && "zz".equals(rlbdParam.getType())) {
                    return ResultUtil.ReturnError("警员身份证号为NULL");
                }
                if (StringUtils.isEmpty(zzRlbdUrl) || "null".equals(zzRlbdUrl)) {
                    return ResultUtil.ReturnError("zzRlbdUrl为NULL");
                }
                if (StringUtils.isEmpty(zzRlbdAckey) || "null".equals(zzRlbdAckey)) {
                    return ResultUtil.ReturnError("zzRlbdAckey为NULL");
                }
                if (StringUtils.isEmpty(zzRlbdSekey) || "null".equals(zzRlbdSekey)) {
                    return ResultUtil.ReturnError("zzRlbdSekey为NULL");
                }
                String xm = rlbdParam.getXm();
                String sfzh = rlbdParam.getSfzh();
                String base64 = rlbdParam.getBase64();
                String url = zzRlbdUrl;
                String accessKey = zzRlbdAckey;
                String secretKey = zzRlbdSekey;
                HttpParameters.Builder hp1 = HttpParameters.newBuilder();
                hp1.api("ZJ_DSJMH_COOKIEID").version("1.0.0");
                hp1.requestURL(url);
                hp1.accessKey(accessKey).secretKey(secretKey);
                hp1.method("get");
                hp1.putParamsMap("tokenApp", "tokenApp");// 固定参数，不需要动
                hp1.putParamsMap("zjhm", zjhm);// 警员身份证号码，在警综平台注册过的账号
                String token = HttpCaller.invoke(hp1.build());
                logger.info("zz人脸比对token:" + token);
                Map<String, String> map = JSON.parseObject(token, new TypeReference<Map<String, String>>() {
                });
                String cookieId = map.get("cookieId");
                logger.info("zz人脸比对map:" + map.toString());
                HttpParameters.Builder hp = HttpParameters.newBuilder();
                hp.api("ZJ_CJSS_TPSS").version("1.0.0");
                hp.requestURL(url);
                hp.accessKey(accessKey).secretKey(secretKey);
                hp.method("post");
                hp.putParamsMap("file", base64);
                if (StringUtil.isNotEmpty(xm)) {
                    hp.putParamsMap("xm", xm);
                }
                if (StringUtil.isNotEmpty(sfzh)) {
                    hp.putParamsMap("sfzh", sfzh);
                }
                hp.putParamsMap("cookieid", cookieId);
                String invoke = HttpCaller.invoke(hp.build());
                Map<String, Object> result = JsonUtil.parse(invoke, Map.class);
                logger.info("zz人脸比对invoke:" + invoke);
                if (!(boolean) result.get("success")) {
                    return ResultUtil.ReturnError("众智人脸比对，返回结果失败：" + invoke);
                }
                DicObj dicObj = manager.getDicObj("ZD_ZB_MZ");
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("dicId", dicObj.getId());
                List<JdoneComDicCode> codeObjs = codeService.findList(params, null);
                Map<String, String> MZ_DIC = new HashMap<>();
                for (JdoneComDicCode codeObj : codeObjs) {
                    MZ_DIC.put(codeObj.getName(), codeObj.getCode());
                }
                List<Map<String, Object>> ListMap = (List<Map<String, Object>>) result.get("data");
                for (Map<String, Object> stringObjectMap : ListMap) {
                    Map<String, Object> data = (Map<String, Object>) stringObjectMap.get("base");
                    RlbdRyxx rlbdRyxx = new RlbdRyxx();
                    /*
                    RxbdPerson r = new RxbdPerson();
                    r.setBzry_XM((String) data.get("xm"));
                    r.setBzry_GMSFHM((String) data.get("sfzh"));
                    r.setBzry_CSRQ(DateTimeUtils.getDateFormat((String) data.get("csrq"), "yyyyMMdd", "yyyy-MM-dd"));
                    r.setBzry_HJDZ_QHNXXDZ((String) data.get("hzdxz"));
                    r.setBzry_MZDM(MZ_DIC.get((String) data.get("mz")));
                    r.setSimilarity();
                     list.add(r);
                    */
                    rlbdRyxx.setXm((String) data.get("xm"));
                    rlbdRyxx.setSfzh((String) data.get("sfzh"));
                    rlbdRyxx.setCsrq(DateTimeUtils.getDateFormat((String) data.get("csrq"), "yyyyMMdd", "yyyy-MM-dd"));
                    rlbdRyxx.setHzdxz((String) data.get("hzdxz"));
                    rlbdRyxx.setMz(MZ_DIC.get((String) data.get("mz")));
                    rlbdRyxx.setXxd((String) stringObjectMap.get("xxd"));
                    ckList.add(rlbdRyxx);
                }
                rlbdResult.setCkList(ckList);
                returnResult.setCode("200");
                returnResult.setData(rlbdResult);
                return returnResult;
            } catch (Exception e) {
                e.printStackTrace();
                returnResult.setCode("500");
                returnResult.setMessage("众智人脸比对错误：" + SysUtil.getExceptionDetail(e));
            }
        }
        if ("yt".equals(rlbdType)) {
            try {
                //依图平台
                String rlbdUrl = configuration.getRlbdUrl();
                String ytRlbdParam = configuration.getRlbdParam();
                String bizId = rlbdParam.getBizId();
                String bizType = rlbdParam.getBizType();
                String base64 = rlbdParam.getBase64();
                String picUrl = "";
                if(StringUtils.isEmpty(bizType)){
                    bizType = "ryzp";
                }
                if (StringUtils.isNotEmpty(bizId) && StringUtils.isNotEmpty(bizType)) {
                    FileInfoObj fileInfo = FrwsApiForThirdPart.getFileInfoByBizIdBizType(bizId, bizType);
                    picUrl = fileInfo.getDownUrl();
                } else if (StringUtils.isNotEmpty(base64)) {
                    JdoneSysUser admin = userService.findByLoginId("admin");
                    UploadParamObj paramObj = new UploadParamObj();
                    paramObj.setOrgSysCode(admin.getOrgCode());
                    paramObj.setOrgName(admin.getOrgName());
                    paramObj.setBizType(bizType);
                    String zpid = StringUtils.getGuid32();
                    paramObj.setBizId(zpid);
                    byte[] fileByte = ChasSswpZpxxServiceImpl.decode(base64);
                    FrwsApiForThirdPart.uploadByteFile(new ByteFileObj(StringUtils.getGuid32() + ".png", fileByte), paramObj);
                    FileInfoObj fileInfo = FrwsApiForThirdPart.getFileInfoByBizIdBizType(zpid, bizType);
                    picUrl = fileInfo.getDownUrl();
                }
                ytRlbdParam = ytRlbdParam.replace("faceUrl", picUrl);
                String result = HttpClientUtils.doPost(rlbdUrl, ytRlbdParam);
                logger.info("yt人脸比对返回："+result);
                if (StringUtils.isEmpty(result)) {
                    returnResult.setCode("500");
                    returnResult.setMessage("依图人脸比对错误：依图人脸返回结果为空！");
                    return returnResult;
                }
                HashMap hashMap = JSONHelper.toHashMap(result);
                int code = (int) hashMap.get("code");
                if (code != 0) {
                    returnResult.setCode("500");
                    returnResult.setMessage("依图人脸比对错误：" + hashMap.get("msg").toString());
                    return returnResult;
                }
                Object data = hashMap.get("data");
                if (data != null) {
                    JSONArray dataList = (JSONArray) data;
                    for (int i = 0; i < dataList.size(); i++) {
                        JSONObject jsonObject = (JSONObject) dataList.get(i);
                        RlbdRyxx rlbdRyxx = new RlbdRyxx();
                        rlbdRyxx.setAge((int) jsonObject.get("age"));
                        rlbdRyxx.setXm(jsonObject.get("humanName").toString());
                        rlbdRyxx.setSfzh(jsonObject.get("credentialsNum").toString());
                        rlbdRyxx.setCsrq(jsonObject.get("birthday").toString());
                        double similar = jsonObject.getDouble("similarity");
                        if(similar<1){
                            similar = similar* 100;
                        }
                        rlbdRyxx.setXxd(String.valueOf(similar));

                        String rlzpUrl="";
                        String facePicUrl = jsonObject.get("facePicUrl").toString();
                        if(StringUtils.isNotEmpty(facePicUrl)){
                            URL url = new URL(facePicUrl);
                            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                            httpConn.connect();
                            InputStream cin = httpConn.getInputStream();
                            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                            byte[] buffer = new byte[1024];
                            int len = 0;
                            while ((len = cin.read(buffer)) != -1) {
                                outStream.write(buffer, 0, len);
                            }
                            cin.close();
                            byte[] fileData = outStream.toByteArray();
                            JdoneSysUser admin = userService.findByLoginId("admin");
                            UploadParamObj paramObj = new UploadParamObj();
                            paramObj.setOrgSysCode(admin.getOrgCode());
                            paramObj.setOrgName(admin.getOrgName());
                            paramObj.setBizType("rlzp");
                            String zpid = StringUtils.getGuid32();
                            paramObj.setBizId(zpid);
                            FrwsApiForThirdPart.uploadByteFile(new ByteFileObj(StringUtils.getGuid32() + ".png", fileData), paramObj);
                            FileInfoObj fileInfo = FrwsApiForThirdPart.getFileInfoByBizIdBizType(zpid, "rlzp");
                            rlzpUrl = fileInfo.getDownUrl();
                        }
                        rlbdRyxx.setFacePicUrl(rlzpUrl);
                        rlbdRyxx.setSex((int) jsonObject.get("sex"));
                        ckList.add(rlbdRyxx);
                    }
                    rlbdResult.setCkList(ckList);
                    returnResult.setCode("200");
                    returnResult.setData(rlbdResult);
                }
            } catch (Exception e) {
                e.printStackTrace();
                returnResult.setCode("500");
                returnResult.setMessage("依图人脸比对错误：" + SysUtil.getExceptionDetail(e));
            }
        }
        if("ythn".equals(rlbdType)){
            try {
                if(StringUtils.isEmpty(rlbdParam.getBase64())){
                    return ResultUtil.ReturnError("base64为空");
                }
                String base64 = rlbdParam.getBase64();
                String rlbdUrl = configuration.getRlbdUrl();
                String ytRlbdParam = configuration.getRlbdParam();
                com.alibaba.fastjson.JSONObject parseObject = com.alibaba.fastjson.JSONObject.parseObject(ytRlbdParam);
                String code = (String) parseObject.get("code");
                rlbdUrl = rlbdUrl + "/faceBack/search/searchYT";

                Map<String, Object> param = new HashMap<>();
                param.put("data",base64);
                Map<String,String> header = new HashMap<>();
                header.put("code",code);
                logger.info("依图(海宁)人脸比对地址："+rlbdUrl);
                logger.info("依图(海宁)人脸比对data："+base64);
                logger.info("依图(海宁)人脸比对code："+code);
                com.alibaba.fastjson.JSONObject postFormJson = HttpClientUtils.httpPostFormJson(rlbdUrl, param, header);
                logger.info("依图(海宁)人脸比对返回："+postFormJson);
                if(postFormJson == null){
                    returnResult.setCode("500");
                    returnResult.setMessage("依图(海宁)人脸比对错误：依图人脸返回结果为空！");
                    return returnResult;
                }
                if(!(boolean)postFormJson.get("result")){
                    returnResult.setCode("500");
                    returnResult.setMessage("依图(海宁)人脸比对错误,code:" +  postFormJson.get("errorCode")+"msg:"+postFormJson.get("message"));
                    return returnResult;
                }
                Map<String,Object> data = (Map<String, Object>) postFormJson.get("data");
                List<Map<String,Object>> list = (List<Map<String, Object>>) data.get("dataList");
                for (Map<String, Object> map : list) {
                    RlbdRyxx rlbdRyxx = new RlbdRyxx();
                    rlbdRyxx.setXm(map.get("name")+"");
                    rlbdRyxx.setSfzh(map.get("person_id")+"");
                    rlbdRyxx.setXxd(map.get("similarity")+"");
                    String rlzpUrl="http://zjgarx.zj:11180/business/api/storage/image?uri_base64=";
                    String facePicUrl = (String) map.get("picture_uri");
                    if(StringUtils.isNotEmpty(facePicUrl)){
                        String base64Code = Base64.getEncoder().encodeToString(facePicUrl.getBytes());
                        rlzpUrl = rlzpUrl + base64Code;
                        rlbdRyxx.setFacePicUrl(rlzpUrl);
                    }
                    ckList.add(rlbdRyxx);
                }
                rlbdResult.setCkList(ckList);
                returnResult.setCode("200");
                returnResult.setData(rlbdResult);
            } catch (Exception e) {
                e.printStackTrace();
                returnResult.setCode("500");
                returnResult.setMessage("依图(海宁)人脸比对异常");
                logger.error("依图(海宁)人脸比对异常:",e);
            }
        }
        if ("jwbd".equals(rlbdType)) {
            String zfptRlsbUrl = configuration.getZfptRlsbUrl();
            if(StringUtils.isEmpty(zfptRlsbUrl)){
                return ResultUtil.ReturnError("未配置警务百度平台地址");
            }
            try {
                Map<String,String> params=new HashMap<>();
                if(StringUtils.isEmpty(rlbdParam.getBase64())){
                    return ResultUtil.ReturnError("base64为空");
                }
                params.put("base64",rlbdParam.getBase64());
                logger.info(String.format("警务百度请求地址:%s,参数:%s",params,zfptRlsbUrl));
                com.alibaba.fastjson.JSONObject jsonObject = HttpClientUtils.httpPostForm(zfptRlsbUrl, params);
                logger.info(String.format("警务百度结果:%s",jsonObject));
                if(!jsonObject.getBooleanValue("isSuccess")){
                    return ResultUtil.ReturnError("未比对到人脸信息");
                }
                com.alibaba.fastjson.JSONArray array = jsonObject.getJSONArray("ryxx");
                //测试数据
//                String jsonArr="[\n" +
//                        "\t{\n" +
//                        "\t\t\"csrq\": \"2023-08-07T22:59:38+08:00\",\n" +
//                        "\t\t\"ryxm\": \"aaa\",\n" +
//                        "\t\t\"xb\": \"1\",\n" +
//                        "\t\t\"xbName\": \"男\",\n" +
//                        "\t\t\"zjhm\": \"425135860930001\",\n" +
//                        "\t\t\"xsd\": \"90%\",\n" +
//                        "\t\t\"url\": \"data:image/jpeg;base64,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\"\n" +
//                        "\t},\n" +
//                        "\t{\n" +
//                        "\t\t\"csrq\": \"2023-08-07T22:59:38+08:00\",\n" +
//                        "\t\t\"ryxm\": \"ccc\",\n" +
//                        "\t\t\"xb\": \"1\",\n" +
//                        "\t\t\"xbName\": \"男\",\n" +
//                        "\t\t\"zjhm\": \"425135860930001\",\n" +
//                        "\t\t\"xsd\": \"90%\",\n" +
//                        "\t\t\"faceUrl\":\"https://img2.baidu.com/it/u=2867800872,2659077058&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=333\"\n" +
//                        "\n" +
//                        "\t}\n" +
//                        "]";
//                com.alibaba.fastjson.JSONArray array = com.alibaba.fastjson.JSONArray.parseArray(jsonArr);
                for (int i = 0; i < array.size(); i++) {
                    RlbdRyxx rlbdRyxx = new RlbdRyxx();
                    com.alibaba.fastjson.JSONObject object = array.getJSONObject(i);
                    rlbdRyxx.setCsrq(object.getString("csrq"));
                    rlbdRyxx.setXm(object.getString("ryxm"));
                    rlbdRyxx.setSex(Integer.valueOf(object.getString("xb")));
                    rlbdRyxx.setSexName(object.getString("xbName"));
                    rlbdRyxx.setSfzh(object.getString("zjhm"));
                    String url = object.getString("url");
                    if(StringUtils.isNotEmpty(url)){
                        rlbdRyxx.setFacePicData(url.split(",")[1]);
                    }
                    rlbdRyxx.setFacePicUrl(object.getString("faceUrl"));
                    rlbdRyxx.setXxd(object.getString("xsd"));
                    ckList.add(rlbdRyxx);
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                ckList.forEach(r -> {
                    if(r.getCsrq() == null||r.getCsrq().toCharArray().length==4){
                        String idCard = r.getSfzh();
                        if(StringUtils.isNotEmpty(idCard)){
                            try {
                                Date birthdate = dateFormat.parse(idCard.substring(6, 14));
                                r.setCsrq(DateTimeUtils.getDateStr(birthdate,13));
                            } catch (ParseException e) {
                                // ...
                            }
                        }
                    }
                    if(r.getXxd() != null && !String.valueOf(r.getXxd()).contains("%")){
                        r.setXxd(r.getXxd()+"%");
                    }
                });
                ckList.sort(Comparator.comparing(e->new BigDecimal(e.getXxd().replace("%",""))));
                Collections.reverse(ckList);
                rlbdResult.setCkList(ckList);
                returnResult.setCode("200");
                returnResult.setData(rlbdResult);
            }catch (Exception e) {
                e.printStackTrace();
                returnResult.setCode("500");
                returnResult.setMessage("警务百度人脸比对错误：" + SysUtil.getExceptionDetail(e));
            }
        }
        return returnResult;
    }


    /**
     * 检验人员身份证和姓名是否匹配
     * @param sfzh
     * @param xm
     * @return
     */
    @Override
    public ApiReturnResult<?> checkSfzhAndXm(HttpServletRequest request,String sfzh, String xm) {
        ApiYthbaService ythService = ServiceContext.getServiceByClass(ApiYthbaService.class);
        SessionUser user = WebContext.getSessionUser();

        Map<String, String> param = new HashMap<String, String>();
        try {
            if (StringUtils.isNotEmpty(xm) && StringUtils.isNotEmpty(sfzh)) {
                param.put("clientIp", oConvertUtils.getIpAddrByRequest(request));
                param.put("sfzh",user.getIdCard());
                param.put("name",user.getName());
                param.put("orgCode",user.getOrgCode());
                param.put("orgName",user.getOrgName());
                logger.info(String.format("常口库调用参数：%s",param));
                Map<String, Object> ryResult = ythService.getGabPageData(sfzh, xm, param);
                logger.info(String.format("常口库调用结果：%s",ryResult));
                if(ryResult.get("success") != null && (boolean)ryResult.get("success")){
                    return ResultUtil.ReturnSuccess("常口库查询异常",ParamUtil.builder().accept("sfpp",false).toMap());
                }else if((Integer) ryResult.get("total") != null && (Integer) ryResult.get("total") == 0){
                    return ResultUtil.ReturnSuccess("身份证和姓名不匹配",ParamUtil.builder().accept("sfpp",false).toMap());
                }else{
                    return ResultUtil.ReturnSuccess("身份证和姓名匹配",ParamUtil.builder().accept("sfpp",true).toMap());
                }
                /*if (ryResult != null && !ryResult.isEmpty() && (Integer) ryResult.get("total") != null && (Integer) ryResult.get("total") > 0) {
                    return ResultUtil.ReturnSuccess("身份证和姓名匹配",ParamUtil.builder().accept("sfpp",true).toMap());
                } else {
                    return ResultUtil.ReturnSuccess("身份证和姓名不匹配",ParamUtil.builder().accept("sfpp",false).toMap());
                }*/
            }else {
                return ResultUtil.ReturnError("身份证和姓名为空");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.ReturnError("系统异常:"+e.getMessage());
        }
    }
}
