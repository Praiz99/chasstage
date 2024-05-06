package com.wckj.chasstage.common.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wckj.chasstage.api.def.zyry.model.ZyryParam;
import com.wckj.chasstage.api.def.zyry.service.ApiZyryyjService;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.framework.api.ApiReturnResult;
import com.wckj.framework.core.utils.DateUtil;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.jdone.modules.sys.util.SysUtil;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: QYT
 * @Date: 2023/6/15 1:15 下午
 * @Description:在押人员预警
 */
@Component("zyryyjTask")
public class ZyryyjTask extends BaseAlarmService {

    private static final Logger log = LoggerFactory.getLogger(ZyryyjTask.class);

    @Autowired
    private ApiZyryyjService apiZyryyjService;


    public void zyryAlarm(String baqid) {
        log.info("开始执行在押人员预警定时任务");
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar kssj = Calendar.getInstance();
            Calendar jssj = Calendar.getInstance();
            kssj.setTime(new Date());
            jssj.setTime(new Date());
            kssj.add(Calendar.HOUR, -25);
            jssj.add(Calendar.HOUR, -24);
            ZyryParam zyryParam = new ZyryParam();
            zyryParam.setBaqid(baqid);
            zyryParam.setKssj(df.format(kssj.getTime()));
            zyryParam.setJssj(df.format(jssj.getTime()));
            ApiReturnResult<String> apiReturnResult = apiZyryyjService.pushZyryAlarm(zyryParam);
            if ("500".equals(apiReturnResult.getCode())) {
                throw new Exception(apiReturnResult.getMessage());
            }
        } catch (Exception e) {
            log.error("在押人员预警", e);
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Calendar kssj = Calendar.getInstance();
//        Calendar jssj = Calendar.getInstance();
//        kssj.setTime(new Date());
//        jssj.setTime(new Date());
//        kssj.add(Calendar.HOUR, -25);
//        jssj.add(Calendar.HOUR, -24);
//        System.out.println("kssj=" + df.format(kssj.getTime()));
//        System.out.println("jssj=" + df.format(jssj.getTime()));
//    }
}
