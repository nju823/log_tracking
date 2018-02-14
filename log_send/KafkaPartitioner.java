package nju.edu.cn.log.log_tracking.log_send;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;
import org.apache.log4j.Logger;


/**
 * Created by cong on 2018-02-14.
 */
public class KafkaPartitioner implements Partitioner{

    private static final Logger LOGGER=Logger.getLogger(KafkaPartitioner.class);

    public KafkaPartitioner(VerifiableProperties properties){

    }

    @Override
    public int partition(Object key, int numPartitions) {
        int partiton=0;
        try{
            Long traceId=Long.parseLong((String) key);
            partiton=(int)(traceId%numPartitions);
        }catch (Exception e){
            LOGGER.warn(e);
        }
        return partiton;
    }
}
