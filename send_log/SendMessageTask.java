package nju.edu.cn.log.log_tracking.send_log;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

/**
 * 发送消息的任务
 */
class SendMessageTask implements Runnable{

    private Producer<String,String> producer;

    private KeyedMessage<String,String> keyedMessage;

    public SendMessageTask(Producer<String, String> producer, KeyedMessage<String, String> keyedMessage) {
        this.producer = producer;
        this.keyedMessage = keyedMessage;
    }

    @Override
    public void run() {
//        producer.send(keyedMessage);
        System.out.println(keyedMessage.message());
    }
}
