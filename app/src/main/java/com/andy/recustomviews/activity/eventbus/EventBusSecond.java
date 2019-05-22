package com.andy.recustomviews.activity.eventbus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.andy.recustomviews.R;
import com.andy.recustomviews.activity.Base2Activity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBusSecond extends Base2Activity {

    @BindView(R.id.btn_first_event)
    Button btnFirstEvent;
    @BindView(R.id.btn_second_event)
    Button btnSecondEvent;
    @BindView(R.id.btn_third_event)
    Button btnThirdEvent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_event_bus_second;
    }

    @Override
    public void init(Bundle bundle) {

    }

    @OnClick(R.id.btn_first_event)
    public void onViewClicked() {
        FirstEvent firstEvent = new FirstEvent();
        firstEvent.setmMsg("hello, I am Andy1");
        EventBus.getDefault().post(firstEvent);
    }

    @OnClick({R.id.btn_second_event, R.id.btn_third_event})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_second_event:
                SecondEvent secondEvent = new SecondEvent();
                secondEvent.setmMsg("hello, I am Andy2");
                EventBus.getDefault().post(secondEvent);
                break;
            case R.id.btn_third_event:
                ThirdEvent firstEvent = new ThirdEvent();
                firstEvent.setmMsg("hello, I am Andy3");
                EventBus.getDefault().post(firstEvent);
                break;
        }
    }
}
