package com.andy.xyfloatingball.floating;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * http: get post
 * Created by Administrator on 2017/1/13.
 */
public class Http {

    public static String get(String addr) {
        try{
            URL url = new URL(addr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setRequestProperty("Charset", "utf-8");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream inputStream = urlConnection.getInputStream();
                //创建字节输出流对象
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                int len = 0;
                byte buffer[] = new byte[1024];
                while ((len = inputStream.read(buffer, 0, 1024)) != -1){
                    outputStream.write(buffer, 0, len);
                }
                outputStream.close();
                inputStream.close();
                return new String(outputStream.toByteArray());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static String post(String u, List<NameValuePair> params) {
        try {
            String encode = "utf-8";
            final byte[] data = getRequestData(params, encode).toString().getBytes();
            URL url = new URL(u);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(data);
            out.flush();
            out.close();
            int response = connection.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream inptStream = connection.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] data2 = new byte[1024];
                int len;
                try {
                    while ((len = inptStream.read(data2)) != -1) {
                        byteArrayOutputStream.write(data2, 0, len);
                    }
                } catch (IOException e) {
                }
                return new String(byteArrayOutputStream.toByteArray());

            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static StringBuffer getRequestData(List<NameValuePair> params, String encode) {
        StringBuffer stringBuffer = new StringBuffer(); // 存储封装好的请求体信息
        try {
            for (int i = 0; i < params.size(); i++) {
                stringBuffer.append(params.get(i).getName()).append("=").append(URLEncoder.encode(params.get(i).getValue(), encode)).append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1); // 删除最后的一个"&"
        } catch (Exception e) {
            return stringBuffer;
        }
        return stringBuffer;
    }
}
