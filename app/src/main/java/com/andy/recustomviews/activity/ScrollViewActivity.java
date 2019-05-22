package com.andy.recustomviews.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.andy.recustomviews.R;
import com.andy.recustomviews.views.MyScrollView;

/**
 * Created by Administrator on 2016/12/23.
 */
public class ScrollViewActivity extends BaseActivity {

    private View view2;
    private int[] loc2;
    private View view;

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_scroll);
        view2 = findViewById(R.id.white_view2);
        view = findViewById(R.id.white_view);
        final MyScrollView myScrollView = (MyScrollView)findViewById(R.id.scrollview);
        myScrollView.setScrollViewListener(new MyScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
                if (loc2 == null){
                    loc2 = new int[2];
                    view2.getLocationInWindow(loc2);
                }
                int[] location = new int[2];
                view.getLocationInWindow(location);
                if (location[1] > loc2[1]){
                    if (view2.getVisibility() != View.GONE){
                        view2.setVisibility(View.GONE);
                    }
                }else {
                    if (view2.getVisibility() != View.VISIBLE){
                        view2.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void scrollBottom(int updown) {
                if (updown == 0){
                    Log.e("Andy", "bottom");
                }else {
                    Log.e("Andy", "top");
                }
            }
        });
        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout linearLayout = (LinearLayout)myScrollView.getChildAt(0);
                if (linearLayout.getChildCount() > 0){
                    linearLayout.removeViewAt(0);
                    int h = 0;
                    for (int i = 0; i < linearLayout.getChildCount();i++){
                        h += linearLayout.getChildAt(i).getHeight();
                    }
                }else {
                    Toast.makeText(ScrollViewActivity.this, "no child now", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
