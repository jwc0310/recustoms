package com.andy.recustomviews.algorithm;

public class RainwaterCollection {


    public static void main(String[] args) {

        int[] abc = {2, 1, 0, 2};
        System.out.println(collect(abc) + "");

    }

    //一层一层的计算
    private static int collect(int[] height) {
        int length = height.length;
        int max = 0;
        //找到最高层
        for (int i = 0; i < length; i ++) {
            max = Math.max(max, height[i]);
        }

        int res = 0;

        for (int i = 0; i < max; i++) {


            for (int j = 0; j < length; j++) {

                if (height[j] < i + 1) //高度小于 i + 1
                    continue;
                System.out.println("i = " + i +", j = " + j +", h = " + height[j]);

                for (int k = j + 1; k < length; k++) {
                    if (height[k] >= i + 1) {
                        res += (k - j -1);
                        break;
                    }
                }
            }
        }

        return res;
    }


}
