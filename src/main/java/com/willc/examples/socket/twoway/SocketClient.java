package com.willc.examples.socket.twoway;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

/**
 * 双向通讯
 * <li>1. client发送消息到server.</li>
 * <li>2. client接收server消息，打印输出到控制台.</li>
 *
 * Created by chengkeqian on 2018/7/9.
 */
public class SocketClient {
    public static void main(String[] args) {
        final String host = "127.0.0.1";
        final int port = 55535;
        try {
            //new a socket and connect to server socket.
            Socket socket = new Socket(host, port);

            //send msg to server.
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Please input the msg you wanna tell server: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            writer.println(reader.readLine());
            writer.flush();
            socket.shutdownOutput(); //通过shutdownOutput告诉服务器已经发送完数据，后续只能接收数据

            //read msg from server.
            InputStream is = socket.getInputStream();
            byte[] data = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = is.read(data)) != -1) {
                sb.append(new String(data, 0, len, "utf-8"));
            }
            System.out.println("get message from server: " + sb);

            writer.close();
            is.close();
            socket.close();
        }catch (ConnectException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
