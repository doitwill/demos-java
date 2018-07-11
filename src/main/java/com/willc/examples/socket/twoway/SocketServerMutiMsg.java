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
public class SocketServerMutiMsg {
    public static void main(String[] args) {
        final int port = 55535;
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("I'm server and waiting for connection.");
            Socket socket = server.accept();  //block, and wait for connection request.

            //when the connection is established, get inputstrean to read data.
            //read msg from client.
            InputStream is = socket.getInputStream();
            while (true) {
                int first = is.read();
                if (first == -1) {
                    break;
                }
                int second = is.read();
                int length = (first << 8) + second;
                byte[] data = new byte[length];
                is.read(data);
                System.out.println("get message from client: " + new String(data, "utf-8"));
            }

            is.close();
            socket.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
