package com.core.modle.bo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Program: my_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
@Data
public class ExceptionLogBO implements Serializable {

    @Serial
    private static final long serialVersionUID = -1L;

    private Integer id;

    private String optUri;

    private String optMethod;

    private String requestMethod;

    private String requestParam;

    private String optDesc;

    private String exceptionInfo;

    private String ipAddress;

    private String ipSource;

    private LocalDateTime createTime;

}
