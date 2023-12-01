package com.core.event;

import com.core.modle.bo.ExceptionLogBO;
import org.springframework.context.ApplicationEvent;

/**
 * @author xiongke
 */
public class ExceptionLogEvent extends ApplicationEvent {

    public ExceptionLogEvent(ExceptionLogBO exceptionLog) {
        super(exceptionLog);
    }

}
