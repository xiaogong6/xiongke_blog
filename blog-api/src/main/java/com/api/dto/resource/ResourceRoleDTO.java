package com.api.dto.resource;

import lombok.Data;

import java.util.List;

@Data
public class ResourceRoleDTO {

    private Integer id;

    private String url;

    private String requestMethod;

    private List<String> roleList;

}
