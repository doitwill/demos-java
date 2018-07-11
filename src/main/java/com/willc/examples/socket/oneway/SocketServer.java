package com.willc.examples.socket.oneway;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 单向通讯 -- server接收消息，打印输出到控制台.
 *
 * Created by chengkeqian on 2018/7/9.
 */
public class SocketServer {
    public static void main(String[] args) {
        final int port = 55535;
        try {
            ServerSocket server = new ServerSocket(port);
            Socket client = server.accept();  //block, and wait for connection request.

            //when the connection is established, get inputstrean to read data.
            InputStream is = client.getInputStream();
            byte[] data = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = is.read(data)) != -1) {
                sb.append(new String(data, 0, len, "utf-8"));
            }
            System.out.println("get message from client: " + sb);


            is.close();
            client.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
