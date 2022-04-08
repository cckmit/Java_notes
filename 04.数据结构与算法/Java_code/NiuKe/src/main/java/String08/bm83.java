package String08;
// @date 2022/4/8 上午11:29

import java.util.Arrays;

/**
 对于一个长度为 n 字符串，我们需要对它做一些变形。

 首先这个字符串中包含着一些空格，就像"Hello World"一样，然后我们要做的是把这个字符串中由空格隔开的单词反序，同时反转每个字符的大小写。

 比如"Hello World"变形后就变成了"wORLD hELLO"。

 输入描述：
 给定一个字符串s以及它的长度n(1 ≤ n ≤ 10^6)
 返回值描述：
 请返回变形后的字符串。题目保证给定的字符串均由大小写字母和空格构成。

 示例1
 输入：
 "This is a sample",16
 返回值：
 "SAMPLE A IS tHIS"
 */
public class bm83 {

    public static void main(String[] args) {
        trans( "This is a sample",16);
    }





    public static String trans(String s, int n) {
        // write code here
        String[] split = s.split(" ", -1);


        for (String s1 : split) {
            System.out.println(s1);
        }

        return "";
    }
}

//