package com.blog.service.impl;

import com.api.dto.user.UserDetailsDTO;
import com.blog.service.RedisService;
import com.blog.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import static com.api.constant.AuthConstant.*;
import static com.api.constant.RedisConstant.LOGIN_USER;

/**
 * @Program: my_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private RedisService redisService;

    @Override
    public UserDetailsDTO getUserDetailInfo(HttpServletRequest request) {
        String token = Optional.ofNullable(request.getHeader(TOKEN_HEADER)).orElse("").replaceFirst(TOKEN_PREFIX, "");
        if (StringUtils.hasText(token) && !"null".equals(token)) {
            Claims claims = parseToken(token);
            String userId = claims.getSubject();
            return (UserDetailsDTO) redisService.hGet(LOGIN_USER, userId);
        }
        return null;
    }

    @Override
    public void renewToken(UserDetailsDTO userDetailsDTO) {
        LocalDateTime expireTime = userDetailsDTO.getExpireTime();
        LocalDateTime currentTime = LocalDateTime.now();
        if (Duration.between(currentTime, expireTime).toMinutes() <= TWENTY_MINUTES) {
            refreshToken(userDetailsDTO);
        }
    }

    @Override
    public String createToken(UserDetailsDTO userDetailsDTO) {
        refreshToken(userDetailsDTO);
        String userId = userDetailsDTO.getId().toString();
        return createToken(userId);
    }

    public String createToken(String subject) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        return Jwts.builder().setId(getUuid()).setSubject(subject)
                .setIssuer(SECRET)
                .signWith(signatureAlgorithm, secretKey).compact();
    }

    private String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public void refreshToken(UserDetailsDTO userDetailsDTO) {
        LocalDateTime currentTime = LocalDateTime.now();
        userDetailsDTO.setExpireTime(currentTime.plusSeconds(EXPIRE_TIME));
        String userId = userDetailsDTO.getId().toString();
        redisService.hSet(LOGIN_USER, userId, userDetailsDTO, EXPIRE_TIME);
    }

    private Claims parseToken(String token) {
        SecretKey secretKey = generalKey();
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(SECRET);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }
}
