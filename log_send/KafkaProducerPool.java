package nju.edu.cn.log.log_tracking.log_send;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static nju.edu.cn.log.log_tracking.log_send.KafkaProperties.buildProperty;

/**
 * kafka线程池
 */
@Component
public class KafkaProducerPool{

    @Value("${log.kafka.thread.num}")
    private int threadNum;

    private int index = 0; // 轮循id

    private Producer<String,String> producers[];

    @PostConstruct
    public void initProducerPool() {
        producers = new Producer[threadNum];
        for (int i = 0; i < threadNum; i++) {  
            producers[i]=new Producer<>(new ProducerConfig(buildProperty()));
        }  
    }  
  
    public void send(KeyedMessage<String,String> message) {
        producers[index++ % threadNum].send(message);
    }  
}  