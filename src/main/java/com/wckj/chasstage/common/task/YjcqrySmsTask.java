package com.wckj.chasstage.common.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wckj.chasstage.common.util.HttpClientUtils;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.baqry.entity.ChasBaqryxx;
import com.wckj.chasstage.modules.baqry.service.ChasBaqryxxService;
import com.wckj.chasstage.modules.yjlb.entity.ChasYjlb;
import com.wckj.framework.core.utils.DateUtil;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.jdone.modules.sys.util.DicUtil;
import com.wckj.jdone.modules.sys.util.MD5Util;
import com.wckj.jdone.modules.sys.util.SysUtil;
import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @Author: QYT
 * @Date: 2023/7/4 4:13 下午
 * @Description:办案区夜间即将超期人员信息发送至值班领导处
 */
@Component("yjcqrySmsTask")
public class YjcqrySmsTask {

    private static final Logger log = LoggerFactory.getLogger(YjcqrySmsTask.class);

    @Autowired
    private ChasBaqryxxService baqryxxService;

    public void sendSms(String baqid) {
        log.info("开始执行办案区夜间即将超期人员信息发送至值班领导处定时任务");
        try {
            //拼接message

            log.info("获取超期人员组成短信消息start");
            Map<String, Object> params = new HashMap<>(16);
            params.put("yjcqry", "1");
            params.put("baqid", baqid);
            String cjkssj = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd") + " 18:00";
            params.put("cjkssj", cjkssj);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, 1);
            String cjjssj = DateUtil.getDateFormat(calendar.getTime(), "yyyy-MM-dd") + " 09:00";
            params.put("cjjssj", cjjssj);
            log.info("查询参数:" + JSON.toJSONString(params));
            List<ChasBaqryxx> baqryxxes = baqryxxService.findList(params, " R_RSSJ");
            log.info("查询结束,总人数:" + baqryxxes.size());
            if (baqryxxes != null && baqryxxes.size() > 0) {
                String message = cjkssj + "至" + cjjssj + "即将超期人员共有" + baqryxxes.size() + "人,具体人员信息如下：";
                int count = 1;
                for (ChasBaqryxx chasBaqryxx : baqryxxes) {
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(chasBaqryxx.getRRssj());
                    calendar1.add(Calendar.DATE, 1);
                    message = message + count + "、" + chasBaqryxx.getRyxm() + "," + DicUtil.translate("CHAS_ZD_ZB_XB", chasBaqryxx.getXb()) +
                            ",人员超期预警时间为：" + DateUtil.getDateFormat(calendar1.getTime(), "yyyy-MM-dd HH:mm:ss") + ";";
                    count++;
                }
                log.info("短信内容:" + message);
                log.info("获取超期人员组成短信消息end");

                log.info("获取领导电话start");
                //获取领导电话
                String getClassListIp = SysUtil.getParamValue("DZX_CLASS_LIST_IP");
                String dwdm = "执法办案管理中心";
                String rq = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd");
                String url = "http://" + getClassListIp + "/Classes.asmx/GetClassList?dwdm=" + dwdm + "&rq=" + rq;
                log.info("url=" + url);
                JSONObject result = HttpClientUtils.httpGet(url);
                log.info("result:" + result.toJSONString());
                boolean isSuccess = result.getBoolean("success");
                if (!isSuccess) {
                    throw new Exception("接口调用成功,但是接口返回数据失败:" + result.getString("msg"));
                }
                StringBuilder dh = new StringBuilder();
                JSONArray jsonArray = result.getJSONArray("data");
                for (int i = 0; i < jsonArray.size(); i++) {
                    dh.append(jsonArray.getJSONObject(i).getString("dh")).append(",");
                }
                String leaderDh = dh.substring(0, dh.length() - 1);
                log.info("领导电话:" + leaderDh);
                if (StringUtils.isEmpty(leaderDh)) {
                    throw new Exception("返回数据无领导电话");
                }
                log.info("获取领导电话end");

                log.info("发送短信start");
                //发送短信
                Map<String, Object> submit = new HashMap<>(16);
                String gaSmsParam = SysUtil.getParamValue("GA_SMS_PARAM");
                log.info("系统参数GA_SMS_PARAM=" + JSON.toJSONString(gaSmsParam));
                JSONObject jsonObject = JSONObject.parseObject(gaSmsParam);
                String secretKey = jsonObject.getString("secretKey");
                submit.put("ecName", jsonObject.getString("ecName"));
                submit.put("apId", jsonObject.getString("apId"));
                submit.put("secretKey", secretKey);
                submit.put("mobiles", leaderDh);
                submit.put("content", message);
                submit.put("sign", jsonObject.getString("sign"));
                submit.put("addSerial", "");
                String stringBuilder = String.valueOf(submit.get("ecName")) +
                        submit.get("apId") +
                        secretKey +
                        leaderDh +
                        message +
                        submit.get("sign") +
                        submit.get("addSerial");
                String selfMac = MD5Util.string2MD5(stringBuilder);
                submit.put("mac", selfMac);
                String param = JSON.toJSONString(submit);
                log.info("param:" + param);
                String gaUrl = "http://" + SysUtil.getParamValue("DZX_GAWPT_IP") + "/sms/norsubmit";
                log.info("gaUrl:" + gaUrl);
                String base64 = Base64.encodeBase64String(param.getBytes());
                log.info("base64:" + base64);
                JSONObject gaResult = HttpClientUtils.httpPost(gaUrl, base64);
                log.info("gaResult:" + gaResult.toJSONString());
                if (!result.getBoolean("success")) {
                    throw new Exception("短信发送接口调用成功,但是接口返回数据失败:" + result.getString("msg"));
                }
            }

        } catch (Exception e) {
            log.error("办案区夜间即将超期人员信息发送至值班领导处定时任务", e);
            e.printStackTrace();
        }
    }
}
