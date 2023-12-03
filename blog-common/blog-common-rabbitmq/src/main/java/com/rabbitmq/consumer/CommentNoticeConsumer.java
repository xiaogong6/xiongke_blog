package com.rabbitmq.consumer;

import com.alibaba.fastjson.JSON;
import com.core.modle.bo.EmailBO;
import com.core.util.EmailUtil;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.api.constant.RabbitMQConstant.EMAIL_QUEUE;


/**
 * @author xiongke
 */
@Component
@RabbitListener(queues = EMAIL_QUEUE)
public class CommentNoticeConsumer {

    @Resource
    private EmailUtil emailUtil;

    @RabbitHandler
    public void process(byte[] data) {
        EmailBO emailBO = JSON.parseObject(new String(data), EmailBO.class);
        emailUtil.sendHtmlMail(emailBO);
    }

}
