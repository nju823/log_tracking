package nju.edu.cn.log.log_tracking.send_log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSONObject;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import nju.edu.cn.log.log_tracking.log_collect.AccessLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogkafkaProducer{

    /**
     * kafka topic
     */
    private static final String ACCESS_LOG_TOPIC="access_log_topic";

    /**
     * 推送消息线程池
     */
    private static final Executor KAFKA_THREAD_POOL= Executors.newFixedThreadPool(20);

    @Autowired
    private KafkaProducerPool kafkaProducerPool;


    /**
     * 将访问日志推送到kafka
     * @param accessLog
     */
    public void send(AccessLogVO accessLog){
        KeyedMessage<String,String> keyedMessage=
                new KeyedMessage<>(ACCESS_LOG_TOPIC,accessLog.getTraceId()+"", JSONObject.toJSONString(accessLog));
        KAFKA_THREAD_POOL.execute(new SendMessageTask(keyedMessage,kafkaProducerPool));
    }

         
}

