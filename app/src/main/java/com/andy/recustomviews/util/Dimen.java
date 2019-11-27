package com.andy.recustomviews.util;

import android.content.Context;

public class Dimen {

    public static int toPx(Context context, float dp){
        return (int)(context.getApplicationContext().getResources().getDisplayMetrics().density * dp + 0.5);
    }

}
