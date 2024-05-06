package com.wckj.taskClient.task;

import java.util.List;

import com.wckj.taskClient.local.entity.TaskCategoryObj;
import com.wckj.taskClient.local.entity.TaskJobObj;

public interface TaskManager {

	/**
	 * 获取所有任务分类
	 * @return List<TaskCategoryObj>
	 */
	public List<TaskCategoryObj> getTaskCategorys();
	
	/**
	 * 获取所有任务
	 * @return List<TaskJobObj>
	 */
	public List<TaskJobObj> getTaskJobs(); 
	
	/**
	 * 通过分类标识和任务标识获取任务
	 * @param categoryMark String 分类标识
	 * @param mark String 任务标识
	 * @return TaskJobObj
	 */
	public TaskJobObj getTaskJob(String categoryMark, String mark);
	
	/**
	 * 通过分类标识获取分类对象
	 * @param categoryMark String 分类标识
	 * @return TaskCategoryObj
	 */
	public TaskCategoryObj getTaskCategoryObj(String categoryMark);
}
