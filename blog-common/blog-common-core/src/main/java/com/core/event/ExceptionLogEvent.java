package com.core.event;

import com.core.model.bo.ExceptionLogBO;
import org.springframework.context.ApplicationEvent;

/**
 * @author xiongke
 */
public class ExceptionLogEvent extends ApplicationEvent {

    public ExceptionLogEvent(ExceptionLogBO exceptionLog) {
        super(exceptionLog);
    }

}
