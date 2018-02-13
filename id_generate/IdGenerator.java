package nju.edu.cn.log.log_tracking.id_generate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

@Component
public class IdGenerator {

	@Value("${log.redis.evalsha}")
	private String evalsha;

	@Value("${log.redis.retry.time}")
	private int retryTimes;

	@Autowired
	private RedisUtil redisUtil;


	public long next(String tab) {
		return next(tab, 0);
	}

	public long next(String tab, long shardId) {
		for (int i = 0; i < retryTimes; ++i) {
			Long id = innerNext(tab, shardId);
			if (id != null) {
				return id;
			}
		}
		throw new RuntimeException("Can not generate log_select!");
	}

	Long innerNext(String tab, long shardId) {
		List<Long> result = (List<Long>) redisUtil.evalsha(evalsha, 2, tab, "" + shardId);
		long id = buildId(result.get(0), result.get(1), result.get(2),
				result.get(3));
		return id;
	}

	public static long buildId(long second, long microSecond, long shardId,
			long seq) {
		long miliSecond = (second * 1000 + microSecond / 1000);
		return (miliSecond << (12 + 10)) + (shardId << 10) + seq;
	}

	public static List<Long> parseId(long id) {
		long miliSecond = id >>> 22;
		// 2 ^ 12 = 0xFFF
		long shardId = (id & (0xFFF << 10)) >> 10;
		long seq = id & 0x3FF;

		List<Long> re = new ArrayList<Long>(4);
		re.add(miliSecond);
		re.add(shardId);
		re.add(seq);
		return re;
	}
}
