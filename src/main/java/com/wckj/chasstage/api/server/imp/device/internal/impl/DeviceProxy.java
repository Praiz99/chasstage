package com.wckj.chasstage.api.server.imp.device.internal.impl;

import com.wckj.chasstage.api.server.imp.device.util.WebServiceClientUtil;
import com.wckj.chasstage.common.util.SYSCONSTANT;
import com.wckj.chasstage.modules.baq.entity.ChasBaq;
import com.wckj.chasstage.modules.baq.service.ChasBaqService;
import com.wckj.chasstage.api.server.imp.device.util.DevResult;
import com.wckj.chasstage.api.server.imp.device.util.DeviceException;
import com.wckj.framework.core.ServiceContext;
import org.apache.log4j.Logger;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class DeviceProxy {
    final static Logger log = Logger.getLogger(DeviceProxy.class);
    @Autowired
    private ChasBaqService chasBaqService;
    private Client getClient(String baqid) throws Exception {
        if(chasBaqService == null){
            chasBaqService = ServiceContext.getServiceByClass(ChasBaqService.class);
        }
        Client client = null;
        ChasBaq chasBaq=  chasBaqService.findById(baqid);
        if(chasBaq!=null){
            if(SYSCONSTANT.Y_I!=chasBaq.getSfznbaq()){
                String msg =String.format("办案区id:%s 编号:%s 非智能办案区",chasBaq.getBaqmc(),chasBaq.getId());
                log.error(msg);
                throw new DeviceException(msg);
            }
            String url = "http://" + chasBaq.getIp() + ":" + chasBaq.getPort() + "/" + chasBaq.getXtmc() + "/services/device?wsdl";
            log.info(url);
            URL _url = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection)_url.openConnection();
            httpConnection.setReadTimeout(15000);//设置http连接的读超时,单位是毫秒
            httpConnection.connect();
            client = new Client(httpConnection.getInputStream(), null);
            client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, String.valueOf( 15000 ));//设置发送的超时限制,单位是毫秒;
            client.setProperty(CommonsHttpMessageSender.DISABLE_KEEP_ALIVE, "true");
            client.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE, "true");
        }
        return client;
    }

    public DevResult invoke(String baqid, String method, Object[] param){
        DevResult r = new DevResult();
        String msg = "";
        try {
            Client client = getClient(baqid);
            if (client != null) {
                Object[] obj = (Object[]) client.invoke(method, param);
                String json = (String) obj[0];
                msg = "办案区:{"+baqid+"} 设备WebService 返回{"+json+"}";
                log.debug(msg);
                r = DevResult.parse(json);
                client.close();
            }else{
                msg = "办案区id[" + baqid + "] 未配置办案区";
                log.error(msg);
                r.setCodeMessage(3, msg);
            }
        } catch (DeviceException e) {
            r.setCodeMessage(3, msg);
        }catch (MalformedURLException e) {
            r.setCodeMessage(3, "远程服务地址错误");
            log.error("远程服务地址错误",e);
        }  catch (Exception e) {
            log.error("调用远程服务异常",e);
            r.setCodeMessage(3, "调用远程服务异常");
        }
        return r;
    }


    private String getClientNew(String baqid) throws Exception {
        if(chasBaqService == null){
            chasBaqService = ServiceContext.getServiceByClass(ChasBaqService.class);
        }
        String url  = null;
        ChasBaq chasBaq=  chasBaqService.findById(baqid);
        if(chasBaq!=null){
            if(SYSCONSTANT.Y_I!=chasBaq.getSfznbaq()){
                String msg =String.format("办案区id:%s 编号:%s 非智能办案区",chasBaq.getBaqmc(),chasBaq.getId());
                log.error(msg);
                throw new DeviceException(msg);
            }
            url = "http://" + chasBaq.getIp() + ":" + chasBaq.getPort() + "/" + chasBaq.getXtmc() + "/services/device?wsdl";
            log.info(url);
        }
        return url;
    }

    public DevResult invokeNew(String baqid, String method, LinkedHashMap<String,Object> param){
        DevResult r = new DevResult();
        String msg = "";
        try {
            String url =  getClientNew(baqid);
            if(url != null){
                String result = WebServiceClientUtil.callServiceHC(url, method, param);
                msg = "办案区:{"+baqid+"} 设备WebService 返回{"+result+"}";
                r = DevResult.parse(result);
            }else{
                msg = "办案区id[" + baqid + "] 未配置办案区";
                log.error(msg);
                r.setCodeMessage(3, msg);
            }
        } catch (DeviceException e) {
            r.setCodeMessage(3, msg);
        }catch (MalformedURLException e) {
            r.setCodeMessage(3, "远程服务地址错误");
            log.error("远程服务地址错误",e);
        }  catch (Exception e) {
            log.error("调用远程服务异常",e);
            r.setCodeMessage(3, "调用远程服务异常");
        }
        return r;
    }
}
