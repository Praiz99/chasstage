package com.wckj.chasstage.api.server.imp.zyry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wckj.chasstage.api.def.zyry.model.ZyryParam;
import com.wckj.chasstage.api.def.zyry.service.ApiZyryyjService;
import com.wckj.chasstage.api.server.imp.yybb.ApiYybbServiceImpl;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.log.Logger;
import com.wckj.framework.core.log.LoggerFactory;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.jdone.modules.sys.util.SysUtil;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: QYT
 * @Date: 2023/6/16 9:36 上午
 * @Description:在押人员预警实现
 */
@Service
public class ApiZyryyjServiceImpl implements ApiZyryyjService {

    private static Logger log = LoggerFactory.getLogger(ApiZyryyjServiceImpl.class);

    @Autowired
    private ChasBaqryxxService baqryxxService;

    @Override
    public ApiReturnResult<String> pushZyryAlarm(ZyryParam zyryParam) {
        ApiReturnResult<String> returnResult = new ApiReturnResult<>();
        String baqid = zyryParam.getBaqid();
        String kssj = zyryParam.getKssj();
        String jssj = zyryParam.getJssj();;
        log.info("开始查询后推送在押人员预警,办案区ID:" + baqid);
        log.info("开始查询后推送在押人员预警,查询入所时间范围:" + kssj + "-" + jssj);
        Map<String, Object> param = new HashMap<>(16);
        param.put("rssj1", kssj);
        param.put("rssj2", jssj);
        param.put("baqid", baqid);
        String id = "";
        String ryxm = "";
        try {
            if (StringUtils.isEmpty(baqid)) {
                throw new Exception("办案区ID不能为空");
            }
            if (StringUtils.isEmpty(kssj)) {
                throw new Exception("入区时间范围开始时间不能为空");
            }
            if (StringUtils.isEmpty(jssj)) {
                throw new Exception("入区时间范围结束时间不能为空");
            }
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<ChasBaqryxx> baqryxxes = baqryxxService.findList(param, " R_RSSJ");
            for (ChasBaqryxx baqryxx : baqryxxes) {
                id = baqryxx.getId();
                ryxm = baqryxx.getRyxm();
                Date rrssj = baqryxx.getRRssj();
                Date ccssj = baqryxx.getCCssj();
                if (ccssj == null) {
                    if ((System.currentTimeMillis() - rrssj.getTime()) > (24 * 60 * 60 * 1000)) {
                        log.info("开始推送,姓名:" + ryxm + ",入所时间:" + df.format(rrssj) + "超过24小时,无出所时间");
                        JSONObject jsonObject = pushZyryByHttp(baqryxx);
                        int code = jsonObject.getInteger("code");
                        if (code != 200) {
                            throw new Exception("接口调用成功,但是返回失败:" + jsonObject.getString("msg"));
                        }
                    }
                } else {
                    if ((ccssj.getTime() - rrssj.getTime()) > (24 * 60 * 60 * 1000)) {
                        log.info("开始推送,姓名:" + ryxm + ",入所时间:" + df.format(rrssj) + "超过出所时间24小时,出所时间:" + df.format(ccssj));
                        JSONObject jsonObject = pushZyryByHttp(baqryxx);
                        int code = jsonObject.getInteger("code");
                        if (code != 200) {
                            throw new Exception("接口调用成功,但是返回失败:" + jsonObject.getString("msg"));
                        }
                    }
                }
            }
            returnResult.setCode("200");
            returnResult.setMessage("推送成功");
        } catch (Exception e) {
            e.printStackTrace();
            returnResult.setCode("500");
            returnResult.setMessage("在押人员推送异常:" + "id=" + id + ",ryxm=" + ryxm + "数据推送错误:" + e.getMessage());
        }
        return returnResult;
    }

    private JSONObject pushZyryByHttp(ChasBaqryxx baqryxx) throws Exception {
        String charset = "utf-8";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = SysUtil.getParamValue("API_ZYRY_URL") + "/yy_baygj/baygj/zyry/wcSaveOrUpdate";
        HttpPost httpPost = new HttpPost(url);
        Map<String, Object> paramList = new HashMap<>(16);
        paramList.put("state", "1");
        paramList.put("badw", baqryxx.getZbdwBh());
        paramList.put("badwmc", baqryxx.getZbdwMc());
        paramList.put("ryxm", baqryxx.getRyxm());
        paramList.put("rybh", baqryxx.getRybh());
        paramList.put("ay", baqryxx.getRyzaymc());
        paramList.put("jssj", df.format(baqryxx.getRRssj()));
        if (baqryxx.getCCssj() != null) {
            paramList.put("cssj", df.format(baqryxx.getCCssj()));
        }
        paramList.put("csyy", StringUtils.isEmpty(baqryxx.getCCsyy())? "无" : baqryxx.getCCsyy());
        Calendar yjsj = Calendar.getInstance();
        yjsj.setTime(baqryxx.getRRssj());
        yjsj.add(Calendar.HOUR, 24);
        paramList.put("yjsj", df.format(yjsj.getTime()));
        String pushString = JSON.toJSONString(paramList);
        log.info("http推送数据内容:" + pushString);
        StringEntity entity = new StringEntity(pushString, charset);
        entity.setContentEncoding(charset);
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        JSONObject result = convertResponse(response);
        log.info("http返回数据内容:" + result.toJSONString());
        return result;
    }

    private static JSONObject convertResponse(CloseableHttpResponse response) throws IOException, ParseException {
        // 读取服务器返回过来的json字符串数据
        HttpEntity entity = response.getEntity();
        String strResult = EntityUtils.toString(entity, "utf-8");
        // 把json字符串转换成json对象
        return JSONObject.parseObject(strResult);
    }
}
