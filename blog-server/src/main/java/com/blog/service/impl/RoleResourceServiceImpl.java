package com.blog.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.service.RoleResourceService;
import com.core.mapper.RoleResourceMapper;
import com.core.modle.entity.RoleResource;
import org.springframework.stereotype.Service;

@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements RoleResourceService {

}
