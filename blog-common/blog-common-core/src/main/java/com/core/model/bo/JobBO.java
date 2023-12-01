package com.core.model.bo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Program: my_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-11-30
 */
@Data
public class JobBO implements Serializable {

    @Serial
    private static final long serialVersionUID = -1L;

    private Integer id;

    private String jobName;

    private String jobGroup;

    private String invokeTarget;

    private String cronExpression;

    private Integer misfirePolicy;

    private Integer concurrent;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String remark;

    private Date nextValidTime;

}
