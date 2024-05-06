package com.wckj.taskClient.util;

import javax.annotation.Resource;

import com.wckj.framework.json.jackson.JsonUtil;
import com.wckj.framework.spring.ContextPropertyConfigurer;
import com.wckj.taskClient.cons.ConfigurationCons;
import com.wckj.taskClient.server.entity.BatchDeployData;

public class BatchUtil {
	@Resource(name = "propertyConfigurer")
	private static ContextPropertyConfigurer contextPropertyConfigurer;

	@SuppressWarnings("static-access")
	public static BatchDeployData getDeployData(String taskMark, String type) {
		String consoleIpPort = contextPropertyConfigurer
				.getPropertyValue(ConfigurationCons.TASKCLIENT_SERVER);
		String url = consoleIpPort
				+ "rest/jdone/task/http/getBatchDeployData?taskMark="
				+ taskMark + "&type=" + type;
		try {
			String jsonStr = HttpClientUtil.getMethod(url);
			if (!"".equals(jsonStr)) {
				BatchDeployData batchDeployData = JsonUtil
						.getObjectFromJsonString(jsonStr, BatchDeployData.class);
				return batchDeployData;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
