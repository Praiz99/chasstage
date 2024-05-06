package com.wckj.taskClient.job;

import com.wckj.chasstage.common.task.DrsxTask;
import com.wckj.chasstage.common.task.YjcqrySmsTask;
import com.wckj.framework.core.ServiceContext;
import com.wckj.framework.core.utils.StringUtils;
import com.wckj.framework.spring.SpringContextHolder;
import com.wckj.jdone.modules.task.entity.TaskExeStrategy;
import com.wckj.jdone.modules.task.entity.TaskParam;
import com.wckj.jdone.modules.task.entity.Taskinst;
import com.wckj.jdone.modules.task.service.TaskExeStrategyService;
import com.wckj.jdone.modules.task.service.TaskParamService;
import com.wckj.jdone.modules.task.service.TaskinstService;
import com.wckj.taskClient.util.JobDesc;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: QYT
 * @Date: 2023/7/4 4:11 下午
 * @Description:办案区夜间即将超期人员信息发送至值班领导处
 */
@JobDesc(description = "超期人员发送值班领导")
public class YjcqrySmsJob implements Job {

    private static final Logger log= LoggerFactory.getLogger(YjcqrySmsJob.class);

    private TaskParamService taskParamService = SpringContextHolder.getContext().getBean(TaskParamService.class);
    private TaskinstService taskinstService = SpringContextHolder.getContext().getBean(TaskinstService.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.debug("YjcqrySmsJob定时任务(办案区夜间即将超期人员信息发送至值班领导处)启动");
        String baqid = "";
        try {
            YjcqrySmsTask task = ServiceContext.getServiceByClass(YjcqrySmsTask.class);
            Map<String, Object> param = new HashMap<>(16);
            param.put("taskType", jobExecutionContext.getJobDetail().getKey().getGroup());
            param.put("taskMark", jobExecutionContext.getJobDetail().getKey().getName());
            List<Taskinst> taskinstList = taskinstService.findList(param, "");
            if (taskinstList != null && taskinstList.size() > 0) {
                Taskinst taskinst = taskinstList.get(0);
                String taskId = taskinst.getId();
                Map<String, Object> param2 = new HashMap<>(16);
                param2.put("taskId", taskId);
                List<TaskParam> taskParams = taskParamService.findList(param2, "");
                for (TaskParam t : taskParams) {
                    if ("baqid".equals(t.getParamKey())) {
                        baqid = t.getParamValue();
                    }
                }
            }
            if (StringUtils.isEmpty(baqid) || "null".equals(baqid)) {
                throw new Exception("baqid未配置");
            }
            task.sendSms(baqid);
        } catch (Exception e) {
            log.error("办案区夜间即将超期人员信息发送至值班领导处出错", e);
        }
    }
}
