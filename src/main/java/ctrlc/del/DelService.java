package ctrlc.del;

import ctrlc.RedisClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static ctrlc.constant.Constants.ALL_KEYS;

/**
 * @Author: wg
 * @Date: 2020/10/23 3:10 下午
 */
public class DelService {

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(30, 3000,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<>());

    static {
        THREAD_POOL_EXECUTOR.prestartCoreThread();

    }

    private static final Set<String> CURRENT_KEY = new HashSet<>();

    static {
        CURRENT_KEY.add("group-10");
        CURRENT_KEY.add("group-12");

    }

    private static void delKey(String key) {


        String cursor = "0";

        ScanParams scanParams = new ScanParams().match("*").count(2000);

        ScanResult<Entry<String, String>> scanResult;


        Jedis resource = RedisClient.getPool().getResource();
        while (true) {
            try {
                scanResult = resource.hscan(key, cursor, scanParams);
                cursor = scanResult.getCursor();
                List<Entry<String, String>> result = scanResult.getResult();

                THREAD_POOL_EXECUTOR.execute(() -> submitDel(key, result));
                if (Integer.parseInt(cursor) < 0) {
                    cursor = "0";
                }
            } catch (Exception e) {

            }
        }
    }

    private static void submitDel(String key, List<Entry<String, String>> list) {
        if (list==null||list.isEmpty()){
            return;
        }
        String[] objects = list.stream().map(Entry::getKey).toArray(String[]::new);
        RedisClient.getPool().getResource().hdel(key, objects);
    }

    public static void start() {
        ALL_KEYS.stream().filter(o -> !CURRENT_KEY.contains(o)).forEach(o -> THREAD_POOL_EXECUTOR.execute(() -> DelService.delKey(o)));
        ALL_KEYS.stream().filter(o -> !CURRENT_KEY.contains(o)).forEach(o -> THREAD_POOL_EXECUTOR.execute(() -> DelService.delKey(o)));
    }

}
