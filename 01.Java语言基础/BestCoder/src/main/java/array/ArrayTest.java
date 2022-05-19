package array;

/**
 * @author cat
 * @description
 * @date 2022/5/18 下午1:19
 */

public class ArrayTest {
    public static void main(String[] args) {

        // （1）动态初始化：数组声明且为数组元素分配空间与赋值的操作分开进行
        int[] arr1 = new int[10];

        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = i;
        }

        // （2）静态初始化：定义数组的同时就分配空间并赋值
        int[] smallPrimes = {2, 3, 5, 7, 11, 13};

        // 匿名数组
        int[] a;
        a = new int[] {17, 19, 23, 29};

    }


}
