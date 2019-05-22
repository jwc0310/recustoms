package com.andy.recustomviews.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.andy.recustomviews.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * Created by Administrator on 2017/1/6.
 */
public class ProviderActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_provider);
        findViewById(R.id.query).setOnClickListener(this);
        findViewById(R.id.insert).setOnClickListener(this);
        findViewById(R.id.update).setOnClickListener(this);
        findViewById(R.id.delete).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (R.id.query == id){
            query();
        }else if (R.id.insert == id){
            insert();
        }else if (R.id.update == id){
            update();
        }else if (R.id.delete == id){
            delete();
        }
    }

    public static final String AUTHORITY = "com.andy.person.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/notes");

    private void query(){
        Cursor cursor = this.getContentResolver().query(CONTENT_URI, null, null, null, null);
        Log.e("Andy", "count = " + cursor.getCount());

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            long createDate = cursor.getLong(cursor.getColumnIndex("create_date"));
            Date date = new Date(createDate);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = simpleDateFormat.format(date);
            Log.e("Andy", "title = "+title+", content = "+content+", time = "+time);

            cursor.moveToNext();
        }
        cursor.close();
    }
    private void insert(){
        ContentValues values = new ContentValues();
        values.put("title", "hello");
        values.put("content", "my name is andy");

        long time = System.currentTimeMillis();
        values.put("create_date", time);

        Uri uri = this.getContentResolver().insert(CONTENT_URI, values);
        Log.e("Andy", "insert " + uri.toString());
    }
    private void update(){
        ContentValues values = new ContentValues();
        values.put("content", "Andy is my name !!!!!!!!!!");
        int count = this.getContentResolver().update(CONTENT_URI, values, "_id=1", null);
        Log.e("Andy", "count = " + count);
        query();
    }
    private void delete(){
        int count = this.getContentResolver().delete(CONTENT_URI, "_id=1", null);
        Log.e("Andy", "count = " + count);
        query();
    }
}
