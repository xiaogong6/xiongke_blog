package com.core.model.bo;

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
public class OperationLogBO implements Serializable {

    @Serial
    private static final long serialVersionUID = -1L;

    private Integer id;

    private String optModule;

    private String optUri;

    private String optType;

    private String optMethod;

    private String optDesc;

    private String requestMethod;

    private String requestParam;

    private String responseData;

    private Integer userId;

    private String nickname;

    private String ipAddress;

    private String ipSource;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
