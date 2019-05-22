package com.andy.recustomviews.java;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */
public class test {

    private static Object lock;
    private static int j = 0;

    private static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                System.out.println("runnable waiting ...");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("runnable doing ...");
            synchronized (lock) {
                try {
                    System.out.println("runnable lock ...");
                    Thread.sleep(5000);
                    System.out.println("runnable unlock ...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private static Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            while (lock != null){
                try {
                    Thread.sleep(500);
                    j++;
                    System.out.println("j = " + j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public static void main(String[] args){


        String origin = "Response{protocol=http/1.1, code=200, message=OK, url=https://play.google.com/store/apps/details?id=com.netmarble.lineageII&referrer=mat_click_id%3Dbd552c975148e02d9e13264f3a58e579-20170621-175038}";
        String[] sp1 = origin.split("\\{");
        for (String str : sp1){
            System.out.println(str);
        }
        String[] sp2 = sp1[1].split("}");

        for (String str : sp2){
            System.out.println(str);
        }

        String[] sp3 = sp1[1].split("}")[0].split(",");
        String url = "";
        for (String str : sp3){
            System.out.println(str.trim());
            if (str.trim().startsWith("url=")){
                url = str.trim();
                break;
            }
        }

        if (url.length() != 0){
            String[] paths = url.split("\\?")[1].split("&");
            for (String str : paths){
                if (str.startsWith("id=")){
                    String packageName = str.substring(3);
                    System.out.println("pn = "+packageName);
                }
            }
        }


//        Account account = new Account("zhang san", 10000.0f);
//        AccountOperator accountOperator = new AccountOperator(account);
//
//        final int THREAD_NUM = 5;
//        Thread threads[] = new Thread[THREAD_NUM];
//        for (int i = 0; i < THREAD_NUM; i ++) {
//            threads[i] = new Thread(accountOperator, "Thread" + i);
//            threads[i].start();
//        }

//        SyncThread thread = new SyncThread();
//        Counter counter = new Counter();
//        Thread thread1 = new Thread(counter, "A");
//        Thread thread2 = new Thread(counter, "B");
//        thread1.start();
//        thread2.start();

//        lock = new Object();
//        System.out.println("start...");
//        new Thread(runnable).start();
//        new Thread(runnable2).start();
//        System.out.println("over...");

//        if (str2.startsWith(str)){
//            str2 = str2.substring(str.length(), str2.length()-str3.length());
//            System.out.println(str2);
//        }


//        Long str = System.currentTimeMillis();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String sd = sdf.format(str);
//        System.out.println("-----" + sd);
//        System.out.println("-----"+1077/720);

    }

    private static void substringTest(){

        String str = "##COMMITstart##";
        String str3 = "##COMMITend##";

        String str00 = "##EDITstart##";
        String str30 = "##EDITend##";

        String tmp1 = str+"bbmmnn"+str3;
        String tmp2 = str00+"cccddnn"+str30;


        String str2 = tmp1+tmp1+tmp2+tmp1+tmp2;
        while(true){
            if (str2.length() == 0){
                break;
            }
            if (str2.startsWith(str)){
                int index = str2.indexOf(str3);
                String tmp = str2.substring(0, index+str3.length());
                System.out.println("commit = "+tmp.substring(str.length(), tmp.length()-str3.length()));
                str2 = str2.substring(tmp.length());
//                System.out.println("commit = "+tmp+", left = "+str2);
                continue;
            }
            if (str2.startsWith(str00)){
                int index = str2.indexOf(str30);
                String tmp = str2.substring(0, index+str30.length());
                System.out.println("edit = "+tmp.substring(str00.length(), tmp.length()-str30.length()));
                str2 = str2.substring(tmp.length());
//                System.out.println("edit = "+tmp+", left = "+str2);
                continue;
            }
            break;
        }
    }

    private static void tt(){
        String pp = "study";
        String[] dd = pp.split(",");

        String name = "123"+"55555"+"312";
        name = name.substring(3);
        System.out.println(name);
        name = name.substring(0, name.length()-3);
        System.out.println(name);


        String beginDate="1328007600000";

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");

        String sd = sdf.format(new Date(Long.parseLong(beginDate)));

        System.out.println(sd);

        List<HashMap> list = new ArrayList<>();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", "2017年1月1日");
        list.add(map);

        HashMap<String, Study> map1 = new HashMap<String, Study>();
        map1.put("key", new Study());
        list.add(map);

        list.add(map1);

        for (int i = 0; i< list.size();i++){
            if (list.get(i).get("key") instanceof Study){
                System.out.println("study");
            }else {
                System.out.println("string");
            }
        }
        System.out.println("map size is "+map.size());
    }

}
