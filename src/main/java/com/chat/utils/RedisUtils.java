package com.chat.utils;

import java.util.Collections;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;

public class RedisUtils {
	
	private static final String LOCK_SUCCCESS = "OK";
	private static final String SET_IF_NOT_EXIT = "NX";
	private static final String SET_WITH_EXPIRE_TIME = "PX";
	private static final Long RELEASE_SUCCESS = 1L;
	private static String REDIS_HOST = "127.0.0.1";
	private static int REDIS_PORT = 0;
	
	static {
		Properties loadProperties = FileLoadUtil.loadProperties("redis.properties");
		REDIS_HOST = loadProperties.getProperty("redis.host");
		String property = loadProperties.getProperty("redis.port");
		REDIS_PORT = Integer.parseInt(property);
	}
	
	public static Jedis getJedisInstance() {
		return new Jedis(REDIS_HOST, REDIS_PORT);
	}
	
	/**
	 * Redis锁
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 */
	public static Boolean setnx(String key,String value,Long seconds) {
		Jedis jedis = RedisUtils.getJedisInstance();
		String set = jedis.set(key, value, SET_IF_NOT_EXIT, SET_WITH_EXPIRE_TIME, seconds);
		if(StringUtils.equals(LOCK_SUCCCESS, set)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Redis解锁
	 * @param key
	 * @param value
	 * @return
	 */
	public static Boolean unLock(String key,String value) {
		Jedis jedis = RedisUtils.getJedisInstance();
		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
		Object eval = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(value));
		if(RELEASE_SUCCESS.equals(eval)) {
			return true;
		}
		return false;
	}

}
