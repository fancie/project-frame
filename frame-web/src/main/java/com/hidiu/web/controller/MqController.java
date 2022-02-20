package com.hidiu.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author fancie
 * @title: MQProducerController
 * @projectName project-frame
 * @description: TODO
 * @date 2022/2/20 下午10:13
 */
@Slf4j
@Controller
public class MqController {

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    /**
     * 发送简单的MQ消息
     * @return
     */
    @ResponseBody
    @RequestMapping("/mq/send")
    public String send() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        log.info("发送MQ消息内容：");
        Message sendMsg = new Message("TestTopic", "TestTag", "msg".getBytes());
        // 默认3秒超时
        SendResult sendResult = defaultMQProducer.send(sendMsg);
        log.info("消息发送响应：" + sendResult.toString());
        return "SUCCESS";
    }

}
