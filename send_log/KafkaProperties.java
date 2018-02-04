package nju.edu.cn.log.log_tracking.send_log;


import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;
import nju.edu.cn.log.log_tracking.log_collect.AccessLogVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Created by cong on 2018-01-22.
 */
public class KafkaProperties {

    private static final String zookeeperHost="120.79.201.3:4181,39.107.107.237:4181,120.79.201.107:4181";

    private static final String kafkaHost="120.79.9.49:9092";


    public static Properties buildProperty(){
        Properties props = new Properties();
        props.put("zookeeper.connect", zookeeperHost);//声明zk
        props.put("serializer.class", StringEncoder.class.getName());
        props.put("metadata.broker.list", kafkaHost);// 声明kafka broker

        /**
         * 内部发送数据是异步还是同步
         * sync：同步, 默认
         * async：异步
         */
        props.put("producer.type", "async");

        // 重试次数
        props.put("message.send.max.retries", "3");

        // 异步提交的时候(async)，并发提交的记录数
        props.put("batch.num.messages", "1000");

        //压缩
        props.put("compression.codec","snappy");

        // 设置缓冲区大小，2000KB
        props.put("send.buffer.bytes", "20480000");
        return props;
    }

}
