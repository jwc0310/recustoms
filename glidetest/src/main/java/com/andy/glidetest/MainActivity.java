package com.andy.glidetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.URL;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Toolbar toolbar;
    private Spinner spinner;
    private List<String> spinner_list;
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Glide Study");
        imageView = (ImageView) findViewById(R.id.imageView);
        loadImage(imageView);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner_list = new ArrayList<>();
        spinner_list.add("1");
        spinner_list.add("2");
        spinner_list.add("3");
        spinner_list.add("4");
        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinner_list);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        int maxMem = (int)Runtime.getRuntime().maxMemory();
        Log.e("Andy", "max memory is "+maxMem/1024/1024+"M");

        Map<String, String> map = new HashMap<>();
        Map<String, String> map1 = new AbstractMap<String, String>() {
            @Override
            public Set<Entry<String, String>> entrySet() {
                return null;
            }
        };


    }

    public void loadImage(View view) {
        String url = "http://img5.imgtn.bdimg.com/it/u=4267222417,1017407570&fm=200&gp=0.jpg";
        String url2 = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1507537231&di=c095dfb800069d577c7f3e403ddad7b3&src=http://attachments.motorfans.com.cn/day_080716/DSC01233_77GuX8dp2B24.jpg";
        Glide.with(this)
                .load(url2)
//                .asBitmap()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.rocket)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(220,220)
                .into(imageView);
    }
}
