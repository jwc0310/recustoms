package com.andy.frameworks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.andy.frameworks.bean.GitRepo;
import com.andy.frameworks.network.ApiManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.request_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<List<GitRepo>> call = ApiManager.getInstance().getApiService().listRepos("octocat");
                call.enqueue(new Callback<List<GitRepo>>() {
                    @Override
                    public void onResponse(Call<List<GitRepo>> call, Response<List<GitRepo>> response) {
                        List<GitRepo> list = response.body();
                        if (list == null)
                            return;

                        for (GitRepo repo : list) {
                            Log.e("MainActivity", repo.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<GitRepo>> call, Throwable t) {

                    }
                });
            }
        }).start();
    }
}
