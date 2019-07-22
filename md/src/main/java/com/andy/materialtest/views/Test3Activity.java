package com.andy.materialtest.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.andy.materialtest.bean.Fruit;
import com.andy.materialtest.adapters.FruitMAdapter;
import com.andy.materialtest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test3Activity extends AppCompatActivity {


    private GridView gridView;
    private FruitMAdapter adapter;
    private LinearLayout loadMore;

    private Fruit[] fruits = {
            new Fruit("草莓", R.drawable.caomei),
            new Fruit("橙子", R.drawable.chengzi),
            new Fruit("猕猴桃", R.drawable.mihoutao),
            new Fruit("苹果", R.drawable.pingguo),
            new Fruit("石榴", R.drawable.shiliu),
            new Fruit("桃子", R.drawable.taozi),
            new Fruit("香蕉", R.drawable.xiangjiao),
            new Fruit("西瓜", R.drawable.xigua),
            new Fruit("樱桃", R.drawable.yingtao)
    };
    private List<Fruit> list = new ArrayList<>();

    private void initFruits(){
        list.clear();
        for (int i = 0; i < 2; i++){
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            list.add(fruits[index]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);
        loadMore = (LinearLayout) findViewById(R.id.loading_more);

        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.e("Andy", "2 = "+view.getFirstVisiblePosition());
                Log.e("Andy", "3= "+view.getLastVisiblePosition());
                if (view.getFirstVisiblePosition() != 0 && view.getLastVisiblePosition() >= 0 && view.getLastVisiblePosition() == view.getCount() - 1) {
                    loadMore.setVisibility(View.VISIBLE);
                } else {
                    if (loadMore.getVisibility() != View.GONE){
                        loadMore.setVisibility(View.GONE);
                    }
                }
            }
        });
        adapter = new FruitMAdapter(this, list);
        gridView.setAdapter(adapter);
        initFruits();
        adapter.notifyDataSetChanged();
    }
}
