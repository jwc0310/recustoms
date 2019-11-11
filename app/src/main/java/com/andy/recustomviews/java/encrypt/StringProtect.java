package com.andy.recustomviews.java.encrypt;

public class StringProtect {



    public static void main(String[] args) {

    }


    private static char m9(char p0, int p1) {
        char v0 = 1;

        v0 = (char )(v0 << p1);
        v0 = (char )(v0 & p0);
        v0 = (char) (v0 >> p1);
        return v0;
    }

    private static char m10(char p0, int p1) {
        char v0 = 1;

        v0 = (char )(v0 << p1);
        v0 = (char )(v0 ^ p0);
        v0 = (char) (v0 >> p1);
        return v0;
    }

    private static char m11(char p0, int p1) {
        char v0 = 1;

        v0 = (char )(v0 << p1);
        v0 = (char )(~v0);
        v0 = (char )(v0 & p0);

        return v0;
    }

    private static char m12(char p0, char p1, int p2) {
        char v0 = 0;
        float v5 = 8f;
        char v2 = 1;
        char v3, v4;

        int v1 = p2 % 3;
        if (v1 == 0) {
            v1 = v0;
            v0 = p0;

            //goto_0
            if (v1 >= v5) {
                return v0;
            }

            //cond_1
            v3 = m9(p0, v1);
            v4 = m9(p1, v1);
            v3 = (char)(v3 ^ v4);

            if (v3 == 0) {

            }

        }

        //cond_4



        v0 = (char )(v0 << p1);
        v0 = (char )(~v0);
        v0 = (char )(v0 & p0);

        return v0;
    }



}
