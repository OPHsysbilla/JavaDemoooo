package ctrlc;

import ctrlc.del.DelService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

/**
 * @author yin.jianfeng
 * @date 2020/10/23
 */
public class InsertClient {

    public static final String HOST = "hackcup-game01-redis-server.xxx.com";

    public static final int PORT = 6379;

    public static final int NETTY_THREAD_NUM = 1;

    public static final int CHANNEL_NUM = 1;

    private static byte[] SET = "hset group-10 ".getBytes();

    private static String name = "group-10";

    private static byte[] VAL = new byte[] {32, 49, 10};

    private static int PACKAGE_NUM = 1024;

    public static int writeThreadNum = 1000;

    public static void main(String[] args) {
        RedisClient.getPool();
        DelService.start();


        JedisPool jedisPool = RedisClient.getPool2();

        for (int i = 0; i < writeThreadNum; i++) {
            new Thread(
                    () -> {
                        int num = Integer.MIN_VALUE;
                        while (true) {
                            try (Jedis jedis = jedisPool.getResource()) {
                                Pipeline pipeline = jedis.pipelined();
                                for (int j = 0; j < PACKAGE_NUM; j++) {
                                    pipeline.hset(name, Thread.currentThread().getName() + num++, "1");
                                }
                                pipeline.sync();
                            }catch (Exception e){

                            }
                        }
                    }
            ).start();
        }
    }

}
