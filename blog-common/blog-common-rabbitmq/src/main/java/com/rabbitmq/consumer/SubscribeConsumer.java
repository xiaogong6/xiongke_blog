package com.rabbitmq.consumer;


import com.alibaba.fastjson.JSON;
import com.api.dto.article.ArticleDTO;
import com.api.feign.ArticleClient;
import com.api.feign.UserInfoClient;
import com.api.vo.base.ResultVO;
import com.api.vo.user.UserInfoVO;
import com.core.modle.bo.EmailBO;
import com.core.modle.entity.Article;
import com.core.util.EmailUtil;
import com.rabbitmq.convert.RabbitMqConvert;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.api.constant.RabbitMQConstant.SUBSCRIBE_QUEUE;


/**
 * @author xiongke
 */
@Component
@RabbitListener(queues = SUBSCRIBE_QUEUE)
public class SubscribeConsumer {

    private final Logger logger = LoggerFactory.getLogger(SubscribeConsumer.class);

    @Value("${website.url}")
    private String websiteUrl;

    @Resource
    private EmailUtil emailUtil;

    @Resource
    private ArticleClient articleClient;

    @Resource
    private UserInfoClient userInfoClient;

    @RabbitHandler
    public void process(byte[] data) {
        Integer articleId = JSON.parseObject(new String(data), Integer.class);
        ResultVO<ArticleDTO> serverResponse = articleClient.getArticleById(articleId);
        if (!serverResponse.getFlag()) {
            logger.error("查询文章信息失败,入参:{}", JSON.toJSONString(articleId));
            return;
        }
        Article article = RabbitMqConvert.INSTANCE.converToArticle(serverResponse.getData());
        ResultVO<List<UserInfoVO>> responseEntity = userInfoClient.getUserInfoSubscribeList();
        if (!responseEntity.getFlag()) {
            logger.error("查询订阅用户信息相应:{}", JSON.toJSONString(responseEntity));
            return;
        }
        List<UserInfoVO> entityData = responseEntity.getData();
        List<String> emails = entityData.stream().map(UserInfoVO::getEmail).toList();
        for (String email : emails) {
            EmailBO emailBO = getEmailBO(email, articleId, article);
            emailUtil.sendHtmlMail(emailBO);
        }
    }

    @NotNull
    private EmailBO getEmailBO(String email, Integer articleId, Article article) {
        EmailBO emailBO = new EmailBO();
        Map<String, Object> map = new HashMap<>();
        emailBO.setEmail(email);
        emailBO.setSubject("文章订阅");
        emailBO.setTemplate("common.html");
        String url = websiteUrl + "/articles/" + articleId;
        if (article.getUpdateTime() == null) {
            map.put("content", "城北花开的个人博客发布了新的文章，"
                    + "<a style=\"text-decoration:none;color:#12addb\" href=\"" + url + "\">点击查看</a>");
        } else {
            map.put("content", "城北花开的个人博客对《" + article.getArticleTitle() + "》进行了更新，"
                    + "<a style=\"text-decoration:none;color:#12addb\" href=\"" + url + "\">点击查看</a>");
        }
        emailBO.setCommentMap(map);
        return emailBO;
    }

}
