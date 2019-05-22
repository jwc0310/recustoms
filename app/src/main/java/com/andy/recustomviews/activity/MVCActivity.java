package com.andy.recustomviews.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.andy.recustomviews.R;
import com.andy.recustomviews.interfaces.OnRequestListener;
import com.andy.recustomviews.model.IWeatherModel;
import com.andy.recustomviews.model.WeatherModelImpl;

public class MVCActivity extends BaseActivity implements OnRequestListener<String>, View.OnClickListener {

    IWeatherModel weatherModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvc);
        weatherModel = new WeatherModelImpl();
        findViewById(R.id.getdata).setOnClickListener(this);
    }

    @Override
    public void onSuccess(String data) {
        Log.e("Andy", "data = " + data);
        ((TextView)findViewById(R.id.showdata)).setText(data);
    }

    @Override
    public void onFailure(int code) {
        Log.e("Andy", "error code = " + code);
    }

    @Override
    public void onClick(View view) {
        weatherModel.getWeater("1", this);
    }
}
