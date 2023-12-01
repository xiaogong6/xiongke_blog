package com.core.quartz;


import com.baomidou.mybatisplus.extension.service.IService;
import com.core.modle.bo.JobBO;
import com.core.util.JobInvokeUtil;
import org.quartz.JobExecutionContext;

/**
 * @author xiongke
 */
public class QuartzJobExecution extends AbstractQuartzJob {

    @Override
    protected void doExecute(JobExecutionContext context, JobBO job) throws Exception {
        JobInvokeUtil.invokeMethod(job);
    }
}
