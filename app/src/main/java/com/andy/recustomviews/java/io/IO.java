package com.andy.recustomviews.java.io;

import android.support.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

/**
 * Created by Andy on 2017/8/31.
 *
 * 字节流，就是数据流中最小的数据单元是8位的字节。
 * 字符流，就是数据流中最小的数据单元是16位的字符。
 *
 * 字节流在操作的时候，不会用到缓冲；而字符流会用到缓冲。所以，字符流的效率会更高一些。
 * 至于为什么用到缓冲会效率更高一些呢？那是因为，缓冲本质上是一段内存区域；
 * 而文件大多是存储在硬盘或者Nand Flash上面。读写内存的速度比读写硬盘或Nand Flash上文件的速度快很多！
 * 目前，文件大多以字节的方式存储的。所以在开发中，字节流使用较为广泛。
 */

public class IO {

    public static void main(String[] args) throws IOException {

        try {

            File f=new File("d:123.txt");

            OutputStream out = new BufferedOutputStream(new FileOutputStream(f));

            String str="hello Andy !";

            byte[]b=str.getBytes();
            for(int i = 0; i < b.length; i++) {
                out.write(b[i]);
            }
            out.close();

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (SecurityException e){
            e.printStackTrace();
        }finally {

        }

    }
}
