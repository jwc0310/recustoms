package com.andy.recustomviews.rxjava2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andy.recustomviews.R;
import com.andy.recustomviews.views.XYFlowLayout;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.andy.recustomviews.util.Dimen.toPx;

public class RxjavaActivity extends AppCompatActivity {

    HashMap<String, Method> hashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        initMethod();
        XYFlowLayout flowLayout = findViewById(R.id.my_flowlayout);
        initFlowLayout(flowLayout);
    }

    private void initMethod(){
        try {
            hashMap.put("retry", RxjavaActivity.class.getMethod("test_retry", null));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void test_retry() {
        Log.e("RxjavaActivity", "test_retry was called");
        Rxjava_retry.test_retry();
    }

    private void initFlowLayout(XYFlowLayout xyFlowLayout){
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.rightMargin = toPx(this, 15);
        lp.topMargin = toPx(this, 5);
        lp.bottomMargin = toPx(this, 5);

        for(Map.Entry<String, Method> entry : hashMap.entrySet()) {
            final Map.Entry<String, Method> tmp = entry;
            TextView textView = new TextView(this);
            textView.setText(tmp.getKey());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        tmp.getValue().invoke(0);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            });

            xyFlowLayout.addView(textView);
        }
    }
}