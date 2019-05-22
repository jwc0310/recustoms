package com.andy.recustomviews.java.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 基于TCP协议的Socket通信，实现用户登录，服务端
 * Created by Administrator on 2017/4/25.
 */
public class SocketServer {

    public static void main(String[] args){
        createServer();
    }

    /**
     * 6、应用多线程实现服务器与多客户端之间的通信

     ① 服务器端创建ServerSocket，循环调用accept()等待客户端连接
     ② 客户端创建一个socket并请求和服务器端连接
     ③ 服务器端接受苦读段请求，创建socket与该客户建立专线连接
     ④ 建立连接的两个socket在一个单独的线程上对话
     ⑤ 服务器端继续等待新的连接
     //服务器线程处理
     //和本线程相关的socket
     Socket socket =null;
     //
     public serverThread(Socket socket){
        this.socket = socket;
     }

     publicvoid run(){
        //服务器处理代码
     }

     //============================================
     //服务器代码
     ServerSocket serverSocket =newServerSocket(10086);
     Socket socket =null;
     int count =0;//记录客户端的数量
     while(true){
     socket = serverScoket.accept();
     ServerThread serverThread =newServerThread(socket);
     serverThread.start();
     count++;
     System.out.println("客户端连接的数量："+count);
     }
     */

    private static void createServer(){
        try {
            //1、创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
            ServerSocket serverSocket = new ServerSocket(10879);

            do {
                System.out.println("listen...");
                //2、调用accept()方法开始监听，等待客户端的连接
                Socket socket = serverSocket.accept();
                System.out.println("accept a client");
//                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("2.pcm"));
                //3、获取输入流，并读取客户端信息
                //// TODO: 2017/11/20 check BufferedReader
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("3.pcm"));

                byte[] buffer = new byte[8192];

                int i;
//                while((i = bufferedReader.read(buffer, 0, 8192)) > 0){
                while((i = dataInputStream.read(buffer, 0, 8192)) > 0){
                    System.out.println("i = "+i);
                    dataOutputStream.write(buffer, 0, i);
                    dataOutputStream.flush();
//                    if (buffer.toString().endsWith("abyea")) {
//                        System.out.println("socket closed");
//                        break;
//                    }else {
////                        bufferedWriter.write(buffer, 0, i);
//                        dataOutputStream.write(buffer, 0, i);
//                        dataOutputStream.flush();
//                    }
                }
//                bufferedWriter.flush();
                socket.shutdownInput();
                socket.close();
                dataOutputStream.close();
                dataInputStream.close();
//                bufferedReader.close();
//                bufferedWriter.close();
            }while(true);
//            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * UDP部分
     *
     * 五、UDP编程
     UDP协议（用户数据报协议）是无连接的、不可靠的、无序的,速度快
     进行数据传输时，首先将要传输的数据定义成数据报（Datagram），大小限制在64k，在数据报中指明数据索要达到的Socket（主机地址和端口号），然后再将数据报发送出去
     DatagramPacket类:表示数据报包
     DatagramSocket类：进行端到端通信的类
     1、服务器端实现步骤
     ① 创建DatagramSocket，指定端口号
     ② 创建DatagramPacket
     ③ 接受客户端发送的数据信息
     ④ 读取数据
     复制代码
     1 //服务器端，实现基于UDP的用户登录
     2 //1、创建服务器端DatagramSocket，指定端口
     3 DatagramSocket socket =new datagramSocket(10010);
     4 //2、创建数据报，用于接受客户端发送的数据
     5 byte[] data =newbyte[1024];//
     6 DatagramPacket packet =newDatagramPacket(data,data.length);
     7 //3、接受客户端发送的数据
     8 socket.receive(packet);//此方法在接受数据报之前会一致阻塞
     9 //4、读取数据
     10 String info =newString(data,o,data.length);
     11 System.out.println("我是服务器，客户端告诉我"+info);
     12
     13
     14 //=========================================================
     15 //向客户端响应数据
     16 //1、定义客户端的地址、端口号、数据
     17 InetAddress address = packet.getAddress();
     18 int port = packet.getPort();
     19 byte[] data2 = "欢迎您！".geyBytes();
     20 //2、创建数据报，包含响应的数据信息
     21 DatagramPacket packet2 = new DatagramPacket(data2,data2.length,address,port);
     22 //3、响应客户端
     23 socket.send(packet2);
     24 //4、关闭资源
     25 socket.close();
     复制代码
     2、客户端实现步骤

     ① 定义发送信息
     ② 创建DatagramPacket，包含将要发送的信息
     ③ 创建DatagramSocket
     ④ 发送数据
     复制代码
     1 //客户端
     2 //1、定义服务器的地址、端口号、数据
     3 InetAddress address =InetAddress.getByName("localhost");
     4 int port =10010;
     5 byte[] data ="用户名：admin;密码：123".getBytes();
     6 //2、创建数据报，包含发送的数据信息
     7 DatagramPacket packet = newDatagramPacket(data,data,length,address,port);
     8 //3、创建DatagramSocket对象
     9 DatagramSocket socket =newDatagramSocket();
     10 //4、向服务器发送数据
     11 socket.send(packet);
     12
     13
     14 //接受服务器端响应数据
     15 //======================================
     16 //1、创建数据报，用于接受服务器端响应数据
     17 byte[] data2 = new byte[1024];
     18 DatagramPacket packet2 = new DatagramPacket(data2,data2.length);
     19 //2、接受服务器响应的数据
     20 socket.receive(packet2);
     21 String raply = new String(data2,0,packet2.getLenth());
     22 System.out.println("我是客户端，服务器说："+reply);
     23 //4、关闭资源
     24 socket.close();
     复制代码
     六、注意问题：

     1、多线程的优先级问题：
     根据实际的经验，适当的降低优先级，否侧可能会有程序运行效率低的情况
     2、是否关闭输出流和输入流：
     对于同一个socket，如果关闭了输出流，则与该输出流关联的socket也会被关闭，所以一般不用关闭流，直接关闭socket即可
     3、使用TCP通信传输对象，IO中序列化部分
     4、socket编程传递文件，IO流部分
     *
     */

}
