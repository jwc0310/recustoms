package com.andy.recustomviews.test;

/**
 * Created by Administrator on 2017/8/24.
 */

public class MyTest {
    private static final String tt = "mainmainmian";
    private static final String match = "&memu_gaid=";

    private static String doIt(String url){
        if (!url.contains(match))
            return url;
        int index = url.indexOf(match);
        System.out.println("index = "+index);
        int index2 = url.indexOf("&", index+1);
        System.out.println("index2 = "+index2);

        String target="";
        if (index2 == -1){
            target = url.substring(index);
            System.out.println("target = "+target);
            String gaid = "";
            if (target.trim().length() > match.length()){
                gaid = target.substring(match.length());
            }
            System.out.println("gaid = "+gaid);
            if (gaid == null || gaid.trim().length() == 0){
                url = url.substring(0, index);
            }else {
                url = url.substring(0, index)+"&"+gaid+"="+tt;
            }

        }else {
            String b = url.substring(0, index);
            String a = url.substring(index2);
            target = url.substring(index, index2);
            System.out.println("target = "+target);
            String gaid = "";
            if (target.trim().length() > match.length()){
                gaid = target.substring(match.length());
            }
            System.out.println("gaid = "+gaid);
            if (gaid == null || gaid.trim().length() == 0){
                url = b+a;
            }else {
                url = b+a+"&"+gaid+"="+tt;
            }
        }

        return url;
    }

    public static void main(String[] args) {

        String url0 = "http://tracking.applift.com/aff_c?offer_id=48465&aff_id=25604&source=launcher-preinstall_auto&unid=e45aa39e-8b17-422e-88c2-10c145714e77";
        String url1 = "http://tracking.applift.com/aff_c?offer_id=48465&aff_id=25604&source=launcher-preinstall_auto&memu_gaid=unid";
        String url11 = "http://tracking.applift.com/aff_c?offer_id=48465&aff_id=25604&source=launcher-preinstall_auto&memu_gaid=";
        String url2 = "http://tracking.applift.com/aff_c?offer_id=48465&aff_id=25604&source=launcher-preinstall_auto&memu_gaid=unid&unid=e45aa39e-8b17-422e-88c2-10c145714e77";
        String url21 = "http://tracking.applift.com/aff_c?offer_id=48465&aff_id=25604&source=launcher-preinstall_auto&memu_gaid=&unid=e45aa39e-8b17-422e-88c2-10c145714e77";

        String url = "";
        url = doIt(url0);
        System.out.println("url0 = "+url);

        url = doIt(url2);
        System.out.println("url2 = "+url);

        url = doIt(url21);
        System.out.println("url21 = "+url);

        url = doIt(url1);
        System.out.println("url1 = "+url);

        url = doIt(url11);
        System.out.println("url11 = "+url);


        System.out.println(MyTest.class.getSimpleName());
        System.out.println(MyTest.class.getCanonicalName());

        String str1 = "我们 是";
        System.out.println(str1 + " 全是字母：" + str1.matches("[a-zA-Z]+"));
        String str2 = "abcdefsdkfsdfsdfsdfsdf";
        System.out.println(str2 + " 全是字母：" + str2.matches("[a-zA-Z]+"));
        String str3 = "abcde fsdk fsdfs dfsdf sdf";
        System.out.println(str3 + " 全是字母：" + str3.matches("[a-zA-Z, ]+"));


    }

}
