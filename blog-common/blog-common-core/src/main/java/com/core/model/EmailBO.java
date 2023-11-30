package com.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * @Program: my_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-11-30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailBO implements Serializable {

    @Serial
    private static final long serialVersionUID = -1L;

    private String email;

    private String subject;

    private Map<String, Object> commentMap;

    private String template;

}
