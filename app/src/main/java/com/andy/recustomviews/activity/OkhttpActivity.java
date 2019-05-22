package com.andy.recustomviews.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.andy.recustomviews.R;
import com.andy.recustomviews.tools.OkhttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/29.
 */
public class OkhttpActivity extends BaseActivity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            Log.e("Andy", "access");
            getData();
        }
    };


    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_okhttp);
        handler.sendEmptyMessageDelayed(0x0, 3000);
    }

    private void getData(){
        OkhttpUtils.accessBaidu(context, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response != null){
                    String string = response.networkResponse().toString();
                    Log.e("Andy", "network ---- "+string);
                    final String packageName = getPackageName(string);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (packageName.length() > 0){
                                Toast.makeText(OkhttpActivity.this, "请求成功!", Toast.LENGTH_SHORT).show();
                                Uri uri = Uri.parse("market://details?"
                                        + packageName);
                                startActivity(new Intent(
                                        Intent.ACTION_VIEW, uri));
                            }else {
                                Toast.makeText(OkhttpActivity.this, "解析问题", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

        });
    }

    private String getPackageName(String origin){
        String packageName = "";
        String[] sp1 = origin.split("\\{");
        for (String str : sp1){
            System.out.println(str);
        }
        String[] sp3 = sp1[1].split("\\}")[0].split(",");
        String url = "";
        for (String str : sp3){
            System.out.println(str.trim());
            if (str.trim().startsWith("url=")){
                url = str.trim();
                break;
            }
        }

        if (url.length() != 0){

            String[] paths = url.split("\\?")[1].split("&");
            String path = url.split("\\?")[1];
            if (path.contains("id=") && path.contains("referrer="))
                packageName = path;
        }
        return packageName;
    }

    private void postData(){
        OkhttpUtils.postUrl(context, "size", "10", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("Andy", "failed");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("Andy", "success");
                Log.e("Andy", str);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "请求成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
