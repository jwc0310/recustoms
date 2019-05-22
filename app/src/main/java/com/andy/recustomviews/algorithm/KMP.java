package com.andy.recustomviews.algorithm;

/**
 * Created by Administrator on 2017/8/28.
 */

public class KMP {

    public static void main(String[] args){
        print("--------------------------------entry");

        String origin = "acaabc";
        String format = "aab";

        if (simpleAlgorithm(origin, format) != -1){
            print("contain");
        }else {
            print("no contain");
        }



        print("--------------------------------leave");
    }

    private static int simpleAlgorithm(String o, String f){

        for (int i = 0; i< o.length(); i++){
            if (o.length() - i < f.length()){
                return -1;
            }

            for (int j = 0; j < f.length(); j++){
                if (o.charAt(i+j) != f.charAt(j)){
                    break;
                }

                if (j == f.length() -1){
                    return i;
                }
            }
        }

        return -1;
    }

    private static int kmpAlgorithm() {

        return -1;
    }

    private static void print(String str){
        System.out.println(str);
    }

}
