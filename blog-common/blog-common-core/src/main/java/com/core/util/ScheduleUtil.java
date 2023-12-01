package com.core.util;


import com.api.enums.JobStatusEnum;
import com.api.constant.ScheduleConstant;
import com.core.exception.TaskException;
import com.core.model.bo.JobBO;
import com.core.quartz.QuartzDisallowConcurrentExecution;
import com.core.quartz.QuartzJobExecution;
import org.quartz.*;

/**
 * @author xiongke
 */
public class ScheduleUtil {

    private static Class<? extends org.quartz.Job> getQuartzJobClass(JobBO job) {
        boolean isConcurrent = Integer.valueOf(1).equals(job.getConcurrent());
        return isConcurrent ? QuartzJobExecution.class : QuartzDisallowConcurrentExecution.class;
    }

    public static TriggerKey getTriggerKey(Integer jobId, String jobGroup) {
        return TriggerKey.triggerKey(ScheduleConstant.TASK_CLASS_NAME + jobId, jobGroup);
    }

    public static JobKey getJobKey(Integer jobId, String jobGroup) {
        return JobKey.jobKey(ScheduleConstant.TASK_CLASS_NAME + jobId, jobGroup);
    }

    public static void createScheduleJob(Scheduler scheduler, JobBO job) throws SchedulerException, TaskException {
        Class<? extends org.quartz.Job> jobClass = getQuartzJobClass(job);
        Integer jobId = job.getId();
        String jobGroup = job.getJobGroup();
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(getJobKey(jobId, jobGroup)).build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
        cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(jobId, jobGroup))
                .withSchedule(cronScheduleBuilder).build();
        jobDetail.getJobDataMap().put(ScheduleConstant.TASK_PROPERTIES, job);
        if (scheduler.checkExists(getJobKey(jobId, jobGroup))) {
            scheduler.deleteJob(getJobKey(jobId, jobGroup));
        }
        scheduler.scheduleJob(jobDetail, trigger);
        if (job.getStatus().equals(JobStatusEnum.PAUSE.getValue())) {
            scheduler.pauseJob(ScheduleUtil.getJobKey(jobId, jobGroup));
        }
    }

    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(JobBO job, CronScheduleBuilder cb)
            throws TaskException {
        switch (job.getMisfirePolicy()) {
            case ScheduleConstant.MISFIRE_DEFAULT:
                return cb;
            case ScheduleConstant.MISFIRE_IGNORE_MISFIRES:
                return cb.withMisfireHandlingInstructionIgnoreMisfires();
            case ScheduleConstant.MISFIRE_FIRE_AND_PROCEED:
                return cb.withMisfireHandlingInstructionFireAndProceed();
            case ScheduleConstant.MISFIRE_DO_NOTHING:
                return cb.withMisfireHandlingInstructionDoNothing();
            default:
                throw new TaskException("The task misfire policy '" + job.getMisfirePolicy()
                        + "' cannot be used in cron schedule tasks", TaskException.Code.CONFIG_ERROR);
        }
    }
}
