package ctrlc.constant;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author zhou.peng
 * @date 2020-10
 */
public class Constants {

    public static List<String> ALL_KEYS = IntStream.rangeClosed(1, 20)
            .mapToObj(num -> {
                String str = String.valueOf(num);
                if (num < 10) {
                    str = "0" + str;
                }
                return "group-" + str;
            }).collect(Collectors.toList());

}
