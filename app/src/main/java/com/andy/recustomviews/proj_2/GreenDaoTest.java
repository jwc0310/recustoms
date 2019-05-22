package com.andy.recustomviews.proj_2;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.andy.recustomviews.R;
import com.andy.recustomviews.activity.Base2Activity;
import com.andy.recustomviews.proj_2.eg2.UserDao;
import com.andy.recustomviews.proj_2.example.NoteActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GreenDaoTest extends Base2Activity {

    @BindView(R.id.insert)
    Button insert;
    @BindView(R.id.show)
    Button show;
    @BindView(R.id.update)
    Button update;
    @BindView(R.id.del)
    Button del;
    @BindView(R.id.jump)
    Button jump;

    @Override
    public int getLayoutId() {
        return R.layout.green_dao;
    }

    @Override
    public void init(Bundle bundle) {

    }

    @OnClick(R.id.jump)
    protected void jump(){
        startActivity(new Intent(this, NoteActivity.class));
    }

    @OnClick(R.id.show)
    protected void query() {
        List<User> list = getUserDao().loadAll();
        for (int i = 0; i < list.size(); i++) {
            Log.e("Andy", list.get(i).toString());
        }
    }
    @OnClick(R.id.insert)
    protected void insert() {
        try {
            List<User> list = new ArrayList<>();
            for (int i = 0; i < 999; i++) {
                User user1 = new User("id__" + i, 1, "1", "1", "1", "1", 1);
                list.add(user1);
            }
            Log.e("Andy", "start = " + System.currentTimeMillis());
            getUserDao().deleteAll();
            Log.e("Andy", "start2 = " + System.currentTimeMillis());
            getUserDao().insertInTx(list);
            Log.e("Andy", "finish ? = " + System.currentTimeMillis());
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
        }
    }


    private UserDao getUserDao() {
        return GreenDaoManager.getInstance().getmDaoSession().getUserDao();
    }
}
