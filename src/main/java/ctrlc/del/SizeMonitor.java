package ctrlc.del;

import ctrlc.HashContext;
import ctrlc.RedisClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.HashMap;
import java.util.Map;

import static ctrlc.constant.Constants.ALL_KEYS;

/**
 * @author zhou.peng
 * @date 2020-10
 */
@SuppressWarnings("all")
public class SizeMonitor {

    @SuppressWarnings("all")
    public static void start() {
        new Thread(() -> {
            while (true) {
                try {
                    Map<String, Response<Long>> sizeMap = new HashMap<>();
                    Map<String, Long> result = new HashMap<>();
                    try (Jedis jedis = RedisClient.getPool().getResource()) {
                        Pipeline pipeline = jedis.pipelined();
                        ALL_KEYS.forEach(key -> {
                            sizeMap.put(key, pipeline.hlen(key));
                        });
                        pipeline.sync();
                        sizeMap.forEach((k, v) -> {
                            result.put(k, v.get());
                        });
                    }
                    System.out.println(result);
                    HashContext.setSizeMap(result);
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
