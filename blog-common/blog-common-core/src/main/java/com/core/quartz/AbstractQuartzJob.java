package com.core.quartz;

import com.blog.mapper.JobLogMapper;
import com.blog.modle.entity.JobLog;
import com.api.constant.ScheduleConstant;
import com.core.model.JobBO;
import com.core.model.JobLogBO;
import com.core.util.BeanCopyUtil;
import com.core.util.ExceptionUtil;
import com.core.util.SpringUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.Date;

import static com.api.constant.CommonConstant.ONE;
import static com.api.constant.CommonConstant.ZERO;


public abstract class AbstractQuartzJob implements org.quartz.Job {

    private static final Logger log = LoggerFactory.getLogger(AbstractQuartzJob.class);

    private static final ThreadLocal<Date> THREAD_LOCAL = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobBO job = new JobBO();
        BeanUtils.copyProperties(context.getMergedJobDataMap().get(ScheduleConstant.TASK_PROPERTIES), job);
        try {
            before(context, job);
            doExecute(context, job);
            after(context, job, null);
        } catch (Exception e) {
            log.error("任务执行异常:", e);
            after(context, job, e);
        }
    }

    protected void before(JobExecutionContext context, JobBO job) {
        THREAD_LOCAL.set(new Date());
    }

    protected void after(JobExecutionContext context, JobBO job, Exception e) {
        Date startTime = THREAD_LOCAL.get();
        THREAD_LOCAL.remove();
        final JobLogBO jobLog = new JobLogBO();
        jobLog.setJobId(job.getId());
        jobLog.setJobName(job.getJobName());
        jobLog.setJobGroup(job.getJobGroup());
        jobLog.setInvokeTarget(job.getInvokeTarget());
        jobLog.setStartTime(startTime);
        jobLog.setEndTime(new Date());
        long runMs = jobLog.getEndTime().getTime() - jobLog.getStartTime().getTime();
        jobLog.setJobMessage(jobLog.getJobName() + " 总共耗时：" + runMs + "毫秒");
        if (e != null) {
            jobLog.setStatus(ZERO);
            jobLog.setExceptionInfo(ExceptionUtil.getTrace(e));
        } else {
            jobLog.setStatus(ONE);
        }
        JobLog jobLog1 = BeanCopyUtil.copyObject(jobLog, JobLog.class);
        SpringUtil.getBean(JobLogMapper.class).insert(jobLog1);
    }

    protected abstract void doExecute(JobExecutionContext context, JobBO job) throws Exception;
}
