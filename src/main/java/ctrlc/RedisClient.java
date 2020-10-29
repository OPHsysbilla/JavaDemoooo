package ctrlc;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import static ctrlc.InsertClient.*;

/**
 * @Author: wg
 * @Date: 2020/10/23 2:52 下午
 */
public class RedisClient {

    public static JedisPool getPool() {
        return pool;
    }

    public static JedisPool getPool2() {
        return pool2;
    }

    private static  JedisPool pool = null;

    private static  JedisPool pool2 = null;

    private static  JedisPool pool3 = null;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(500);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMaxWaitMillis(100000000);
        pool = new JedisPool(jedisPoolConfig,HOST , PORT);

        JedisPoolConfig jedisPoolConfig2 = new JedisPoolConfig();
        jedisPoolConfig2.setMaxTotal(writeThreadNum);
        jedisPoolConfig2.setMaxIdle(10);
        jedisPoolConfig2.setMaxWaitMillis(10000);
        pool2 = new JedisPool(jedisPoolConfig2,HOST , PORT);

        JedisPoolConfig jedisPoolConfig3 = new JedisPoolConfig();
        jedisPoolConfig3.setMaxTotal(1000);
        jedisPoolConfig3.setMaxIdle(10);
        jedisPoolConfig3.setMaxWaitMillis(10000);
        pool3 = new JedisPool(jedisPoolConfig3,"hackcup-game01-redis-server.xxx.com" , 1000);

    }

    public static JedisPool getPool3() {
        return pool3;
    }



}

