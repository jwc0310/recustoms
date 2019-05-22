package com.andy.recustomviews.activity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andy.recustomviews.R;
import com.andy.recustomviews.tools.BitmapUtils;
import com.andy.recustomviews.tools.DevicesInfoUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;

public class SetWrapperActivity extends Base2Activity {

    @BindView(R.id.set_wrapper1)
    Button setWrapper1;
    @BindView(R.id.set_wrapper2)
    Button setWrapper2;
    @BindView(R.id.set_wrapper3)
    Button setWrapper3;
    @BindView(R.id.set_wrapper4)
    Button setWrapper4;
    @BindView(R.id.next_wrapper)
    Button nextWrapper;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            int what = msg.what;
            String tips = (String) msg.obj;

            Log.e("Andy", "msg.what = "+what+", msg.obj = " + tips);

            if (msg.what == 0x9003){
                setSetWrapper3(tips);
            }
        }
    };

    private WallpaperManager wallpaperManager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_wrapper;
    }

    @Override
    public void init(Bundle bundle) {
        wallpaperManager = WallpaperManager.getInstance(this);
    }

    private void setSetWrapper1(){
        try {
            Resources res = getResources();
            Bitmap bitmap = BitmapFactory.decodeResource(res, getResources().getIdentifier("recustomviews_02", "drawable", getPackageName()));
            wallpaperManager.setBitmap(bitmap);
            Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setSetWrapper2(){
        try {
            wallpaperManager.setResource(getResources().getIdentifier("recustomviews_01", "drawable", getPackageName()));
            Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setSetWrapper3(String path){
//        String path = "/sdcard/Pictures/recustomviews_03.png";
        try {
            File file = new File(path);
            if (file.exists()&&!file.isDirectory()){
                InputStream is = new FileInputStream(new File(path));
                setWallpaper(is);
                is.close();
                Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
            }else {
                Log.e("Andy", "not find picture");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setWallpaper(InputStream inputStream) throws IOException{
        super.setWallpaper(inputStream);
        Toast.makeText(SetWrapperActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
    }
    private void selApks(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/vnd.android.package-archive");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,0x111);
    }

    @OnClick({R.id.set_wrapper1, R.id.set_wrapper2, R.id.set_wrapper3, R.id.next_wrapper, R.id.set_wrapper4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.set_wrapper1:
                getWallpaperPath();
                break;
            case R.id.set_wrapper2:
                DevicesInfoUtils.getResolution(context);
                Log.e("Andy", DevicesInfoUtils.getResolution(context));
                break;
            case R.id.set_wrapper3:
                selApks();
                break;
            case R.id.set_wrapper4:
                Intent chooseIntent = new Intent(Intent.ACTION_SET_WALLPAPER);
                startActivity(Intent.createChooser(chooseIntent, "选择壁纸"));
                break;
            case R.id.next_wrapper:
                try {
                    wallpaperManager.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void getWallpaperPath(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String configName = "xyfConfig";
                try {
                    File file = new File("/sdcard/Pictures/XYFWallpaper");
                    if (!file.exists() || !file.isDirectory()){
                        file.mkdirs();
                    }else {
                        String[] children = file.list(new FilenameFilter() {
                            @Override
                            public boolean accept(File file, String s) {
                                return s.toLowerCase().endsWith(".png")
                                        || s.toLowerCase().endsWith(".gif")
                                        || s.toLowerCase().endsWith(".jpg")
                                        || s.equals(configName);
                            }
                        });
                        File tmp;
                        for (int i = 0; i<children.length; i++){
                            tmp = new File(file, children[i]);
                            if (tmp.exists()&&!tmp.isDirectory()){
                                tmp.delete();
                            }
                        }
                    }

                    Socket socket = new Socket("127.0.0.1", 21505);
                    OutputStream os = socket.getOutputStream();
                    PrintWriter pw = new PrintWriter(os);
                    pw.write("setWallpaper");
                    pw.flush();
                    pw.close();
                    os.close();
                    socket.close();

                    File configFile = new File(file, configName);
                    while (true){
                        if (configFile.exists() && !configFile.isDirectory()){
                            Thread.sleep(50);

                            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(configFile), "gb2312"));
                            String json = "";
                            String info;
                            while ((info = br.readLine()) != null){
                                json += info;
                            }
                            br.close();
                            Log.e("Andy", json);
                            if (json.trim().equals("")){
                                continue;
                            }
                            Message msg = handler.obtainMessage();
                            JSONObject object = new JSONObject(json);
                            int status = object.optInt("status", 0);
                            if (status != 1){
                                msg.what = 0x9001;
                                msg.obj = "取消设置";
                                handler.sendMessage(msg);
                                break;
                            }

                            String fileName = object.optString("fileName");
                            if (fileName == null || fileName.equals("")){
                                msg.what = 0x9002;
                                msg.obj = "文件不存在";
                                handler.sendMessage(msg);
                                break;
                            }
                            File sourceFile = new File(file, fileName);
                            if (!sourceFile.exists() || sourceFile.isDirectory()){
                                msg.what = 0x9002;
                                msg.obj = "文件不存在";
                                handler.sendMessage(msg);
                                break;
                            }
//                            copyFile(sourceFile.getAbsolutePath(), file.getParent()+File.separator+fileName);
                            Bitmap bm = BitmapFactory.decodeStream(new FileInputStream(sourceFile));
                            if (bm == null){
                                Log.e("Andy", "decodeStream fail");
                                return;
                            }
                            Log.e("Andy", "w1 x h1 = "+bm.getWidth()+"x"+bm.getHeight());
                            int nw, nh;
                            int sw, sh;
                            sw = DevicesInfoUtils.getScreenWidth(context);
                            sh = DevicesInfoUtils.getScreenHeight(context);
                            if (bm.getWidth() > bm.getHeight()){
                                if (sw < sh){
                                    sw = DevicesInfoUtils.getScreenHeight(context);
                                    sh = DevicesInfoUtils.getScreenWidth(context);
                                }
                            }else {
                                if (sw > sh){
                                    sw = DevicesInfoUtils.getScreenHeight(context);
                                    sh = DevicesInfoUtils.getScreenWidth(context);
                                }
                            }
                            Log.e("Andy", "sw x sh = "+sw+"x"+sh);
                            if (sw >= sh * bm.getWidth() / bm.getHeight()){
                                nw = sw;
                                nh = bm.getHeight() * sw / bm.getWidth();
                            }else {
                                nw = bm.getWidth() * sh / bm.getHeight();
                                nh = sh;
                            }

                            Log.e("Andy", "nw x nh = "+nw+"x"+nh);
                            Bitmap bm1 = BitmapUtils.scaleImage(bm, nw, nh);
                            if (bm1 == null){
                                Log.e("Andy", "scale image fail");
                                return;
                            }
                            Log.e("Andy", "w2 x h2 = "+bm1.getWidth()+"x"+bm1.getHeight());

                            Bitmap bm3 = BitmapUtils.imageCrop(bm1, sw, sh, false);
                            if (bm3 == null){
                                Log.e("Andy", "crop image fail");
                                return;
                            }
                            Log.e("Andy", "w3 x h3 = "+bm3.getWidth()+"x"+bm3.getHeight());
                            if (BitmapUtils.saveBmpToSd(file.getParent(), bm3, fileName, 100, true)){
                                msg.what = 0x9003;
                                msg.obj = file.getParent()+File.separator+fileName;
                                handler.sendMessage(msg);
                            }else {
                                Log.e("Andy", "save fail");
                            }

                            if (file.exists() && file.isDirectory()){
                                String[] children = file.list(new FilenameFilter() {
                                    @Override
                                    public boolean accept(File file, String s) {
                                        return s.toLowerCase().endsWith(".png")
                                                || s.toLowerCase().endsWith(".gif")
                                                || s.toLowerCase().endsWith(".jpg")
                                                || s.equals(configName);
                                    }
                                });
                                File tmp;
                                for (int i = 0; i<children.length; i++){
                                    tmp = new File(file, children[i]);
                                    if (tmp.exists()&&!tmp.isDirectory()){
                                        tmp.delete();
                                    }
                                }
                            }
                            if (bm != null && !bm.isRecycled()){
                                bm.recycle();
                                bm = null;
                            }
                            if (bm1 != null && !bm1.isRecycled()){
                                bm1.recycle();
                                bm1 = null;
                            }
                            if (bm3 != null && !bm3.isRecycled()){
                                bm3.recycle();
                                bm3 = null;
                            }
                            break;
                        }
                        Thread.sleep(100);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(0x9004);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(0x9004);
                }catch (JSONException e){
                    e.printStackTrace();
                    handler.sendEmptyMessage(0x9004);
                }
            }
        }).start();
    }

    /**
     *  复制单个文件
    * @param oldPath String 原文件路径 如：c:/fqf.txt
    * @param newPath String 复制后路径 如：f:/fqf.txt
    * @return boolean
    */

    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }

    /**
     * 复制整个文件夹内容
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public void copyFolder(String oldPath, String newPath) {

        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a=new File(oldPath);
            String[] file=a.list();
            File temp=null;
            for (int i = 0; i < file.length; i++) {
                if(oldPath.endsWith(File.separator)){
                    temp=new File(oldPath+file[i]);
                }
                else{
                    temp=new File(oldPath+File.separator+file[i]);
                }

                if(temp.isFile()){
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ( (len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if(temp.isDirectory()){//如果是子文件夹
                    copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);
                }
            }
        }
        catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();

        }

    }
}
