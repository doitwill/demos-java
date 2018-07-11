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
public class SocketClientMutiMsg {
    public static void main(String[] args) {
        final String host = "127.0.0.1";
        final int port = 55535;
        try {
            //new a socket and connect to server socket.
            Socket socket = new Socket(host, port);

            //send msg to server.
            OutputStream os = socket.getOutputStream();
            System.out.println("Please input the msg you wanna tell server: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String msg = reader.readLine();
                if (msg != null && msg.equals("end")) {
                    break;
                }
                byte[] msgBytes = msg.getBytes("utf-8");
                os.write(msgBytes.length >> 8);
                os.write(msgBytes.length);
                os.write(msgBytes);
                os.flush();
            }

            os.close();
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
