package com.blog.strategy.context;

import com.api.dto.user.UserInfoDTO;
import com.api.enums.LoginTypeEnum;
import com.blog.strategy.SocialLoginStrategy;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author xiongke
 */
@Service
public class SocialLoginStrategyContext {

    @Resource
    private Map<String, SocialLoginStrategy> socialLoginStrategyMap;

    public UserInfoDTO executeLoginStrategy(String data, LoginTypeEnum loginTypeEnum) {
        return socialLoginStrategyMap.get(loginTypeEnum.getStrategy()).login(data);
    }

}
