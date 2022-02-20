package com.hidiu.mq.config;

import com.hidiu.mq.listener.MQListenerProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fancie
 * @title: RocketMQConfigure
 * @projectName project-frame
 * @description: TODO
 * @date 2022/2/20 下午10:32
 */
@Slf4j
@Configuration
public class RocketMQConfigure {

    @Value("${rocketmq.consumer.groupName}")
    private String groupName;
    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.topics}")
    private String topics;
    // 消费者线程数据量
    @Value("${rocketmq.consumer.consumeThreadMin}")
    private Integer consumeThreadMin;
    @Value("${rocketmq.consumer.consumeThreadMax}")
    private Integer consumeThreadMax;
    @Value("${rocketmq.consumer.consumeMessageBatchMaxSize}")
    private Integer consumeMessageBatchMaxSize;

    @Autowired
    private MQListenerProcessor mqListenerProcessor;
    /**
     * mq 消费者配置
     * @return
     * @throws MQClientException
     */
    @Bean
    public DefaultMQPushConsumer defaultConsumer() throws MQClientException {
        log.info("defaultConsumer 正在创建---------------------------------------");
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
        // 设置监听
        consumer.registerMessageListener(mqListenerProcessor);

        /**
         * 设置consumer第一次启动是从队列头部开始还是队列尾部开始
         * 如果不是第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        /**
         * 设置消费模型，集群还是广播，默认为集群
         */
        try {
            // 设置该消费者订阅的主题和tag，如果订阅该主题下的所有tag，则使用*,
            String[] topicArr = topics.split(";");
            for (String topic : topicArr) {
                String[] tagArr = topic.split("~");
                consumer.subscribe(tagArr[0], tagArr[1]);
            }
            consumer.start();
            log.info("consumer 创建成功 groupName={}, topics={}, namesrvAddr={}",groupName,topics,namesrvAddr);
        } catch (MQClientException e) {
            log.error("consumer 创建失败!");
        }
        return consumer;
    }

}
