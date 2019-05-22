package com.andy.recustomviews.tools;

import android.support.v4.app.FragmentActivity;

import com.andy.recustomviews.fragment.BaseFragment;

/**
 * Created by Administrator on 2017/4/10.
 */
public class FragmentUtils {

    private static BaseFragment tmpFragment = null;

    public static void switchFragment(FragmentActivity activity, int resId, BaseFragment fragment){
        if (tmpFragment == null){
            activity.getFragmentManager().beginTransaction().add(resId, fragment, "").commit();
            tmpFragment = fragment;
            return;
        }
        if (fragment != tmpFragment){
            if (!fragment.isAdded()){
                activity.getFragmentManager().beginTransaction().hide(tmpFragment)
                        .add(resId, fragment).commit();
            }else {
                activity.getFragmentManager().beginTransaction().hide(tmpFragment)
                        .show(fragment).commit();
            }
            tmpFragment = fragment;
        }
    }
}
