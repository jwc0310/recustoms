package com.andy.materialtest.uis;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.andy.materialtest.BuildConfig;
import com.andy.materialtest.bases.BaseActivity;
import com.andy.materialtest.bases.TestEvent;
import com.andy.materialtest.bean.Fruit;
import com.andy.materialtest.adapters.FruitAdapter;
import com.andy.materialtest.R;
import com.andy.materialtest.utils.PhoneInfo;
import com.andy.materialtest.utils.PropertyUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
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
    private SwipeRefreshLayout swipeRefresh;


    @Override
    public int contentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**drawer layout**/
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setLogo(R.mipmap.ic_launcher);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        }

        /**nav view**/
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //// TODO: 2017/2/25 判断item id 做不同的操作
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        /**fab**/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fb_button_event();
                sendBookNotification("xxxx");
            }
        });

        /**Recycler view**/
        initFruits();
        final RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recycler.setLayoutManager(layoutManager);
        adapter = new FruitAdapter(list);
        recycler.setAdapter(adapter);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });
        recycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!swipeRefresh.isRefreshing() && !recycler.canScrollVertically(1)){
                    Log.e("Andy", "is end");
                }
            }
        });
    }

    private void refreshFruits(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(MainActivity.this, "backup", Toast.LENGTH_SHORT).show();
                if (BuildConfig.LOG_DEBUG){
                    Log.e("Andy", "I am debug version");
                }else {
                    Log.e("Andy", "I am release version");
                }
                break;
            case R.id.delete:
                Toast.makeText(MainActivity.this, "delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(MainActivity.this, "setting", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Main3Activity.class));
                break;
            case R.id.test_1:
                Toast.makeText(MainActivity.this, "test-1", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Test1Activity.class));
                break;
            case R.id.test_2:
                Toast.makeText(MainActivity.this, "test-2", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Test2Activity.class));
                break;
            case R.id.test_3:
                Toast.makeText(MainActivity.this, "test-3", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Test3Activity.class));
                break;
            case R.id.test_4:
                Toast.makeText(MainActivity.this, "test-4", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Test4Activity.class));
                break;
            case R.id.test_5:
                Toast.makeText(MainActivity.this, "test-5", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Test5Activity.class));
                break;
            default:
                break;
        }
        return true;
    }

    private void initFruits(){
        list.clear();
        for (int i = 0; i < 50; i++){
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            list.add(fruits[index]);
        }
    }

    private void fb_button_event(){
        /** snack bar **/
        String abi2 = PropertyUtil.getProperty("ro.product.cpu.abi2", "02");
        String abi = PropertyUtil.getProperty("ro.product.cpu.abi", "01");
        Log.e("Andy", "abi = " + abi);
        Log.e("Andy", "abi2 = " + abi2);

        File file = new File("/sys/devices/pci0000:00/0000:00:03.0/virtio0");
        if (file.exists()){
            Log.e("Andy", "exists");
        }else {
            Log.e("Andy", "not exists");
        }

        if (abi.equals("x86") && abi2.equals("armeabi-v7a") && file.exists()){
            Log.e("Andy", " wo shi moniqi");
        }



//                Snackbar.make(view, "Data deleted?", Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener(){
//
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(MainActivity.this, "fab clicked", Toast.LENGTH_SHORT).show();
//                    }
//                }).show();
//                String package_name = "com.android.vending";
//                Intent intent = getPackageManager().getLaunchIntentForPackage(package_name);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);

//                Intent intent =  new Intent();
//                intent.setAction("android.intent.action.MAIN");
//                intent.setClassName("com.android.settings", "com.android.settings.ManageApplications");
//                startActivity(intent);
    }

    int bookCount = 0;
    public void sendBookNotification(String gameName) {

        PhoneInfo.getPhoneInfo(this);

        String title = getString(R.string.app_name);
        String contentText = "您预约的&lt;%1$s&gt;已上线，快来抢鲜体验";
        contentText = String.format(contentText, gameName);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setTicker(contentText)
                .setContentTitle(title)
                .setContentText(contentText)
                .build();

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(bookCount++, notification);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(TestEvent event) {
        Log.e("Main", event.toString());
    }
}
