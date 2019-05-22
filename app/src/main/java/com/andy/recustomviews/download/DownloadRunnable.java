package com.andy.recustomviews.download;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * Created by Administrator on 2017/1/3.
 */
public class DownloadRunnable implements Runnable {
    private static final String TAG = DownloadRunnable.class.getSimpleName();

    private String url;
    private String tag;
    public DownloadRunnable(String url, String tag){
        this.url = url;
        this.tag = tag;
    }
    private static final int tryTimes = 5;
    @Override
    public void run() {
        do{
            RandomAccessFile raf;
            HttpURLConnection conn;
            InputStream is;
            try {
                raf = createDownloadFile();
                conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);
                conn.setUseCaches(true);
                conn.connect();
                //TODO 是否已经下载， 如果已经下载设置Range
    //            if (getUrl(url).size != 0){
    //                conn.setRequestProperty("Range", "bytes="+getUrl(url).size+"-");
    //            }
                is = conn.getInputStream();
                byte[] buffer = new byte[1024];
                int count;
                while((count = is.read(buffer))!= -1){
                    raf.write(buffer, 0, count);
                }
                is.close();
                raf.close();
                conn.disconnect();
                Log.e(TAG, "url = "+url+", tag = "+tag);
                Log.e(TAG, "exit "+tag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }while(true);
    }

    private RandomAccessFile createDownloadFile() throws IOException{
        String fileName = getFileNameByUrl(url, tag);
        File file = new File(Environment.getExternalStorageDirectory().getPath(), tag);

        if (!file.getParentFile().isDirectory() && !file.getParentFile().mkdirs()){
            throw new IOException("cannot create download folder");
        }

        if (file.exists()){

        }
        RandomAccessFile raf = new RandomAccessFile(file, "rwd");
        return raf;
    }

    private String getFileNameByUrl(String url, String name) {
        if (isEmpty(url)) {
            return null;
        }
        if (url.length() < 100) {

            int index = url.lastIndexOf('?');
            int index2 = url.lastIndexOf("/");
            if (index > 0 && index2 >= index) {
                return UUID.randomUUID().toString();
            }
            return url.substring(index2 + 1, index < 0 ? url.length() : index);
        }else{
            return name+UUID.randomUUID().toString();
        }
    }

    private boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }
}
