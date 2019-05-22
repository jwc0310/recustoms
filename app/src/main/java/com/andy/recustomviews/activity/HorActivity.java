package com.andy.recustomviews.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.andy.recustomviews.R;

import java.util.ArrayList;

/**
 *
 * Created by Administrator on 2017/1/12.
 */
public class HorActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.hori_activity);
        TextView textView = new TextView(this);
        textView.setText("dddd");
    }
}
