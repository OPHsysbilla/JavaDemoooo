package ctrlc;

import java.util.Map;

/**
 * @Author: wg
 * @Date: 2020/10/23 3:06 下午
 */
public class HashContext {
    /**
     * hash->len
     */
    private static volatile  Map<String,Long> sizeMap;

    public static Map<String, Long> getSizeMap() {
        return sizeMap;
    }

    public static void setSizeMap(Map<String, Long> sizeMap) {
        HashContext.sizeMap = sizeMap;
    }
}
