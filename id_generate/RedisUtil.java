package nju.edu.cn.log.log_tracking.id_generate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * Created by cong on 2018-01-31.
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    /**
     * 执行缓存在redis的脚本
     * @param sha
     * @param params
     * @return
     */
    public Object evalsha(String sha,int length,String... params){

        Object result = redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) {
                Jedis jedis = (Jedis) connection.getNativeConnection();

                return jedis.evalsha(sha, length,params);

            }
        }, true);
        return result;
    }

}
