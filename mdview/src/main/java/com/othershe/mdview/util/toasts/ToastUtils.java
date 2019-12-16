package com.othershe.mdview.util.toasts;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    private Context context;


    public ToastUtils(Context context) {
        this.context = context;
    }

    public void show(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
