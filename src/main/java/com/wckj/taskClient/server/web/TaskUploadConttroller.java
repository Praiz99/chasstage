package com.wckj.taskClient.server.web;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.wckj.framework.spring.ContextPropertyConfigurer;
import com.wckj.taskClient.cons.ConfigurationCons;

@Controller
@RequestMapping("/task/http")
public class TaskUploadConttroller {

	@Resource(name = "propertyConfigurer")
	private ContextPropertyConfigurer contextPropertyConfigurer;

	@SuppressWarnings("static-access")
	private String packagePaths = contextPropertyConfigurer.getPropertyValue(ConfigurationCons.TASKCLIENT_ANOTATION_PACKAGES);


	/**
	 * 上传任务文件
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 * @throws IllegalStateException
	 */
	@RequestMapping(value = "/timeTaskFileUpload")
	@ResponseBody
	public Map<String, Object> timeTaskFileUpload(@RequestParam(value = "file", required = false) CommonsMultipartFile file)
			throws IllegalStateException, Exception {
		Map<String, Object> result = new HashMap<String, Object>(0);
		String classPath = getClassPath() + packagePaths.replace(".", "/") + "/";
		String fileName = file.getOriginalFilename();
		String path = classPath + fileName;
		File newFile = new File(path);
		file.transferTo(newFile);
		result.put("msg", "文件上传成功！");
		return result;
	}

	/**
	 * 获取工程存放任务路径
	 * 
	 * @return
	 */
	public String getClassPath() {
		String classPath = TaskUploadConttroller.class.getClassLoader().getResource("/").getPath();
		return classPath;
	}

}
