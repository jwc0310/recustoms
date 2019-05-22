package com.andy.recustomviews.java.collections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/11.
 */

public class MapTest {

    public static void main(String[] args) {
        Map<String, String> maps = new HashMap<>();
        maps.put("a1", "a11");
        maps.put("a2", "a12");
        maps.put("a3", "a13");
        maps.put("a4", "a15");
        maps.put("a5", "a15");

        System.out.println("map:" + maps);

        // HashMap的键值对个数
        System.out.println("size:" + maps.size());

        //遍历键值对
        Iterator<Map.Entry<String, String>> interator = maps.entrySet().iterator();
        while (interator.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry) interator.next();
            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
        }

        if (maps.containsKey("a1")) {
            System.out.println("contain a1");
        }

        if (!maps.containsKey("a6")) {
            System.out.println("not contain a6");
        }

        //遍历key
        Iterator<String> stringIterator = maps.keySet().iterator();
        while (stringIterator.hasNext()) {
            System.out.println("key = " + stringIterator.next());
        }
        //遍历value
        Iterator<String> valueIterator = maps.values().iterator();
        while (valueIterator.hasNext()) {
            System.out.println("value = " + valueIterator.next());
        }


    }

}
