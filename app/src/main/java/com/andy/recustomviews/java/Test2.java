package com.andy.recustomviews.java;

import android.net.Uri;

import java.net.URI;

/**
 * Created by Administrator on 2017/6/22.
 */
public class Test2 {

    public static void main(String args[]){
        //https://play.google.com/store/apps/details?id=com.entermate.luthiel

        String url = "https://play.google.com/store/apps/details?id=com.entermate.luthiel";
        Uri uri = Uri.parse(url);
        System.out.println(uri.getHost());
        System.out.println(uri.getAuthority());
        System.out.println(uri.getPath());
        System.out.println(uri.getEncodedAuthority());
        System.out.println(uri.getQuery());
        System.out.println(uri.getQueryParameter("id"));
        System.out.println(uri.toString());

    }

    private static void t1(){
        String str = "abcdefghijklmnopqrstuvwxyz";
        String tag1 = "def", tag2 = "xyz", tag3 = "ddd";

        System.out.println(tag1 +" index = "+str.indexOf(tag1));
        System.out.println(tag2 +" index = "+str.indexOf(tag2));
        System.out.println(tag3 +" index = "+str.indexOf(tag3));

        System.out.println("commit str = "+ getCommitStr(str, tag1, tag2, tag3));

        String commitStr = getCommitStr(str, tag1, tag2, tag3);

        System.out.println("left = "+str.substring(commitStr.length()));
    }

    private static String getCommitStr(String st1, String t1, String t2, String t3){
        if (!st1.contains(t1) && !st1.contains(t2) && !st1.contains(t3)){
            return st1;
        }

        int i1 = st1.indexOf(t1);
        int i2 = st1.indexOf(t2);
        int i3 = st1.indexOf(t3);

        int index = st1.length();
        if (i1 != -1 && i1 < index){
            index = i1;
        }

        if (i2 != -1 && i2 < index){
            index = i2;
        }

        if (i3 != -1 && i3 < index){
            index = i3;
        }

        return st1.substring(0, index);
    }
}
