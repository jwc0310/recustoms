package com.andy.recustomviews.java.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * 两台计算机通信：IP地址, 协议, 端口号
 *      TCP：Transmission Control Protocol 传输控制协议
 *      IP：Internet Protocol 互联网协议
 *
 * TCP/IP五层模型
 *      应用层：HTTP、FTP、SMTP、Telnet等
 *      传输层：TCP/IP
 *      网络层：
 *      数据链路层：
 *      物理层：网线、双绞线、网卡等
 *
 *  Java中的网络支持
 *      针对网络通信的不同层次，Java提供了不同的API，其提供的网络功能有四大类：
 *      InetAddress:用于标识网络上的硬件资源，主要是IP地址
 *      URL：统一资源定位符，通过URL可以直接读取或写入网络上的数据
 *      Sockets：使用TCP协议实现的网络通信Socket相关的类
 *      Datagram:使用UDP协议，将数据保存在用户数据报中，通过网络进行通信。
 *
 * Created by Administrator on 2017/4/25.
 */
public class SocketClient {
    public static void main(String[] args){
//        showURL();
//        showInetAddr();
//        showUrlData();
        createClient();
    }
    //客户端
    private static void createClient(){
        //1、创建客户端Socket，指定服务器地址和端口
        try {
            Socket socket = new Socket("127.0.0.1", 10879);
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write("abyea");
            pw.flush();
            socket.shutdownOutput();

            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            while (true){
                if ((info=br.readLine()) != null){
                    show(info);
                    if (info.endsWith("byett")){
                        break;
                    }
                }
                Thread.sleep(1000);
            }
            pw.close();
            os.close();
            is.close();
            br.close();
            socket.close();
            show("over");

        } catch (IOException e) {
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private static void showUrlData(){
        try {
            URL url = new URL("http://www.xyaz.cn");
            InputStream is = url.openStream();      //字节输入流
            InputStreamReader isr = new InputStreamReader(is, "utf-8"); //字符输入流
            BufferedReader bufferedReader = new BufferedReader(isr);        //字符输出流添加缓存
            String data;
            while ((data = bufferedReader.readLine()) != null){
                show(data);
            }
            bufferedReader.close();
            isr.close();
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /** InetAddress **/
    private static void showInetAddr(){
        try {
            //获得本地主机信息
            InetAddress address = InetAddress.getLocalHost();
            show(address.getHostName());  //pc name
            show(address.getHostAddress()); //pc ip
            //通过ip获得别人主机
            InetAddress address1 = InetAddress.getByName("192.168.1.34");
            show(address1.getHostName());  //pc name
            //通过name获得别人主机
            InetAddress address2 = InetAddress.getByName("WQ-20160321EABU");
            show(address2.getHostAddress()); //pc ip
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /** URL **/
    private static void showURL(){
        try {
            URL xy = new URL("http://www.xyaz.cn");
            URL url = new URL(xy, "/index.html?username=tom&pwd=123#test"); // ?:参数, #:点
            show(url.getProtocol());  //获取协议
            show(url.getHost());    //获取主机

            show(url.getPort()+"");//如果没有指定端口号，根据协议不同使用默认端口。此时getPort()方法的返回值为 -1
            show(url.getPath());//获取文件路径
            show(url.getFile());//文件名，包括文件路径+参数
            show(url.getRef());//相对路径，就是锚点，即#号后面的内容
            show(url.getQuery());//查询字符串，即参数
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    private static void show(String str){
        System.out.println(str);
    }
}
