package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.core.modle.entity.Resource;
import com.api.dto.other.LabelOptionDTO;
import com.api.dto.resource.ResourceDTO;
import com.api.vo.other.ConditionVO;
import com.api.vo.other.ResourceVO;

import java.util.List;

public interface ResourceService extends IService<Resource> {

    void importSwagger();

    void saveOrUpdateResource(ResourceVO resourceVO);

    void deleteResource(Integer resourceId);

    List<ResourceDTO> listResources(ConditionVO conditionVO);

    List<LabelOptionDTO> listResourceOption();

}
