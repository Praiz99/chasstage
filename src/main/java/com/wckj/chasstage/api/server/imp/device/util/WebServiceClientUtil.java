package com.wckj.chasstage.api.server.imp.device.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

public class WebServiceClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(WebServiceClientUtil.class);

    /**
     * HttpClient 调用 WebService
     *
     * @param wsUrl   webService地址，格式：http://ip:port/xxx/xxx/soap?wsdl
     * @param param 格式的入参StrDate=2023/08/02
     * @return
     */
    public static String callServiceHC(String wsUrl,String methodName, LinkedHashMap<String,Object> param) throws Exception{
        String xml = createSoapContent(methodName,param);
        String returnDatabase = doPostSoap(wsUrl,"",xml);//urn:hl7-org:v3/ZDYFWTYRK

        // 强制将返回结果转换为 UTF-8 编码
        returnDatabase = convertToUTF8(returnDatabase);
        // 解析
        returnDatabase = analyzeResult(returnDatabase);
        return returnDatabase;
    }

    /**
     * 强制转换整utp-8
     * @param input
     * @return
     */
    public static String convertToUTF8(String input) {
        try {
            byte[] utf8Bytes = input.getBytes(StandardCharsets.UTF_8);
            return new String(utf8Bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return input; // 返回原始字符串，如果转换失败
    }

    /**
     * 根据拼接 xml 字符串
     */
    public static String createSoapContent(String methodName,LinkedHashMap<String,Object> param) {
//        logger.info("开始拼接请求报文");
        //开始拼接请求报文
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        stringBuilder.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:dev=\"http://device.imp.server.api.DeviceCenter.wckj.com\">");
        stringBuilder.append("<soapenv:Header/>");
        stringBuilder.append("<soapenv:Body>");

        stringBuilder.append("<dev:"+methodName+">");
        for (String key : param.keySet()) {
            Object o = param.get(key);
            if(o instanceof List){
                List<String> list = (List<String>) o;
                for (String value : list) {
                    stringBuilder.append("<dev:"+key+">");
                    stringBuilder.append(value);
                    stringBuilder.append("</dev:"+key+">");
                }
            }else{
                stringBuilder.append("<dev:"+key+">");
                stringBuilder.append(o);
                stringBuilder.append("</dev:"+key+">");
            }
        }
        stringBuilder.append("</dev:"+methodName+">");
        stringBuilder.append("</soapenv:Body>");
        stringBuilder.append("</soapenv:Envelope>");
        logger.debug("拼接后的参数\n" + stringBuilder.toString());
        return stringBuilder.toString();
    }

    /**
     * HTTPClient 调用 WebService
     *
     * @param url
     * @param soap
     * @param SOAPAction
     * @return
     */
    public static String doPostSoap(String url, String SOAPAction,String soap) {
        //请求体
        String retStr = "";
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
            httpPost.setHeader("SOAPAction", SOAPAction);
            StringEntity data = new StringEntity(soap, Charset.forName("UTF-8"));
            httpPost.setEntity(data);
            CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                // 打印响应内容
                retStr = EntityUtils.toString(httpEntity, "UTF-8");
                logger.info("DC,ws打印响应内容:"+retStr);
            }
            // 释放资源
            closeableHttpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("doPostSoap",e);
        }
        return retStr;
    }

    /**
     * 解析返回值
     * @param soapResponse
     * @return
     */
    public static String analyzeResult(String soapResponse) {
        // 从soapResponse中提取JSON部分
        String jsonPart = null;
        try {
            if(soapResponse.length() > 0){
                int startIndex = soapResponse.indexOf("<ns:return>");
                int endIndex = soapResponse.indexOf("</ns:return>");
                jsonPart = soapResponse.substring(startIndex + "<ns:return>".length(), endIndex);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("analyzeResult异常:"+soapResponse);
            throw e;
        }
        return jsonPart;
    }

}