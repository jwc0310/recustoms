package com.andy.recustomviews.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.andy.recustomviews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/12.
 */
public class HorView extends LinearLayout {

    private Context context;
    private LayoutInflater inflater;
    private List<View> viewList;

    public HorView(Context context) {
        super(context, null);
    }

    public HorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        inflater = LayoutInflater.from(context);
        initView();
    }
    private int position;
    /** 初始化 **/
    private void initView(){
        viewList = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            View tmpView = inflater.inflate(R.layout.hori_item, this);
            viewList.add(tmpView);
            tmpView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    remove();
                }
            });
        }
    }

    /** get count **/
    private int getCount(){
        return viewList.size();
    }

    private View getItem(){
        return viewList.get(position);
    }

    private synchronized void remove(){
        this.removeViewAt(position);
        viewList.remove(position);
    }
}
