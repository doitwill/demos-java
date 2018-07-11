package com.willc.examples.socket.muticlients;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 一个Socket服务往往需要服务大量的Socket请求，那么就不能在服务完一个Socket的时候就关闭。
 * 采用循环接受请求并处理的逻辑
 *
 * Created by chengkeqian on 2018/7/9.
 */
public class SocketServer {
    public static void main(String args[]) throws IOException {
        // 监听指定的端口
        int port = 55533;
        ServerSocket server = new ServerSocket(port);
        // server将一直等待连接的到来
        System.out.println("server将一直等待连接的到来");

        while(true){
            Socket socket = server.accept();
            System.out.println("connected with client: " + socket.toString());

            // 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = inputStream.read(bytes)) != -1) {
                // 注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                sb.append(new String(bytes, 0, len, "UTF-8"));
            }
            System.out.println("get message from client: " + sb);

            inputStream.close();
            socket.close();
        }
    }
}
