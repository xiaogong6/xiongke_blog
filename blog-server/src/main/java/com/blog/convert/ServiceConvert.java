package com.blog.convert;

import com.blog.modle.bo.UserAuthBO;
import com.blog.modle.entity.UserAuth;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Program: my_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
@Mapper
public interface ServiceConvert {

    ServiceConvert INSTANCE = Mappers.getMapper(ServiceConvert.class);

    UserAuth converToUserAuth(UserAuthBO userAuthBO);
}
