package com.andy.recustomviews.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.andy.recustomviews.sqlite.ProviderMetaData;

import java.util.HashMap;

/**
 *
 * Created by Administrator on 2017/1/3.
 */
public class PersonContentProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        mDbHelper = new DatabaseHelper(this.getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (sUriMatcher.match(uri)){
            case COLLECTION_INDICATOR:
                // 设置查询的表
                queryBuilder.setTables(ProviderMetaData.TableMetaData.TABLE_NAME);
                // 设置投影映射
                queryBuilder.setProjectionMap(sNotesProjectionMap);
                break;
            case SINGLE_INDICATOR:
                queryBuilder.setTables(ProviderMetaData.TableMetaData.TABLE_NAME);
                queryBuilder.setProjectionMap(sNotesProjectionMap);
                queryBuilder.appendWhere(ProviderMetaData.TableMetaData._ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknow URI: " + uri);
        }
        String orderBy;
        if (TextUtils.isEmpty(sortOrder)){
            orderBy = ProviderMetaData.TableMetaData.DEFAULT_ORDERBY;
        }else {
            orderBy = sortOrder;
        }

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, orderBy);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch(sUriMatcher.match(uri)) {
            case COLLECTION_INDICATOR:
                return ProviderMetaData.TableMetaData.CONTENT_TYPE;

            case SINGLE_INDICATOR:
                return ProviderMetaData.TableMetaData.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("Unknow URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        if (sUriMatcher.match(uri) != COLLECTION_INDICATOR) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long rowID = db.insert(ProviderMetaData.TableMetaData.TABLE_NAME, null, contentValues);

        if(rowID > 0) {
            Uri retUri = ContentUris.withAppendedId(ProviderMetaData.TableMetaData.CONTENT_URI, rowID);
            return retUri;
        }

        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int count = -1;
        switch(sUriMatcher.match(uri)) {
            case COLLECTION_INDICATOR:
                count = db.delete(ProviderMetaData.TableMetaData.TABLE_NAME, s, strings);
                break;

            case SINGLE_INDICATOR:
                String rowID = uri.getPathSegments().get(1);
                count = db.delete(ProviderMetaData.TableMetaData.TABLE_NAME, ProviderMetaData.TableMetaData._ID + "=" + rowID, null);
                break;

            default:
                throw new IllegalArgumentException("Unknow URI :" + uri);

        }
        // 更新数据时，通知其他ContentObserver
        this.getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int count = -1;
        switch(sUriMatcher.match(uri)) {
            case COLLECTION_INDICATOR:
                count = db.update(ProviderMetaData.TableMetaData.TABLE_NAME, contentValues, null, null);
                break;

            case SINGLE_INDICATOR:
                String rowID = uri.getPathSegments().get(1);
                count = db.update(ProviderMetaData.TableMetaData.TABLE_NAME, contentValues, ProviderMetaData.TableMetaData._ID + "=" + rowID, null);
                break;

            default:
                throw new IllegalArgumentException("Unknow URI : " + uri);

        }
        this.getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
    private static final UriMatcher sUriMatcher;
    private static final int COLLECTION_INDICATOR = 1;
    private static final int SINGLE_INDICATOR = 2;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(ProviderMetaData.AUTHORITY, "notes", COLLECTION_INDICATOR);
        sUriMatcher.addURI(ProviderMetaData.AUTHORITY, "notes/#", SINGLE_INDICATOR);
    }

    private static HashMap<String, String> sNotesProjectionMap;
    static {
        sNotesProjectionMap = new HashMap<String, String>();
        sNotesProjectionMap.put(ProviderMetaData.TableMetaData._ID, ProviderMetaData.TableMetaData._ID);
        sNotesProjectionMap.put(ProviderMetaData.TableMetaData.NOTE_CONTENT, ProviderMetaData.TableMetaData.NOTE_CONTENT);
        sNotesProjectionMap.put(ProviderMetaData.TableMetaData.NOTE_TITLE, ProviderMetaData.TableMetaData.NOTE_TITLE);
        sNotesProjectionMap.put(ProviderMetaData.TableMetaData.CREATE_DATE, ProviderMetaData.TableMetaData.CREATE_DATE);
    }

    private DatabaseHelper mDbHelper;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context){
            super(context, ProviderMetaData.DATABASE_NAME, null, ProviderMetaData.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.e("DatabaseHelper", "create table: " + ProviderMetaData.TableMetaData.SQL_CREATE_TABLE);
            db.execSQL(ProviderMetaData.TableMetaData.SQL_CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ProviderMetaData.TableMetaData.TABLE_NAME);
            onCreate(db);
        }
    }
}
