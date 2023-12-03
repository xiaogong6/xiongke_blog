package com.blog.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.service.UserRoleService;
import com.core.mapper.UserRoleMapper;
import com.core.modle.entity.UserRole;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
