package com.willc.examples.socket.twoway;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 双向通讯
 * <li>1. server接收消息，打印输出到控制台.</li>
 * <li>2. server发送消息给client.</li>
 *
 * Created by chengkeqian on 2018/7/9.
 */
public class SocketServer {
    public static void main(String[] args) {
        final int port = 55535;
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("I'm server and waiting for connection.");
            Socket socket = server.accept();  //block, and wait for connection request.

            //when the connection is established, get inputstrean to read data.
            //read msg from client.
            InputStream is = socket.getInputStream();
            byte[] data = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            //只有当客户端关闭它的输出流的时候，服务端才能取得结尾的-1
            //如果读取的值为-1 说明到了流的末尾，Socket已经被关闭了，此时将不能再去读取.
            while ((len = is.read(data)) != -1) {
                sb.append(new String(data, 0, len, "utf-8"));
            }
            System.out.println("get message from client: " + sb);

            //send msg to client.
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            writer.println(reader.readLine());
            writer.flush();

            is.close();
            writer.close();
            socket.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
