package com.wckj.taskClient.local.entity;

import java.util.HashMap;
import java.util.Map;

public class TaskJobObj {

	private String name;
	
	private String mark;
	
	private String status;
	
	private String desc;
	
	private String jobClass;
	
	private Map<String,String> jobParams = new HashMap<String,String>(0); 
	
	private String cronExp;
	
	private String categoryMark;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public Map<String, String> getJobParams() {
		return jobParams;
	}

	public void setJobParams(Map<String, String> jobParams) {
		this.jobParams = jobParams;
	}

	public String getCronExp() {
		return cronExp;
	}

	public void setCronExp(String cronExp) {
		this.cronExp = cronExp;
	}

	public String getCategoryMark() {
		return categoryMark;
	}

	public void setCategoryMark(String categoryMark) {
		this.categoryMark = categoryMark;
	}
}
