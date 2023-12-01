package com.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.core.modle.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> listMenusByUserInfoId(Integer userInfoId);

}
