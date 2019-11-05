package com.andy.recustomviews.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/5.
 */
public class te {

    public static void main(String[] args){

        String[] tt = {"aa:bb:cc", "aa:bb", "aa"};
        for (int i =0; i<tt.length; i++) {
            String appId = tt[i];

            String appid = appId;
            String from = "官方";
            String module = "launcher-messagebox";

            String[] params = appId.split(":");

            for (int j = 0; j < params.length; j++){
                System.out.println(params[j]);
            }

            if (params.length == 3) {
                appid = isEmpty(params[0]) ? appId : params[0];
                from = isEmpty(params[1]) ? from : params[1];
                module = isEmpty(params[2]) ? module : params[2];
            } else if (params.length == 2) {
                appid = isEmpty(params[0]) ? appId : params[0];
                from = isEmpty(params[1]) ? from : params[1];
            }

            System.out.println("appid = "+appid+", from = "+from+", module = "+module);
            System.out.println("-----------------------------------------------------");

        }


        String version = "Linux version 3.10.0-tencent (ychuang@ubuntu) (gcc version 4.8.4 (Ubuntu 4.8.4-2 ubuntu1~14.04) ) #125 SMP PREEMPT Thu Sep 22 15:50:14 CST 2016";
        char[] chars = version.toCharArray();
        StringBuffer buffer = new StringBuffer();
        for (int cursor = 0; cursor <chars.length; cursor++) {
            char chr = chars[cursor];
            String hexValue = Integer.toHexString(chr);
            //System.out.println(chr + ": 0x"+hexValue);

            if (cursor % 4 == 0) {
                buffer.setLength(0);
            }

            buffer.insert(0, hexValue);

            if (cursor % 4 == 3) {
                buffer.insert(0, "0x");
                System.out.println(buffer.toString());
            }
        }

    }

    private static boolean isEmpty(String string){
        if (string == null || string.length() == 0){
            return true;
        }
        return false;
    }

}
