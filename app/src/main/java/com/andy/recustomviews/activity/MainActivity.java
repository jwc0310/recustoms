package com.andy.recustomviews.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andy.recustomviews.R;
import com.andy.recustomviews.activity.eventbus.EventBusFirstActivity;
import com.andy.recustomviews.proj_1.MainActivity_proj1;
import com.andy.recustomviews.proj_2.GreenDaoTest;
import com.andy.recustomviews.recycler.FullyGridLayoutManager;
import com.andy.recustomviews.rxjava2.RxjavaActivity;
import com.andy.recustomviews.service.ShotterService;
import com.andy.recustomviews.tools.Shotter;
import com.andy.recustomviews.views.XYFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity extends BaseActivity {
    private String TAG = "Andy";
    private Context context;
    private RecyclerView recyclerView;
    private Adapter adapter;

    private String[] content = new String[] {
      "水平", "DRAG", "QQ", "SCROLL", "ACTION_MODE",
            "OKHTTP3", "服务页面", "PROVIDER", "broswer",
            "MVC", "OpenGL", "gldemo", "点9", "proj-1",
            "proj-2", "壁纸", "EventBus", "View", "fileChoose",
            "MVP", "Canvas", "vertical", "Drawer", "OpenGLLight",
            "OpenGLTexture", "GLBitmapActivity", "Rxjava23", "自定义ViewGroup", "Rxjava",
            "摇一摇"
    };

    private String[] packages = new String[] {
            "HorActivity", "MoveActivity", "QqActivity", "ScrollViewActivity", "ActionModeActivity",
            "OkhttpActivity", "ServiceActivity", "ProviderActivity", "BroswerActivity",
            "MVCActivity", "OpenGLESActivity", "DrawPoint", "Point9Test", "MainActivity_proj1",
            "GreenDaoTest", "SetWrapperActivity", "EventBusFirstActivity", "ViewActivity", "FileChooserExampleActivity",
            "MVPActivity", "CanvasActivity", "VerticalActivity", "Drawer", "OpenGLLight",
            "OpenGLTexture", "GLBitmapActivity", "Rxjava23Activity"
    };

    private Class[] classes = new Class[] {
            HorActivity.class, MoveActivity.class, QqActivity.class, ScrollViewActivity.class, ActionModeActivity.class,
            OkhttpActivity.class, ServiceActivity.class, ProviderActivity.class, BroswerActivity.class,
            MVCActivity.class, OpenGLESActivity.class, DrawPoint.class, Point9Test.class, MainActivity_proj1.class,
            GreenDaoTest.class, SetWrapperActivity.class, EventBusFirstActivity.class, ViewActivity.class, FileChooserExampleActivity.class,
            MVPActivity.class, CanvasActivity.class, VerticalActivity.class, Drawer.class, OpenGLLight.class,
            OpenGLTexture.class, GLBitmapActivity.class, Rxjava23Activity.class, MyViewGroup.class, RxjavaActivity.class,
            Yaoyiyao.class
    };

    private List<String> list;
    private List<String> list_packages;
    private XYFlowLayout flowLayout;

    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
//        StatusBarCompat.transparent(this);


        list = Arrays.asList(content);
        list_packages = Arrays.asList(packages);
        initViews();
        flowLayout = (XYFlowLayout) findViewById(R.id.flowLayout);
        initFlowLayout(flowLayout);

        fortest();

        String url = "https://play.google.com/store/apps/details?id=com.entermate.luthiel";
        Uri uri = Uri.parse(url);
        System.out.println(uri.getHost());
        System.out.println(uri.getAuthority());
        System.out.println(uri.getPath());
        System.out.println(uri.getEncodedAuthority());
        System.out.println(uri.getQuery());
        System.out.println(uri.getQueryParameter("id"));
        System.out.println(uri.toString());


        if (uri.getHost().equals("play.google.com") && uri.getPath().equals("/store/apps/details")){
            Log.e("Andy", "xxxxxx");
        }
    }

    private void fortest(){
        LayoutInflater inflater = LayoutInflater.from(context);
        LayoutInflater inflater1 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private int toPx(Context context, float dp){
        return (int)(context.getResources().getDisplayMetrics().density * dp + 0.5);
    }

    private void initFlowLayout(XYFlowLayout xyFlowLayout){
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.rightMargin = toPx(this, 15);
        lp.topMargin = toPx(this, 5);
        lp.bottomMargin = toPx(this, 5);

        for(int i = 0; i < list.size(); i++){

            final TextView textView1 = new TextView(this);
            textView1.setText(list.get(i));
            textView1.setBackground(getResources().getDrawable(R.drawable.xy_flow_bg_selector));
            xyFlowLayout.addView(textView1, lp);
            final int position = i;
            textView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(context, classes[position]));
                }
            });
        }
    }

    private void initViews(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new FullyGridLayoutManager(this, 3){
            @Override
            public boolean canScrollVertically(){
                return false;
            }
        });
        adapter = new Adapter(context, list, list_packages);
        recyclerView.setAdapter(adapter);

        final EditText editText = findViewById(R.id.edit_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e("editext", "before = " + s.toString() +", " + start+", " + count+", " + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("editext", "on = " + s.toString() +", " + start+", " + count+", " + count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("editext", "after = " + s.toString());

            }
        });

        findViewById(R.id.launcher3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shortCutShows();
            }
        });

        findViewById(R.id.all_apps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllApps();
            }
        });

        findViewById(R.id.sendBroadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Andy", "send b");
                Intent intent = new Intent();
                intent.setAction("com.andy.recustomviews.receiver.mybroadcastreceiver");
                intent.putExtra("data", "hello receiver");
                sendBroadcast(intent);
            }
        });

        findViewById(R.id.wifi_rel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWifiInfo();
            }
        });

        findViewById(R.id.app_screencap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestScreenShot();
            }
        });

        findViewById(R.id.app_screencap2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shotter.screenShot();
            }
        });

        findViewById(R.id.app_debug).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pkgName = editText.getText().toString().trim();
                if (pkgName.length() <= 0) {
                    return;
                }

                showAppDebug(pkgName);
            }
        });
    }

    public static final int REQUEST_MEDIA_PROJECTION = 0x2893;

    @SuppressLint("NewApi")
    public void requestScreenShot() {
        if (Build.VERSION.SDK_INT >= 21) {
            startActivityForResult(
                    ((MediaProjectionManager) getSystemService("media_projection")).createScreenCaptureIntent(),
                    REQUEST_MEDIA_PROJECTION);
        } else {
            finish();
        }
    }

    private void showAppDebug(String packageName) {
        PackageManager pm = getPackageManager();

        try {
            PackageInfo pkgInfo = pm.getPackageInfo(packageName, 1);
            if (pkgInfo != null) {
                ApplicationInfo info = pkgInfo.applicationInfo;
                boolean isDebuggable = ((info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
                TextView textView = findViewById(R.id.main_context);
                textView.setText(packageName + (isDebuggable ? " is debuggable" : "not debuggable"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showWifiInfo() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        Log.e("Andy wifi", connectionInfo.toString());
        Log.e("Andy wifi", "network id = " + connectionInfo.getNetworkId());
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        Log.e("Andy wifi", "size = " + configuredNetworks.size());
        for (WifiConfiguration conf : configuredNetworks) {
            Log.e("Andy wifi","\n conf = " + conf.toString() +", [network id] = " + conf.networkId);
        }
    }

    private class AdapterViewHolder extends RecyclerView.ViewHolder{
        private TextView  textView;
        private LinearLayout linearLayout;
        public AdapterViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.main_item_tv);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.main_item_ll);
        }
    }

    private class Adapter extends RecyclerView.Adapter<AdapterViewHolder>{

        private Context context;
        private List<String> list;
        private List<String> list_packages;
        private LayoutInflater inflater;

        public Adapter(Context context, List<String> list, List<String> list_packages){
            this.context = context;
            this.list = list;
            this.list_packages = list_packages;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AdapterViewHolder(inflater.inflate(R.layout.item_main, parent, false));
        }

        @Override
        public void onBindViewHolder(AdapterViewHolder holder, final int position) {
            holder.textView.setText(list.get(position).trim());
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        startActivity(new Intent(context, Class.forName("com.andy.recustomviews.activity."+list_packages.get(position))));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
    private void getAllApps(){
        /**
         * 同样，以ACTION_MAIN和CATEGORY_LAUNCHER作为过滤条件
         * 查询所有符合的应用信息(ResolveInfo)
         * ResolveInfo中就包含了我们需要的主要信息。
         * 当然还有其他的方式获取应用信息，如PackageInfo,关于这些信息,可以参看
         * http://android.tgbus.com/Android/tutorial/201108/364210.shtml
         */

        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final PackageManager pManager = this.getPackageManager();
        /**
         * queryIntentActivities通过解析所有应用程序中含有如下Intent-filter的App
         * <intent-filter>
         *      <action android:name="android.intent.action.MAIN" />
         *      <category android:name="android.intent.category.LAUNCHER" />
         * </intent-filter>
         */
        final List<ResolveInfo> infoList = pManager.queryIntentActivities(mainIntent, 0);
        if(infoList != null){
            for(ResolveInfo info : infoList){
            }
        }
    }

    private void shortCutShows(){
        Resources res = getResources();
        Bundle bundle = new Bundle();

        ArrayList<String> shortcutNames = new ArrayList<>();
        shortcutNames.add(res.getString(R.string.group_application));
        shortcutNames.add("其他");

        bundle.putStringArrayList(Intent.EXTRA_SHORTCUT_NAME, shortcutNames);

        ArrayList<Intent.ShortcutIconResource> shortcutIcons = new ArrayList<>();
        shortcutIcons.add(Intent.ShortcutIconResource.fromContext(this, R.drawable.recustomviews_01));
        shortcutIcons.add(Intent.ShortcutIconResource.fromContext(this, R.drawable.recustomviews_01));

        bundle.putParcelableArrayList(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, shortcutIcons);
        Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
        pickIntent.putExtra(Intent.EXTRA_INTENT,
                new Intent(Intent.ACTION_CREATE_SHORTCUT));
        pickIntent.putExtra(Intent.EXTRA_TITLE,
                res.getString(R.string.title_select_app));
        //将附加信息加入到pickIntent
        pickIntent.putExtras(bundle);

        startActivityForResult(pickIntent, 0x0);

    }
    private void addShortcut(Intent data){

        if (data == null)
            return;

        Resources res = getResources();

        final String appName = res.getString(R.string.group_application);
        final String shortcutName = data.getStringExtra(Intent.EXTRA_SHORTCUT_NAME);
        if(shortcutName != null && appName.equals(shortcutName)){
            //说明用户选择的是应用程序,进入应用程序列表
            Intent mainIntent = new Intent(Intent.ACTION_MAIN,null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
            pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
            startActivityForResult(pickIntent, 0x1);
        }else{
            //否则，直接创建快捷方式
            startActivityForResult(data, 0x2);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 0x0){
            addShortcut(data);
            return;
        }
        switch (requestCode) {
            case REQUEST_MEDIA_PROJECTION: {
                if (resultCode == -1 && data != null) {
                    MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
                    Shotter.mMediaProjection = mediaProjectionManager.getMediaProjection(Activity.RESULT_OK, data);

                    Handler handler = new Handler(getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, ShotterService.class);
                            startService(intent);
                        }
                    }, 2000);
                }
            }
        }
    }

    private void init(){
        SparseArray sparseArray=new SparseArray<String>();

        //增加的两种方式
        sparseArray.append(0, "This is 0");
        sparseArray.append(1, "This is 1");
        sparseArray.append(2, "This is 2");

        sparseArray.put(3, "This is 3");
        sparseArray.put(4, "This is 4");

        //遍历
        for (int i = 0; i < sparseArray.size(); i++) {
            System.out.println("遍历得到位置"+i+"的值为:"+sparseArray.get(i));
        }

        //查找某个位置的键
        int key =sparseArray.keyAt(1);
        System.out.println("查找位置1处的键 key="+key);

        //查找某个位置的值
        String value=(String) sparseArray.valueAt(1);
        System.out.println("查找位置1处的值 value="+value);

        //修改的两种方式
        sparseArray.put(0, "This is new 0");
        sparseArray.put(1, "This is new 1");
        sparseArray.setValueAt(2, "This is new 2");
        sparseArray.setValueAt(3, "This is new 3");
        for (int i = 0; i < sparseArray.size(); i++) {
            System.out.println("修改后遍历得到位置"+i+"的值为:"+sparseArray.get(i));
        }

        //删除
        sparseArray.delete(0);
        System.out.println("删除操作后sparseArray大小 size="+sparseArray.size());
        //注意:
        //在执行删除后sparseArray的size()减小了1
        //为了遍历完,应该将循环条件修改为i < sparseArray.size()+1
        //HashMap也有类似的情况.参见分割线以下的例子
        //如果关于SparseArray的遍历有什么好的方法或者建议,多谢
        for (int i = 0; i < sparseArray.size()+1; i++) {
            System.out.println("删除后遍历得到位置"+i+"的值为:"+sparseArray.get(i));
        }

        System.out.println("//////////////这是分割线////////////////");

        HashMap<Integer, String> hashMap=new HashMap<Integer, String>();
        hashMap.put(0, "000");
        hashMap.put(1, "111");
        hashMap.put(2, "222");
        hashMap.put(3, "333");
        hashMap.put(4, "444");
        for (int i = 0; i < hashMap.size(); i++) {
            System.out.println("HashMap遍历得到位置"+i+"的值为:"+hashMap.get(i));
        }

        hashMap.remove(Integer.valueOf(0));
        System.out.println("删除操作后hashMap大小 size="+hashMap.size());
        //注意:
        //在执行删除后hashMap的size()减小了1
        //为了遍历完,应该将循环条件修改为i < hashMap.size()+1
        for (int i = 0; i < hashMap.size()+1; i++) {
            System.out.println("HashMap遍历得到位置"+i+"的值为:"+hashMap.get(i));
        }

        //但是这样做是意义不大的,我们常用的是利用keySet来遍历,如下:
        Set<Integer> set = hashMap.keySet();
        for (Iterator<Integer> iter = set.iterator(); iter.hasNext();) {
            Integer keyTemp = iter.next();
            String valueTemp = hashMap.get(keyTemp);
            System.out.println("利用keySet遍历:"+keyTemp + "的值是" + valueTemp);
        }

    }

}
