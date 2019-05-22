package com.andy.recustomviews.proj_2;

import com.andy.recustomviews.activity.ReApplication;
import com.andy.recustomviews.proj_2.eg2.DaoMaster;
import com.andy.recustomviews.proj_2.eg2.DaoSession;

/**
 * Created by Administrator on 2017/4/22.
 */
public class GreenDaoManager
{
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private static GreenDaoManager instance;

    private GreenDaoManager()
    {
        if (instance == null) {
            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(ReApplication.getContext(), "NOTE.db");
            mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
            mDaoSession = mDaoMaster.newSession();
        }
    }

    /**
     * 对外唯一实例的接口
     *
     * @return
     */
    public static GreenDaoManager getInstance()
    {
        if (instance == null){
            synchronized (GreenDaoManager.class){
                if (instance == null){
                    instance = new GreenDaoManager();
                }
            }
        }
        return instance;
    }
    public DaoMaster getmDaoMaster()
    {
        return mDaoMaster;
    }
    public DaoSession getmDaoSession()
    {
        return mDaoSession;
    }
    public DaoSession getNewSession()
    {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }
}