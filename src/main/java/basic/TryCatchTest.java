package basic;

public class TryCatchTest {
    public int vaule = 0;

    public static void main(String[] args) {
        int num = 10;
//		System.out.println(test1(num)) ;
        System.out.println(test2(num));
//		System.out.println("test()函数返回：" + test(10));
    }

    public static int test2(int b) {
        try {
            b += 20;
            return b;
            // return的局部变量，暂存在局部变量第二行
        } finally {
            b += 30;
            System.out.println(b);
        }
    }

    private static int test(int value) {
        TryCatchTest t = new TryCatchTest();
        try {
            value += 1;
            System.out.println("Try block executing: " + value);
            return value;
        } catch (Exception e) {
            value += -1;
            System.out.println("Catch Error executing: " + value);
            return value;
        } finally {
            value += 3;
            System.out.println("finally executing: " + value);
            return value;
        }
    }

    public static int test1(int a) {
        try {
            a += 20;
            return a;
        } finally {
            a += 30;
            return a;
        }
    }
}