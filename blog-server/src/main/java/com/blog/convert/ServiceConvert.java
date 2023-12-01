package com.blog.convert;

import com.blog.modle.bo.ArticleBO;
import com.blog.modle.bo.UserAuthBO;
import com.blog.modle.bo.UserInfoBO;
import com.core.modle.entity.Article;
import com.core.modle.entity.UserAuth;
import com.core.modle.entity.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

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

    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "preArticleCard", ignore = true)
    @Mapping(target = "nextArticleCard", ignore = true)
    @Mapping(target = "categoryName", ignore = true)
    @Mapping(target = "author", ignore = true)
    ArticleBO convertArticleBO(Article article);

    UserInfoBO convert(UserInfo userInfo);

    List<UserInfoBO> converUserInfoBOList(List<UserInfo> list);
}
