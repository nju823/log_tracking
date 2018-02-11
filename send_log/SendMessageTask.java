package nju.edu.cn.log.log_tracking.send_log;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerPool;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 发送消息的任务
 */
class SendMessageTask implements Runnable{

    private KafkaProducerPool producerPool;

    private KeyedMessage<String,String> keyedMessage;

    public SendMessageTask(KeyedMessage<String, String> keyedMessage, KafkaProducerPool producerPool) {
        this.keyedMessage = keyedMessage;
        this.producerPool=producerPool;
    }

    @Override
    public void run() {
        producerPool.send(keyedMessage);
//        System.out.println(keyedMessage.message());
    }
}
