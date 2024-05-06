package com.wckj.taskClient.service.imp;

import java.util.Map;

import javax.annotation.Resource;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.springframework.stereotype.Service;

import com.wckj.taskClient.local.entity.TaskJobObj;
import com.wckj.taskClient.server.web.TimeTaskJobListener;
import com.wckj.taskClient.service.TaskManageService;

@Service
public class TaskManageServiceImp implements TaskManageService {

	@Resource(name = "quartzfactory")
	private StdScheduler sts;



	/**
	 * 删除任务
	 */
	@Override
	public void removeJob(TaskJobObj taskJobObj) {
		try {
			String categoryMark = taskJobObj.getCategoryMark();
			String jobMark = taskJobObj.getMark();
			TriggerKey triggerKey = TriggerKey.triggerKey(jobMark, categoryMark);

			sts.pauseTrigger(triggerKey);// 停止触发器
			sts.unscheduleJob(triggerKey);// 移除触发器
			sts.deleteJob(JobKey.jobKey(jobMark,categoryMark));// 删除任务
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	
	/**
	 * 启动添加任务
	 */
	@Override
	public void startJob(TaskJobObj taskJobObj) throws Exception {
		String categoryMark = taskJobObj.getCategoryMark();
		String jobMark = taskJobObj.getMark();
		String jobClass = taskJobObj.getJobClass();
		Map<String,String> jobParams = taskJobObj.getJobParams();
		Class cls = Class.forName(jobClass);
		String cronExp = taskJobObj.getCronExp();
		TriggerKey triggerKey = TriggerKey.triggerKey(jobMark, categoryMark);
		CronTrigger trigger = (CronTrigger) sts.getTrigger(triggerKey);
		if (trigger == null) {
			// 不存在，创建一个
			JobDetail job = JobBuilder.newJob(cls).withIdentity(jobMark, categoryMark).build();
			trigger = TriggerBuilder.newTrigger().withIdentity(jobMark, categoryMark)
					.withSchedule(CronScheduleBuilder.cronSchedule(cronExp)).build();
			JobDataMap jobDataMap = job.getJobDataMap();
			for(Map.Entry<String, String> entry : jobParams.entrySet()){
				jobDataMap.put(entry.getKey(), entry.getValue());
			}
			sts.scheduleJob(job, trigger);
		}else{
			// Trigger已存在，那么更新相应的定时设置
			String oldTime = trigger.getCronExpression();
			if (oldTime.equalsIgnoreCase(cronExp)) {
				// 如果新时间和老时间一样则启动任务
				sts.resumeJob(JobKey.jobKey(jobMark, categoryMark));
			} else {
				// 表达式调度构建器
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExp);
				// 按新的cronExpression表达式重新构建trigger
				trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder)
						.build();
				// 按新的trigger重新设置job执行
				sts.rescheduleJob(triggerKey, trigger);
			}
		}
	}

	/**
	 * 停止任务
	 */
	@Override
	public void pauseJob(TaskJobObj taskJobObj) throws Exception {
		String categoryMark = taskJobObj.getCategoryMark();
		String jobMark = taskJobObj.getMark(); 
		sts.pauseJob(JobKey.jobKey(jobMark, categoryMark));
	}

	/**
	 * 添加任务监听器
	 */
	@Override
	public void addJobListener(TimeTaskJobListener timeTaskJobListener) throws SchedulerException {
		sts.getListenerManager().addJobListener(timeTaskJobListener);
	}

}
