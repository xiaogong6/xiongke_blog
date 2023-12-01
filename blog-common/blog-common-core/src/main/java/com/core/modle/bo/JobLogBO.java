package com.core.modle.bo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Program: xiongke_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-11-30
 */
@Data
public class JobLogBO implements Serializable {

    @Serial
    private static final long serialVersionUID = -1L;

    private Integer id;

    private Integer jobId;

    private String jobName;

    private String jobGroup;

    private String invokeTarget;

    private String jobMessage;

    private Integer status;

    private String exceptionInfo;

    private LocalDateTime createTime;

    private Date startTime;

    private Date endTime;

}
