package com.wckj.taskClient.service;

import org.quartz.SchedulerException;

import com.wckj.taskClient.local.entity.TaskJobObj;
import com.wckj.taskClient.server.web.TimeTaskJobListener;

public interface TaskManageService {
	
	
	/**
	 * 启动任务接口
	 * @param taskJobObj
	 * @throws Exception 
	 */
	void startJob(TaskJobObj taskJobObj) throws Exception;
	
	
	/**
	 * 停止任务接口
	 * @param taskJobObj
	 * @throws Exception 
	 */
	void pauseJob(TaskJobObj taskJobObj) throws Exception;
	
	/**
	 * 删除任务接口
	 * @param jobName
	 */
	void removeJob(TaskJobObj taskJobObj);
	
	/**
	 * 添加任务监听器
	 * @param timeTaskJobListener
	 * @throws SchedulerException
	 */
	void addJobListener(TimeTaskJobListener timeTaskJobListener) throws SchedulerException;
}
