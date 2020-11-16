package code;

import java.util.*;

class Solution410 {
    public static void main(String[] args) {
        printRandomMatrix(7, 10, 0);
        printRandomMatrix(7, 10, 1);
        printRandomMatrix(7, 10, 2);
        lengthOfLongestSubstring("abcabcbb");
//        minWindow("ADOBECODEBANC",
//                "ABC");\
        trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1});

    }

    public static int trap(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int leftMaxFromLeft = 0;
        int rightMaxFromRight = 0;
        int ans = 0;
        while (left > right) {
            if (height[left] > leftMaxFromLeft) {
                leftMaxFromLeft = height[left];
            } else {
                ans += Math.min(leftMaxFromLeft, rightMaxFromRight) - height[left];
            }
            left++;
            if (height[right] > rightMaxFromRight) {
                rightMaxFromRight = height[right];
            } else {
                ans += Math.min(leftMaxFromLeft, rightMaxFromRight) - height[left];
            }
            right--;
        }

//        int ans = 0, current = 0;
//        Deque<Integer> stack = new LinkedList<Integer>();
//        while (current < height.length) {
//            while (!stack.isEmpty() && height[current] > height[stack.peek()]) {
//                int top = stack.pop();
//                if (stack.isEmpty())
//                    break;
//                int distance = current - stack.peek() - 1;
//                int bounded_height = Math.min(height[current], height[stack.peek()]) - height[top];
//                ans += distance * bounded_height;
//            }
//            stack.push(current);
//            current++;
//        }
        return ans;
    }

    public int trapTwoPoints(int[] height) {
        int left = 0, right = height.length - 1;
        int ans = 0;
        int left_max = 0, right_max = 0;
        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= left_max) {
                    left_max = height[left];
                } else {
                    ans += (left_max - height[left]);
                }
                ++left;
            } else {
                if (height[right] >= right_max) {
                    right_max = height[right];
                } else {
                    ans += (right_max - height[right]);
                }
                --right;
            }
        }
        return ans;
    }

    private static void printRandomMatrix(int base, int toChange, int origin) {
        System.out.println("rowFrom = " + 1 + " : ");
        System.out.print("** ");
        for (int col = 1; col <= base; col++) {
            System.out.printf("%02d ", col);
        }
        System.out.println();
        for (int row = 1; row < base + 1; row++) {
            System.out.printf("%02d ", row);
            for (int col = 1; col <= base; col++) {
                int res = (row - 1) * base + col;
                System.out.printf("%2d ", origin == 0 ? (res - 1) % toChange + 1
                        : (origin == 1 ? res : (res - 1) % toChange));
            }
            System.out.println();
        }
    }

    public int maxPoints(int[][] points) {
        //当为0个点，1个点，2个点。他们必然共线
        if (points.length < 3) {
            return points.length;
        }
        //创建哈希表，保存点与点之间的斜率
        HashMap<List<Integer>, Integer> map = new HashMap<>();
        int n = points.length;
        int maxNumHrizontal;//横向共线，即纵坐标相同点的个数k = 0
        int maxVertical;//纵向共线，即横坐标相同点的个数K = ∞
        int maxDuplicate;//相同点的个数 k = 0/0 = NaN
        int maxNum;//K非0或者非无穷大的共线点个数

        int maxP = 0;//最大共线点数
        for (int i = 0; i < n; i++) {//从每个点开始，看看它后面的点是否共线
            maxNum = 1;
            maxNumHrizontal = 1;
            maxVertical = 1;
            maxDuplicate = 1;
            for (int j = i + 1; j < n; j++) {
                int numerator = points[j][1] - points[i][1];//纵坐标之差，k的分子 y2 - y1
                int denominator = points[j][0] - points[i][0];//横坐标之差，k的分母 x2 - x1
                //横纵坐标差都为0，说明这两个点相同
                if (numerator == 0 && denominator == 0) {
                    maxDuplicate++;
                }
                //纵坐标之差为0，横坐标之差不为0，说明在水平上呈一条线
                else if (numerator == 0 && denominator != 0) {
                    maxNumHrizontal++;
                }
                //纵坐标之差为0，横坐标之差不为0，说明在水平上呈一条线
                else if (denominator == 0 && numerator != 0) {
                    maxVertical++;
                }
                //横纵坐标之差都不为0
                else {
                    int positive = 1;//设立一个标志，确定K值的正负
                    //横纵坐标的差的乘积小于0，说明k为负
                    if (numerator * denominator < 0) {
                        positive = 0;
                    }
                    //由于计算最大公因数时，要求数都为正数
                    int gcdnum = gcd(Math.abs(numerator), Math.abs(denominator));
                    //化为最简分数
                    numerator /= gcdnum;
                    denominator /= gcdnum;
                    //当k为负数时，为了将分子为负分母为正或者分子为正分母为负的情况统一起来，令分子始终为正，分母为负
                    if (positive == 0) {
                        denominator *= -1;
                    }
                    //k值用List表示，第一位为分子，第二位为分母
                    List<Integer> k = new ArrayList<>();
                    k.add(numerator);
                    k.add(denominator);
                    //k值存在，就在原来的基础上+1；k值不存在，就在默认的1点共线上+1
                    map.put(k, map.getOrDefault(k, 1) + 1);
                    //当k值对应的value（共线点个数）大于maxNum，更新maxNum
                    maxNum = Math.max(maxNum, map.get(k));
                }
            }
            //在一个点与它后面所有点都计算完K值和共线点之后，求最多的共线点个数
            //相同点和共线点要一起计算总个数
            //在计算共线时，在相同的几个点中，多记录了一次所以要-1
            maxP = Math.max(maxP, maxNum + maxDuplicate - 1);
            maxP = Math.max(maxP, maxNumHrizontal + maxDuplicate - 1);
            maxP = Math.max(maxP, maxVertical + maxDuplicate - 1);
            map.clear();
        }
        return maxP;
    }

    private int gcd(int a, int b) {
        int r;
        while (b > 0) {
            r = a % b;
            a = b;
            b = r;
        }
        return a;
    }


    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = null;
        ListNode tail = null;
        int remain = 0;
        while (l1 != null || l2 != null) {
            int val1 = l1 != null ? l1.val : 0;
            int val2 = l2 != null ? l2.val : 0;
            int res = val1 + val2 + remain;
            remain = res / 10;
            if (head == null) {
                head = new ListNode(res % 10);
                tail = head;
            } else {
                tail.next = new ListNode(res % 10);
                tail = tail.next;
            }
            l1 = l1 != null ? l1.next : l1;
            l2 = l2 != null ? l2.next : l2;
        }
        if (remain > 0) {
            tail.next = new ListNode(remain);
        }
        return head;
    }

    public static int lengthOfLongestSubstring(String s) { //移动右边，去探测左边
        int maxLength = 0;
        int left = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                left = Math.max(left, map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i), i);
            maxLength = Math.max(maxLength, left >= 0 ? (i - left + 1) : 0);
        }
        return maxLength;
    }

    public static int lengthOfLongestSubstringMoving(String s) {
        Set<Character> set = new HashSet<Character>();
        int maxLength = 0;
        int r = -1; // 从-1开始
        //固定左边，右边去探索
        for (int left = 0; left < s.length(); left++) {
            if (left != 0) {
                set.remove(s.charAt(left - 1));//踢出左边的
            }
            while (r + 1 < s.length() && !set.contains(s.charAt(r + 1))) {
                set.add(s.charAt(r + 1));
                r++;
            }
            maxLength = Math.max(maxLength, r - left + 1); // 包含left位置
        }
        return maxLength;
    }

    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        if (words == null || words.length == 0 || s == null || s.length() == 0) return res;
        HashMap<String, Integer> all = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            // 会有重复的words
            all.put(words[i], all.getOrDefault(words[i], 0) + 1);
        }
        // 所有word长度相同
        int singleLen = words[0].length();
        for (int l = 0; l < s.length() - singleLen * words.length + 1; l++) { // 固定左边， 探索右边界
            // 不满足完全包含所有字符就已经不符合
            HashMap<String, Integer> hasWord = new HashMap<>();  // 探索到的存在的word
            int matchCount = 0;  // 从l开始能连续匹配的word个数
            while (matchCount < words.length) {
                String w = s.substring(l + matchCount * singleLen, l + (matchCount + 1) * singleLen); // 找下一个word
                if (all.containsKey(w)) {
                    int newCount = hasWord.getOrDefault(w, 0) + 1;
                    if (newCount > all.getOrDefault(w, 0)) break; // 超出word可以重复的个数
                    hasWord.put(w, newCount);
                } else {
                    break;
                }
                matchCount++;
            }
            if (matchCount == words.length) { // 从l = left 左区间开始能探测到的，已经满足所有个数
                res.add(l);
            }
        }
        return res;
    }

    // S = "ADOBECODEBANC", T = "ABC"
    // "BANC"
    public static String minWindow(String s, String t) {
        int l = 0;
        int length = Integer.MAX_VALUE;
        int start = 0;
        int[] need = new int[128];
        int needCount = t.length();

        for (int i = 0; i < t.length(); i++) {
            need[t.charAt(i)]++;
        }

        for (int r = 0; r < s.length(); r++) {
            if (need[s.charAt(r)] > 0) {
                needCount--;
            }
            need[s.charAt(r)]--; // 第r个字符进入窗口
            if (needCount == 0) { // 找到所有必需字符
                while (l < r && need[s.charAt(l)] < 0) { // 考虑缩减左边届
                    need[s.charAt(l)]++;
                    l++;
                }
                if (r - l + 1 < length) {
                    length = r - l + 1;
                    start = l;
                }
                need[s.charAt(l)]++;
                l++;
                needCount++;
            }
        }
        return s.substring(start, length);
    }
}
