package com.andy.recustomviews.proj_1.fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.andy.recustomviews.R;
import com.andy.recustomviews.fragment.BaseFragment;
import com.andy.recustomviews.ndk.MyJni;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by Administrator on 2017/4/10.
 */
public class MainFragment extends BaseFragment {

    @BindView(R.id.button_0)
    Button button0;
    @BindView(R.id.button_1)
    Button button1;
    @BindView(R.id.button_2)
    Button button2;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_proj1;
    }

    @Override
    public void init(Bundle bundle) {

    }

    @OnClick(R.id.button_0)
    public void onShortClick(){
        MyJni myJni = new MyJni();
        Log.e("Andy", myJni.displayJni());
        Log.e("Andy", myJni.xyUpdate("Andy ", "Chen"));
        int[] rs = myJni.getIntArray(10);
        Log.e("Andy", IntArrayToString(rs));
        Log.e("Andy", "strcat = "+myJni.strConcat("xxxx_","yyyy"));
    }

    @OnLongClick(R.id.button_1)
    public boolean onLongClick(){
        Toast.makeText(mActivity, "is a long click", Toast.LENGTH_SHORT).show();
        return true;
    }

    private String IntArrayToString(int[] ints){
        StringBuilder str = new StringBuilder();
        str.append('[');
        for (int i : ints){
            str.append(i);
            str.append(',');
        }
        str.deleteCharAt(str.length()-1);
        str.append(']');
        return str.toString();
    }
}
