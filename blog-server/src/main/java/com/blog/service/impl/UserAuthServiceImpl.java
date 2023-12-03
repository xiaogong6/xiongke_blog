package com.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.api.constant.CommonConstant;
import com.api.dto.base.PageResultDTO;
import com.api.dto.other.EmailDTO;
import com.api.dto.user.UserAdminDTO;
import com.api.dto.user.UserAreaDTO;
import com.api.dto.user.UserInfoDTO;
import com.api.dto.user.UserLogoutStatusDTO;
import com.api.enums.LoginTypeEnum;
import com.api.enums.RoleEnum;
import com.api.vo.other.ConditionVO;
import com.api.vo.other.PasswordVO;
import com.api.vo.other.QQLoginVO;
import com.api.vo.user.UserVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.blog.convert.ServiceConvert;
import com.blog.modle.bo.UserAuthBO;
import com.blog.repo.UserAuthRepo;
import com.blog.service.AuroraInfoService;
import com.blog.service.RedisService;
import com.blog.service.TokenService;
import com.blog.service.UserAuthService;
import com.blog.strategy.context.SocialLoginStrategyContext;
import com.core.exception.BizException;
import com.core.mapper.UserAuthMapper;
import com.core.mapper.UserInfoMapper;
import com.core.mapper.UserRoleMapper;
import com.core.modle.entity.UserAuth;
import com.core.modle.entity.UserInfo;
import com.core.modle.entity.UserRole;
import com.core.util.PageUtil;
import com.core.util.UserUtil;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.api.constant.RabbitMQConstant.EMAIL_EXCHANGE;
import static com.api.constant.RedisConstant.*;
import static com.api.enums.UserAreaTypeEnum.getUserAreaType;
import static com.core.util.CommonUtil.checkEmail;
import static com.core.util.CommonUtil.getRandomCode;

/**
 * @Program: my_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Resource
    private UserAuthMapper userAuthMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RedisService redisService;

    @Resource
    private AuroraInfoService auroraInfoService;

    @Resource
    private TokenService tokenService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private SocialLoginStrategyContext socialLoginStrategyContext;

    @Resource
    private UserAuthRepo userAuthRepo;

    @Override
    public void updateById(UserAuthBO userAuthBO) {
        UserAuth userAuth = ServiceConvert.INSTANCE.converToUserAuth(userAuthBO);
        userAuthRepo.updateById(userAuth);
    }

    @Override
    public void sendCode(String username) {
        if (!checkEmail(username)) {
            throw new BizException("请输入正确邮箱");
        }
        String code = getRandomCode();
        Map<String, Object> map = new HashMap<>();
        map.put("content", "您的验证码为 " + code + " 有效期15分钟，请不要告诉他人哦！");
        EmailDTO emailDTO = EmailDTO.builder()
                .email(username)
                .subject(CommonConstant.CAPTCHA)
                .template("common.html")
                .commentMap(map)
                .build();
        rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, "*", new Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));
        redisService.set(USER_CODE_KEY + username, code, CODE_EXPIRE_TIME);
    }

    @Override
    public List<UserAreaDTO> listUserAreas(ConditionVO conditionVO) {
        List<UserAreaDTO> userAreaDTOs = new ArrayList<>();
        switch (Objects.requireNonNull(getUserAreaType(conditionVO.getType()))) {
            case USER:
                Object userArea = redisService.get(USER_AREA);
                if (Objects.nonNull(userArea)) {
                    userAreaDTOs = JSON.parseObject(userArea.toString(), List.class);
                }
                return userAreaDTOs;
            case VISITOR:
                Map<String, Object> visitorArea = redisService.hGetAll(VISITOR_AREA);
                if (Objects.nonNull(visitorArea)) {
                    userAreaDTOs = visitorArea.entrySet().stream()
                            .map(item -> UserAreaDTO.builder()
                                    .name(item.getKey())
                                    .value(Long.valueOf(item.getValue().toString()))
                                    .build())
                            .collect(Collectors.toList());
                }
                return userAreaDTOs;
            default:
                break;
        }
        return userAreaDTOs;
    }

    @Override
    public PageResultDTO<UserAdminDTO> listUsers(ConditionVO conditionVO) {
        Integer count = userAuthMapper.countUser(conditionVO);
        if (count == 0) {
            return new PageResultDTO<>();
        }
        List<UserAdminDTO> list = userAuthMapper.listUsers(PageUtil.getLimitCurrent(), PageUtil.getSize(), conditionVO);
        return new PageResultDTO<>(list, count);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserVO userVO) {
        if (!checkEmail(userVO.getUsername())) {
            throw new BizException("邮箱格式不对!");
        }
        if (checkUser(userVO)) {
            throw new BizException("邮箱已被注册！");
        }
        UserInfo userInfo = UserInfo.builder()
                .email(userVO.getUsername())
                .nickname(CommonConstant.DEFAULT_NICKNAME + IdWorker.getId())
                .avatar(auroraInfoService.getWebsiteConfig().getUserAvatar())
                .build();
        userInfoMapper.insert(userInfo);
        UserRole userRole = UserRole.builder()
                .userId(userInfo.getId())
                .roleId(RoleEnum.USER.getRoleId())
                .build();
        userRoleMapper.insert(userRole);
        UserAuth userAuth = UserAuth.builder()
                .userInfoId(userInfo.getId())
                .username(userVO.getUsername())
                .password(BCrypt.hashpw(userVO.getPassword(), BCrypt.gensalt()))
                .loginType(LoginTypeEnum.EMAIL.getType())
                .build();
        userAuthMapper.insert(userAuth);
    }

    @Override
    public void updatePassword(UserVO user) {
        if (!checkUser(user)) {
            throw new BizException("邮箱尚未注册！");
        }
        userAuthMapper.update(new UserAuth(), new LambdaUpdateWrapper<UserAuth>()
                .set(UserAuth::getPassword, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()))
                .eq(UserAuth::getUsername, user.getUsername()));
    }

    @Override
    public void updateAdminPassword(PasswordVO passwordVO) {
        UserAuth user = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
                .eq(UserAuth::getId, UserUtil.getUserDetailsDTO().getId()));
        if (Objects.nonNull(user) && BCrypt.checkpw(passwordVO.getOldPassword(), user.getPassword())) {
            UserAuth userAuth = UserAuth.builder()
                    .id(UserUtil.getUserDetailsDTO().getId())
                    .password(BCrypt.hashpw(passwordVO.getNewPassword(), BCrypt.gensalt()))
                    .build();
            userAuthMapper.updateById(userAuth);
        } else {
            throw new BizException("旧密码不正确");
        }
    }

    @Override
    public UserLogoutStatusDTO logout() {
        tokenService.delLoginUser(UserUtil.getUserDetailsDTO().getId());
        return new UserLogoutStatusDTO("注销成功");
    }

    @Override
    public UserInfoDTO qqLogin(QQLoginVO qqLoginVO) {
        return socialLoginStrategyContext.executeLoginStrategy(JSON.toJSONString(qqLoginVO), LoginTypeEnum.QQ);
    }

    private Boolean checkUser(UserVO user) {
        if (!user.getCode().equals(redisService.get(USER_CODE_KEY + user.getUsername()))) {
            throw new BizException("验证码错误！");
        }
        UserAuth userAuth = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
                .select(UserAuth::getUsername)
                .eq(UserAuth::getUsername, user.getUsername()));
        return Objects.nonNull(userAuth);
    }
}
