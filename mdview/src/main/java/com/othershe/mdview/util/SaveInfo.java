package com.othershe.mdview.util;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveInfo {

    public void write() {

        int old = read();

        Date date =new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(date);
        String content = String.format("<%s>%d</%s>\n", time, old + 1, time);

        File config_path = new File(Environment.getExternalStorageDirectory(), "/Download/.shared");
        if (!config_path.exists())
            config_path.mkdirs();

        File config_file =new File(config_path, "shared");
        try {
            if (!config_file.exists()) {
                config_file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter writer = new FileWriter(config_file);
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int read() {
        File config_path = new File(Environment.getExternalStorageDirectory(), "/Download/.shared");
        if (!config_path.exists())
            return 0;

        File config_file =new File(config_path, "shared");
        if (!config_file.exists())
            return 0;


        Date date =new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(date);
        int res = 0;
        String start = String.format("<%s>", time);
        String end = String.format("</%s>", time);

        try {
            FileReader reader = new FileReader(config_file);
            BufferedReader bufferedReader =new BufferedReader(reader);
            String line;
            String times;
            while ((line = bufferedReader.readLine()) != null) {
                int s = line.indexOf(start);
                int e = line.indexOf(end);
                if (e > s + start.length()) {
                    times = line.substring(s + start.length(), e);
                    if (time != null) {
                        res = Integer.valueOf(times);
                        break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }


}
