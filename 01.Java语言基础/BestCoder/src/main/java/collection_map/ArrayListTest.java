package collection_map;

import java.util.ArrayList;

/**
 * @author cat
 * @description
 * @date 2022/6/2 下午7:28
 */
public class ArrayListTest {
    public static void main(String[] args) {

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("1");

        }

        list.add("1");


        System.out.println(list.size());
    }
}
