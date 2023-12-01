package com.core.quartz;


import com.core.modle.bo.JobBO;
import com.core.util.JobInvokeUtil;
import org.quartz.JobExecutionContext;

/**
 * @author xiongke
 */
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, JobBO job) throws Exception {
        JobInvokeUtil.invokeMethod(job);
    }
}
