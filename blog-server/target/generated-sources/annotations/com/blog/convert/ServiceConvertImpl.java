package com.blog.convert;

import com.blog.modle.bo.ArticleBO;
import com.blog.modle.bo.UserAuthBO;
import com.blog.modle.bo.UserInfoBO;
import com.core.modle.bo.JobBO;
import com.core.modle.entity.Article;
import com.core.modle.entity.Job;
import com.core.modle.entity.UserAuth;
import com.core.modle.entity.UserInfo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-08T17:29:57+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
public class ServiceConvertImpl implements ServiceConvert {

    @Override
    public UserAuth converToUserAuth(UserAuthBO userAuthBO) {
        if ( userAuthBO == null ) {
            return null;
        }

        UserAuth.UserAuthBuilder userAuth = UserAuth.builder();

        userAuth.id( userAuthBO.getId() );
        userAuth.userInfoId( userAuthBO.getUserInfoId() );
        userAuth.username( userAuthBO.getUsername() );
        userAuth.password( userAuthBO.getPassword() );
        userAuth.loginType( userAuthBO.getLoginType() );
        userAuth.ipAddress( userAuthBO.getIpAddress() );
        userAuth.ipSource( userAuthBO.getIpSource() );
        userAuth.createTime( userAuthBO.getCreateTime() );
        userAuth.updateTime( userAuthBO.getUpdateTime() );
        userAuth.lastLoginTime( userAuthBO.getLastLoginTime() );

        return userAuth.build();
    }

    @Override
    public ArticleBO convertArticleBO(Article article) {
        if ( article == null ) {
            return null;
        }

        ArticleBO articleBO = new ArticleBO();

        articleBO.setId( article.getId() );
        articleBO.setArticleCover( article.getArticleCover() );
        articleBO.setArticleTitle( article.getArticleTitle() );
        articleBO.setArticleContent( article.getArticleContent() );
        articleBO.setIsTop( article.getIsTop() );
        articleBO.setIsFeatured( article.getIsFeatured() );
        articleBO.setIsDelete( article.getIsDelete() );
        articleBO.setStatus( article.getStatus() );
        articleBO.setType( article.getType() );
        articleBO.setOriginalUrl( article.getOriginalUrl() );
        articleBO.setCreateTime( article.getCreateTime() );
        articleBO.setUpdateTime( article.getUpdateTime() );

        return articleBO;
    }

    @Override
    public UserInfoBO convert(UserInfo userInfo) {
        if ( userInfo == null ) {
            return null;
        }

        UserInfoBO userInfoBO = new UserInfoBO();

        userInfoBO.setNickname( userInfo.getNickname() );
        userInfoBO.setIntro( userInfo.getIntro() );
        userInfoBO.setWebsite( userInfo.getWebsite() );
        userInfoBO.setEmail( userInfo.getEmail() );

        return userInfoBO;
    }

    @Override
    public List<UserInfoBO> converUserInfoBOList(List<UserInfo> list) {
        if ( list == null ) {
            return null;
        }

        List<UserInfoBO> list1 = new ArrayList<UserInfoBO>( list.size() );
        for ( UserInfo userInfo : list ) {
            list1.add( convert( userInfo ) );
        }

        return list1;
    }

    @Override
    public JobBO convert(Job job) {
        if ( job == null ) {
            return null;
        }

        JobBO jobBO = new JobBO();

        jobBO.setId( job.getId() );
        jobBO.setJobName( job.getJobName() );
        jobBO.setJobGroup( job.getJobGroup() );
        jobBO.setInvokeTarget( job.getInvokeTarget() );
        jobBO.setCronExpression( job.getCronExpression() );
        jobBO.setMisfirePolicy( job.getMisfirePolicy() );
        jobBO.setConcurrent( job.getConcurrent() );
        jobBO.setStatus( job.getStatus() );
        jobBO.setCreateTime( job.getCreateTime() );
        jobBO.setUpdateTime( job.getUpdateTime() );
        jobBO.setRemark( job.getRemark() );
        jobBO.setNextValidTime( job.getNextValidTime() );

        return jobBO;
    }

    @Override
    public List<JobBO> converToJobBOList(List<Job> jobs) {
        if ( jobs == null ) {
            return null;
        }

        List<JobBO> list = new ArrayList<JobBO>( jobs.size() );
        for ( Job job : jobs ) {
            list.add( convert( job ) );
        }

        return list;
    }
}
