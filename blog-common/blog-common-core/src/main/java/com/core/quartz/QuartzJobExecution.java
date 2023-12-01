package com.core.quartz;


import com.core.model.bo.JobBO;
import com.core.util.JobInvokeUtil;
import org.quartz.JobExecutionContext;

public class QuartzJobExecution extends AbstractQuartzJob {

    @Override
    protected void doExecute(JobExecutionContext context, JobBO job) throws Exception {
        JobInvokeUtil.invokeMethod(job);
    }
}
