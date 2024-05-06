package com.wckj.taskClient.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpClientUtil {

	/**
	 * 发送GET请求
	 * 
	 * @param url
	 * @return
	 * @throws IOException 
	 */
	public static String getMethod(String url) throws IOException {
		String responseBody = "";
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 创建GET方法的实例
		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		// 使用系统提供的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		try {
			// 执行getMethod
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + getMethod.getStatusLine());
				return responseBody;
			}
			// 使用getResponseBodyAsString()取返回内容
			// responseBody = getMethod.getResponseBodyAsString();
			// 使用getResponseBodyAsStream()取返回内容
			InputStream stream = getMethod.getResponseBodyAsStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			StringBuffer buf = new StringBuffer();
			String line;
			while (null != (line = br.readLine())) {
				buf.append(line).append("\n");
			}
			responseBody = buf.toString();
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			throw e;
		} catch (IOException e) {
			// 发生网络异常
			throw e;
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}

		return responseBody;
	}

	/**
	 * 文件下载
	 * 
	 * @param url
	 * @throws IOException 
	 */
	public static void fileDownload(String url, String filePath) throws IOException {
		HttpClient client = new HttpClient();
		GetMethod httpGet = new GetMethod(url);
		try {
			client.executeMethod(httpGet);

			InputStream in = httpGet.getResponseBodyAsStream();

			FileOutputStream out = new FileOutputStream(new File(filePath));

			byte[] b = new byte[2048];
			int len = 0;
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
			}
			in.close();
			out.close();

		} catch (HttpException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			httpGet.releaseConnection();
		}
	}

	/**
	 * 发送POST请求
	 * 
	 * @param url
	 * @param parts
	 *            Part[] parts = new Part[]{ new FilePart("file", newFile), new
	 *            StringPart("key",value) };
	 * @return
	 * @throws IOException 
	 */
	public static String postMethod(String url, Part[] parts) throws IOException {
		String responseBody = "";
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15 * 1000);
		// 创建GET方法的实例
		PostMethod postMethod = new PostMethod(url);
		// 将表单的值放入postMethod中
		MultipartRequestEntity entity = new MultipartRequestEntity(parts, postMethod.getParams());
		postMethod.setRequestEntity(entity);
		try {
			// 执行postMethod
			int statusCode = httpClient.executeMethod(postMethod);
			// HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发
			// 301或者302
			if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
				// 从头中取出转向的地址
				Header locationHeader = postMethod.getResponseHeader("location");
				String location = null;
				if (locationHeader != null) {
					location = locationHeader.getValue();
					//System.out.println("The page was redirected to:" + location);
				} else {
					throw new IOException("Location field value is null.");
				}
				return responseBody;
			}
			if (statusCode != HttpStatus.SC_OK) {
				return responseBody;
			}
			// 使用getResponseBodyAsString()取返回内容
			// responseBody = postMethod.getResponseBodyAsString();
			// 使用getResponseBodyAsStream()取返回内容
			InputStream stream = postMethod.getResponseBodyAsStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			StringBuffer buf = new StringBuffer();
			String line;
			while (null != (line = br.readLine())) {
				buf.append(line).append("\n");
			}
			responseBody = buf.toString();
		} catch (HttpException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}

		return responseBody;
	}

}
