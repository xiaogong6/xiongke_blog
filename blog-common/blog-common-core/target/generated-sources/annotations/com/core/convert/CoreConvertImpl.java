package com.core.convert;

import com.core.modle.bo.JobLogBO;
import com.core.modle.entity.JobLog;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-26T15:02:54+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
public class CoreConvertImpl implements CoreConvert {

    @Override
    public JobLog converToJobLog(JobLogBO bo) {
        if ( bo == null ) {
            return null;
        }

        JobLog.JobLogBuilder jobLog = JobLog.builder();

        jobLog.id( bo.getId() );
        jobLog.jobId( bo.getJobId() );
        jobLog.jobName( bo.getJobName() );
        jobLog.jobGroup( bo.getJobGroup() );
        jobLog.invokeTarget( bo.getInvokeTarget() );
        jobLog.jobMessage( bo.getJobMessage() );
        jobLog.status( bo.getStatus() );
        jobLog.exceptionInfo( bo.getExceptionInfo() );
        jobLog.createTime( bo.getCreateTime() );
        jobLog.startTime( bo.getStartTime() );
        jobLog.endTime( bo.getEndTime() );

        return jobLog.build();
    }
}
