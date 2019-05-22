package com.andy.recustomviews.ndk;

/**
 * Created by Administrator on 2017/4/14.
 */
public class MyJni {

    static {
        System.loadLibrary("myjni");
    }

    public native String displayJni();
    public native String xyUpdate(String str1, String str2);
    public native int[] getIntArray(int size);
    public native String strConcat(String str1, String str2);
    public native Bean newBean(String msg, int what);
    public native String getString(Bean bean);
}
