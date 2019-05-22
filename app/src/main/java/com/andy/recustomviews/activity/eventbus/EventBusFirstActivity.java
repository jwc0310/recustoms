package com.andy.recustomviews.activity.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.andy.recustomviews.R;
import com.andy.recustomviews.activity.Base2Activity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class EventBusFirstActivity extends Base2Activity {

    @BindView(R.id.btn_try)
    Button btnTry;
    @BindView(R.id.tv)
    TextView tv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_event_bus_first;
    }

    @Override
    public void init(Bundle bundle) {
        EventBus.getDefault().register(this);
    }

    @OnClick(R.id.btn_try)
    void btnEvent(){
        startActivity(new Intent(this, EventBusSecond.class));
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * onEvent 事件的发送和接收在同一个线程，onEvent方法中不能执行耗时操作(会导致事件分发延迟)
     * onEventMainThread   UI线程中执行，不能有耗时操作
     * onEventBackgroundThread  线程中执行
     * onEventAsync     都会创建子线程在执行onEventAsync
     */
    @Subscribe
    public void onEventMainThread(FirstEvent event){
        String msg = "onEventMainThread收到了消息："+event.getmMsg();
        Log.e("Andy", event.getmMsg());
        tv.setText(msg);
        Toast.makeText(EventBusFirstActivity.this, event.getmMsg(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onEventMainThread(SecondEvent event){
        Log.e("Andy", event.getmMsg());
    }

    @Subscribe
    public void onEventBackgroundThread(SecondEvent event){
        Log.e("Andy", event.getmMsg());
    }

    @Subscribe
    public void onEvent(SecondEvent event){
        Log.e("Andy", event.getmMsg());
    }


    @Subscribe
    public void onEvent(ThirdEvent event){
        Log.e("Andy", event.getmMsg());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void aa(ThirdEvent event){
        Log.e("Andy", event.getmMsg()+"lllllllllll");
    }

}
