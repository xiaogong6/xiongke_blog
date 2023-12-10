package com.blog.convert;

import com.api.dto.other.ArticleSearchDTO;
import com.api.vo.article.ArticleVO;
import com.api.vo.user.UserAuthVO;
import com.api.vo.user.UserInfoVO;
import com.blog.modle.bo.ArticleBO;
import com.blog.modle.bo.UserAuthBO;
import com.blog.modle.bo.UserInfoBO;
import com.core.modle.es.ArticleSearch;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-10T19:57:08+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
public class CommonConvertImpl implements CommonConvert {

    @Override
    public UserAuthBO converToUserAuthBO(UserAuthVO userAuthVO) {
        if ( userAuthVO == null ) {
            return null;
        }

        UserAuthBO userAuthBO = new UserAuthBO();

        userAuthBO.setId( userAuthVO.getId() );
        userAuthBO.setUserInfoId( userAuthVO.getUserInfoId() );
        userAuthBO.setUsername( userAuthVO.getUsername() );
        userAuthBO.setPassword( userAuthVO.getPassword() );
        userAuthBO.setLoginType( userAuthVO.getLoginType() );
        userAuthBO.setIpAddress( userAuthVO.getIpAddress() );
        userAuthBO.setIpSource( userAuthVO.getIpSource() );
        userAuthBO.setCreateTime( userAuthVO.getCreateTime() );
        userAuthBO.setUpdateTime( userAuthVO.getUpdateTime() );
        userAuthBO.setLastLoginTime( userAuthVO.getLastLoginTime() );

        return userAuthBO;
    }

    @Override
    public List<ArticleSearchDTO> converToArticleSearchDTOList(List<ArticleSearch> articleSearchList) {
        if ( articleSearchList == null ) {
            return null;
        }

        List<ArticleSearchDTO> list = new ArrayList<ArticleSearchDTO>( articleSearchList.size() );
        for ( ArticleSearch articleSearch : articleSearchList ) {
            list.add( articleSearchToArticleSearchDTO( articleSearch ) );
        }

        return list;
    }

    @Override
    public ArticleVO converToArticleVO(ArticleBO resBO) {
        if ( resBO == null ) {
            return null;
        }

        ArticleVO.ArticleVOBuilder articleVO = ArticleVO.builder();

        articleVO.id( resBO.getId() );
        articleVO.articleTitle( resBO.getArticleTitle() );
        articleVO.articleContent( resBO.getArticleContent() );
        articleVO.articleCover( resBO.getArticleCover() );
        articleVO.categoryName( resBO.getCategoryName() );
        articleVO.isTop( resBO.getIsTop() );
        articleVO.isFeatured( resBO.getIsFeatured() );
        articleVO.status( resBO.getStatus() );
        articleVO.type( resBO.getType() );
        articleVO.originalUrl( resBO.getOriginalUrl() );
        articleVO.updateTime( resBO.getUpdateTime() );

        return articleVO.build();
    }

    @Override
    public List<UserInfoVO> converToUserInfoVOList(List<UserInfoBO> resBOList) {
        if ( resBOList == null ) {
            return null;
        }

        List<UserInfoVO> list = new ArrayList<UserInfoVO>( resBOList.size() );
        for ( UserInfoBO userInfoBO : resBOList ) {
            list.add( userInfoBOToUserInfoVO( userInfoBO ) );
        }

        return list;
    }

    protected ArticleSearchDTO articleSearchToArticleSearchDTO(ArticleSearch articleSearch) {
        if ( articleSearch == null ) {
            return null;
        }

        ArticleSearchDTO.ArticleSearchDTOBuilder articleSearchDTO = ArticleSearchDTO.builder();

        articleSearchDTO.id( articleSearch.getId() );
        articleSearchDTO.articleTitle( articleSearch.getArticleTitle() );
        articleSearchDTO.articleContent( articleSearch.getArticleContent() );
        articleSearchDTO.isDelete( articleSearch.getIsDelete() );
        articleSearchDTO.status( articleSearch.getStatus() );

        return articleSearchDTO.build();
    }

    protected UserInfoVO userInfoBOToUserInfoVO(UserInfoBO userInfoBO) {
        if ( userInfoBO == null ) {
            return null;
        }

        UserInfoVO.UserInfoVOBuilder userInfoVO = UserInfoVO.builder();

        userInfoVO.nickname( userInfoBO.getNickname() );
        userInfoVO.intro( userInfoBO.getIntro() );
        userInfoVO.website( userInfoBO.getWebsite() );
        userInfoVO.email( userInfoBO.getEmail() );

        return userInfoVO.build();
    }
}
