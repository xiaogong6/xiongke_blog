package com.core.event;

import com.core.modle.bo.OperationLogBO;
import org.springframework.context.ApplicationEvent;

/**
 * @author xiongke
 */
public class OperationLogEvent extends ApplicationEvent {

    public OperationLogEvent(OperationLogBO operationLog) {
        super(operationLog);
    }

}
