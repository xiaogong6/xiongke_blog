package com.blog.convert;

import com.api.dto.other.ArticleSearchDTO;
import com.api.vo.article.ArticleVO;
import com.api.vo.user.UserAuthVO;
import com.api.vo.user.UserInfoVO;
import com.blog.modle.bo.ArticleBO;
import com.blog.modle.bo.UserAuthBO;
import com.blog.modle.bo.UserInfoBO;
import com.core.modle.es.ArticleSearch;
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
public interface CommonConvert {

    CommonConvert INSTANCE = Mappers.getMapper(CommonConvert.class);

    UserAuthBO converToUserAuthBO(UserAuthVO userAuthVO);

    List<ArticleSearchDTO> converToArticleSearchDTOList(List<ArticleSearch> articleSearchList);

    @Mapping(target = "tagNames", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "articleAbstract", ignore = true)
    ArticleVO converToArticleVO(ArticleBO resBO);

    List<UserInfoVO> converToUserInfoVOList(List<UserInfoBO> resBOList);
}
