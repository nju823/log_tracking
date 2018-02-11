package nju.edu.cn.log.log_tracking.id_generate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by cong on 2018-02-11.
 * 生成递增的long型整数id
 */
@Component
public class NumberIdGenerator {

    @Value("${log.redis.retry.time}")
    private int retryTimes;

    @Autowired
    private RedisUtil redisUtil;

    public Long next(String idKey){
        for (int i = 0; i < retryTimes; ++i) {
            Long id = redisUtil.increment(idKey);
            if (id != null) {
                return id;
            }
        }
        throw new RuntimeException("Can not generate id!");
    }

}
