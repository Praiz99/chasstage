package com.wckj.chasstage.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * HttpClient4.3工具类
 */
public class HttpClientUtils {
    private static Logger log = LoggerFactory.getLogger(HttpClientUtils.class);
    private static RequestConfig requestConfig = null;
    private static String charset = "utf-8";
    static {
        // 设置请求和传输超时时间
        requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
    }
    /**
     * @param url
     * @param obj 1. json字符串   2. map  3.JSONObject
     * @return JSONObject
     */
    public static JSONObject httpPost(String url, Object obj) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        // 设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        try {
            if (null != obj) {
                StringEntity entity = null;
                if (obj instanceof String) {
                    entity = new StringEntity(obj.toString(), charset);
                } else {
                    entity = new StringEntity(JSON.toJSONString(obj), charset);
                }
                entity.setContentEncoding(charset);
                entity.setContentType("application/json");
//              entity.setContentType("application/x-www-form-urlencoded");
                httpPost.setEntity(entity);
            }
            //log.debug(" {} - {} ", url, obj);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return convertResponse(response);
        } catch (Exception e) {
            log.error("error HttpClientUtils  - 异常", e);
            log.error("error HttpClientUtils 地址："+url+" ,参数："+obj);
        } finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }


    /**
     * post请求传输String参数 例如：name=Jack&sex=1&type=2
     * Content-type:application/x-www-form-urlencoded
     *
     * @param url url地址
     * @param
     * @return
     */
    public static JSONObject httpPostForm(String url, Map<String, String> params) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        try {
            if (null != params) {
                //组织请求参数
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                if (params != null && params.size() > 0) {
                    Set<String> keySet = params.keySet();
                    for (String key : keySet) {
                        paramList.add(new BasicNameValuePair(key, params.get(key)));
                    }
                }

                httpPost.setEntity(new UrlEncodedFormEntity(paramList, charset));
            }
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return convertResponse(response);
        } catch (IOException e) {
            log.error("post请求提交失败:" + url, e);
            jsonResult = JSONObject.parseObject(e.toString());
        } finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }

    /**
     * 发送get请求
     *
     * @param url 路径
     * @return
     */
    public static JSONObject httpGet(String url) {
        // get请求返回结果
        JSONObject jsonResult = null;
        CloseableHttpClient client = HttpClients.createDefault();
        // 发送get请求
        HttpGet request = new HttpGet(url);
        request.setConfig(requestConfig);
        try {
            CloseableHttpResponse response = client.execute(request);
            jsonResult = convertResponse(response);
        } catch (Exception e) {
            log.error("get请求提交失败:" + url, e);
        } finally {
            request.releaseConnection();
        }
        return jsonResult;
    }

    private static JSONObject convertResponse(CloseableHttpResponse response) throws IOException, ParseException {
        // 读取服务器返回过来的json字符串数据
        HttpEntity entity = response.getEntity();
        String strResult = EntityUtils.toString(entity, "utf-8");
        // 把json字符串转换成json对象
        return JSONObject.parseObject(strResult);
    }


    public static String doPost(String url,String body) {
        // 定义httpClient和response
        String responseContent = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        try {
            // 创建默认的httpClient实例
            httpClient = HttpClients.createDefault();
            // 定义Post请求
            HttpPost httpPost = new HttpPost(url);
            // 设置配置
            RequestConfig.Builder builder = createBuilder();
            RequestConfig config = builder.build();
            httpPost.setConfig(config);
            // 设置请求头
            httpPost.setHeader("Accept", MediaType.APPLICATION_JSON_VALUE);
            //根据请求头来设置是Body内容，还是表单形式。 APPLICATION_JSON_UTF8_VALUE：body是json或者txt格式
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
            // 发送请求得到返回数据
            httpPost.setEntity(new StringEntity(body, "UTF-8"));
            response = httpClient.execute(httpPost);
            // 状态码
            // 响应内容
            HttpEntity entity = response.getEntity();
            // 响应内容
            responseContent = EntityUtils.toString(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            // 关闭流
            closeStream(response);
            closeStream(httpClient);
        }
        return responseContent;
    }

    public static void closeStream(Closeable c) {
        // 流不为空
        if (c != null) {
            try {
                // 流关闭
                c.close();
            } catch (Exception ex) {
                System.out.println("closeStream failed");
            }
        }
    }


    /**
     *
     * 功能描述: <br>
     * 创建默认Builder
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private static RequestConfig.Builder createBuilder() {
        // init Builder and init TIME_OUT
        return RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(15000)
                .setConnectionRequestTimeout(15000);
    }

    /**
     * @param url
     * @param obj 1. json字符串   2. map  3.JSONObject
     * @return JSONObject
     */
    public static JSONObject httpPostObj(String url, Object obj) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        // 设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        try {
            if (null != obj) {
                StringEntity entity = null;
                if (obj instanceof String) {
                    entity = new StringEntity(obj.toString(), charset);
                } else {
                    entity = new StringEntity(JSON.toJSONString(obj), charset);
                }
                entity.setContentEncoding(charset);
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            //log.debug(" {} - {} ", url, obj);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return convertResponse(response);
        } catch (Exception e) {
            log.error("error HttpClientUtils  - 异常", e);
            log.error("error HttpClientUtils 地址："+url+" ,参数："+obj);
        } finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }

    public static JSONObject httpPostFormJson(String url, Map<String, Object> params,Map<String,String> headers) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        try {
            if (null != params) {
                //组织请求参数
                StringEntity s = new StringEntity(JSON.toJSONString(params));
                if(!headers.isEmpty()){
                    for (String key : headers.keySet()) {
                        httpPost.setHeader(key,headers.get(key));
                    }
                    s.setContentType(headers.get("Content-Type"));
                }
                s.setContentEncoding(charset);
                httpPost.setEntity(s);
            }
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return convertResponse(response);
        } catch (IOException e) {
            log.error("post请求提交失败:" + url, e);
            jsonResult = JSONObject.parseObject(e.toString());
        } finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }

}
