package com.blog.strategy.impl;

import com.alibaba.fastjson.JSON;
import com.api.constant.SocialLoginConstant;
import com.api.dto.qq.QQTokenDTO;
import com.api.dto.qq.QQUserInfoDTO;
import com.api.dto.social.SocialTokenDTO;
import com.api.dto.social.SocialUserInfoDTO;
import com.api.enums.LoginTypeEnum;
import com.api.vo.other.QQLoginVO;
import com.core.config.properties.QQConfigProperties;
import com.core.exception.BizException;
import com.core.util.CommonUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.api.constant.SocialLoginConstant.*;
import static com.api.enums.StatusCodeEnum.QQ_LOGIN_ERROR;


/**
 * @author xiongke
 */
@Service("qqLoginStrategyImpl")
public class QQLoginStrategyImpl extends AbstractSocialLoginStrategyImpl {

    private final Logger logger = LoggerFactory.getLogger(QQLoginStrategyImpl.class);

    @Resource
    private QQConfigProperties qqConfigProperties;

    @Resource
    private RestTemplate restTemplate;

    @Override
    public SocialTokenDTO getSocialToken(String data) {
        QQLoginVO qqLoginVO = JSON.parseObject(data, QQLoginVO.class);
        checkQqToken(qqLoginVO);
        return SocialTokenDTO.builder()
                .openId(qqLoginVO.getOpenId())
                .accessToken(qqLoginVO.getAccessToken())
                .loginType(LoginTypeEnum.QQ.getType())
                .build();
    }

    @Override
    public SocialUserInfoDTO getSocialUserInfo(SocialTokenDTO socialTokenDTO) {
        Map<String, String> formData = new HashMap<>(3);
        formData.put(QQ_OPEN_ID, socialTokenDTO.getOpenId());
        formData.put(ACCESS_TOKEN, socialTokenDTO.getAccessToken());
        formData.put(OAUTH_CONSUMER_KEY, qqConfigProperties.getAppId());
        QQUserInfoDTO qqUserInfoDTO = JSON.parseObject(restTemplate.getForObject(qqConfigProperties.getUserInfoUrl(), String.class, formData), QQUserInfoDTO.class);
        return SocialUserInfoDTO.builder()
                .nickname(Objects.requireNonNull(qqUserInfoDTO).getNickName())
                .avatar(qqUserInfoDTO.getFigureUrlQq())
                .build();
    }

    private void checkQqToken(QQLoginVO qqLoginVO) {
        Map<String, String> qqData = new HashMap<>(1);
        qqData.put(SocialLoginConstant.ACCESS_TOKEN, qqLoginVO.getAccessToken());
        try {
            String result = restTemplate.getForObject(qqConfigProperties.getCheckTokenUrl(), String.class, qqData);
            QQTokenDTO qqTokenDTO = JSON.parseObject(CommonUtil.getBracketsContent(Objects.requireNonNull(result)), QQTokenDTO.class);
            if (!qqLoginVO.getOpenId().equals(qqTokenDTO.getOpenid())) {
                throw new BizException(QQ_LOGIN_ERROR);
            }
        } catch (Exception e) {
            logger.error("QQtoken异常,{}", e.getMessage());
            throw new BizException(QQ_LOGIN_ERROR);
        }
    }

}
