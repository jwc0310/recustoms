package com.andy.materialtest.views;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.andy.materialtest.bean.Fruit;
import com.andy.materialtest.adapters.FruitAdapter;
import com.andy.materialtest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test2Activity extends BaseActivity {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private TextView textView;

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
    private FruitAdapter adapter;

    private void initFruits(){
        list.clear();
        for (int i = 0; i < 10; i++){
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            list.add(fruits[index]);
        }
    }

    @Override
    public int contentViewResId() {
        return R.layout.activity_test2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = (TextView) findViewById(R.id.load_more);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessageDelayed(0x2, 2000);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new FruitAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        recyclerView.setAdapter(adapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!swipeRefresh.isRefreshing() && !recyclerView.canScrollVertically(1)){
                    Log.e("Andy", "bottom");
                    textView.setVisibility(View.VISIBLE);
                    handler.sendEmptyMessageDelayed(0x1, 2000);
                }else {
                    if (textView.getVisibility() != View.GONE){
                        textView.setVisibility(View.GONE);
                    }
                }
            }
        });

        initFruits();
        adapter.notifyDataSetChanged();
    }


    Handler handler = new Handler(){
        public void handleMessage(Message msg){

            if (msg.what == 0x1){
                for (int i = 0; i< 10; i++){
                    list.add(fruits[i%fruits.length]);
                }
                textView.setVisibility(View.GONE);
            }else if (msg.what == 0x2){
                initFruits();
                swipeRefresh.setRefreshing(false);
            }
            adapter.notifyDataSetChanged();
        }
    };
}
