package com.blog.convert;

import com.api.vo.user.UserAuthVO;
import com.blog.modle.bo.UserAuthBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Program: my_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
@Mapper
public interface CommonConvert {

    CommonConvert INSTANCE = Mappers.getMapper(CommonConvert.class);

    UserAuthBO converToUserAuthBO(UserAuthVO userAuthVO);

}
