package com.rabbitmq.consumer;

import com.alibaba.fastjson.JSON;
import com.core.modle.bo.EmailBO;
import com.core.util.EmailUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.api.constant.RabbitMQConstant.EMAIL_QUEUE;


/**
 * @author xiongke
 */
@Component
@RabbitListener(queues = EMAIL_QUEUE)
public class CommentNoticeConsumer {

    @Autowired
    private EmailUtil emailUtil;

    @RabbitHandler
    public void process(byte[] data) {
        EmailBO emailBO = JSON.parseObject(new String(data), EmailBO.class);
        emailUtil.sendHtmlMail(emailBO);
    }

}
