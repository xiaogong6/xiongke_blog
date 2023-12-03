package com.core.listener;

import com.core.event.ExceptionLogEvent;
import com.core.event.OperationLogEvent;
import com.core.mapper.ExceptionLogMapper;
import com.core.mapper.OperationLogMapper;
import com.core.modle.entity.ExceptionLog;
import com.core.modle.entity.OperationLog;
import jakarta.annotation.Resource;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author xiongke
 */
@Component
public class AuroraListener {

    @Resource
    private OperationLogMapper operationLogMapper;

    @Resource
    private ExceptionLogMapper exceptionLogMapper;

    @Async
    @EventListener(OperationLogEvent.class)
    public void saveOperationLog(OperationLogEvent operationLogEvent) {
        operationLogMapper.insert((OperationLog) operationLogEvent.getSource());
    }

    @Async
    @EventListener(ExceptionLogEvent.class)
    public void saveExceptionLog(ExceptionLogEvent exceptionLogEvent) {
        exceptionLogMapper.insert((ExceptionLog) exceptionLogEvent.getSource());
    }

}
