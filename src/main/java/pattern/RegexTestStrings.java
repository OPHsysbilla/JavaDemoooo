package pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTestStrings {
    public static final String EXAMPLE_TEST = "This is my small example "
            + "string which I'm going to " + "use for pattern matching.";

    public static void main(String[] args) {
//        testEscape();
//        notRecordGroup(true);
//        notRecordGroup(false);
//        nameOneGroup();
//        matchYear();
//        alphaAfterDigital();
        System.out.println(3%1);
    }

    private static void testReplacement() {
        System.out.println(EXAMPLE_TEST.matches("\\w.*"));
        String[] splitString = (EXAMPLE_TEST.split("\\s+"));
        System.out.println(splitString.length);// should be 14
        for (String string : splitString) {
            System.out.println(string);
        }
        // replace all whitespace with tabs
        System.out.println(EXAMPLE_TEST.replaceAll("\\s+", "🌹"));
    }

    private static void testEscape() {
        // 去除单词与 , 和 . 之间的空格
        String Str = "Hello , World .";
        String pattern = "(\\w)(\\s+)([.,])";
        // $0 匹配 `(\w)(\s+)([.,])` 结果为 `o空格,` 和 `d空格.`
        // $1 匹配 `(\w)` 结果为 `o` 和 `d`
        // $2 匹配 `(\s+)` 结果为 `空格` 和 `空格`
        // $3 匹配 `([.,])` 结果为 `,` 和 `.`
        System.out.println(Str.replaceAll(pattern, "$1$3")); // Hello, World.
    }

    // test ?:
    private static void notRecordGroup(boolean notRecordGroup) {
        String str = "img.jpg   342.png";
        // 分组且创建反向引用
        Pattern pattern = Pattern.compile(notRecordGroup ? "(jpg|png)" : "(?:jpg|png)");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            System.out.println(matcher.group());
            System.out.println(matcher.group(1));
        }
    }

    public static void nameOneGroup() {
        String str = "@wxj 你好啊";
        Pattern pattern = Pattern.compile("@(?<first>\\w+\\s)"); // 保存一个副本
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            System.out.println(matcher.group());
            System.out.println(matcher.group(1));
            System.out.println(matcher.group("first") + "🌹" + matcher.group("first").length());
        }
    }

    /**
     * (?i) 使正则忽略大小写。
     * (?s) 表示单行模式（"single line mode"）使正则的 . 匹配所有字符，包括换行符。
     * (?m) 表示多行模式（"multi-line mode"），使正则的 ^ 和 $ 匹配字符串中每行的开始和结束。
     */
    public static void matchYear() {
        String str = "1990\n2010\n2017";
        // 这里应用了 (?m) 的多行匹配模式，只为方便我们测试输出
        // "^1990$|^199[1-9]$|^20[0-1][0-6]$|^2017$" 为判断 1990-2017 正确的正则表达式
        Pattern pattern = Pattern.compile("(?m)^199[0-9]$|^20[0-1][0-7]$");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    /**
     * 字符"*"匹配所有单个字符，除了换行符（Linux 中换行是 \n，Windows 中换行是 \r\n）
     * <div>(?<title>.+)</div> （?<title>）表示命名捕获到的这个group，后续可以不用$1，$2，而用title获得这个组
     */
    public static void testDigitalAndAlphabet(String s) {
        Pattern pattern = Pattern.compile("(\\d+)([a-zA-Z]+)");
        Matcher matcher = pattern.matcher(s);
        StringBuilder sb = new StringBuilder(s);
        sb.append("\n");
        while (matcher.find()) {
            sb.append("Start index: ").append(matcher.start());
            sb.append(" End index: ").append(matcher.end()).append(" ");
            for (int i = 0; i <= matcher.groupCount(); i++) {
                sb.append(" [").append(i).append("]: ").append(matcher.group(i));
            }
            sb.append("\n");
        }
        System.out.print(sb.toString());
    }

    private static void alphaAfterDigital() {
        testDigitalAndAlphabet("4apple11architecture9serious78dragon9c");
        testDigitalAndAlphabet("aa44");
        testDigitalAndAlphabet("aaa");
    }

}