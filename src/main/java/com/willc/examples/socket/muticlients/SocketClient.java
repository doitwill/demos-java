package com.willc.examples.socket.muticlients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by chengkeqian on 2018/7/9.
 * 单向通讯 -- client发送消息到server.
 */
public class SocketClient {
    public static void main(String[] args) {
        final String host = "127.0.0.1";
        final int port = 55533;
        try {
            //new a socket and connect to server socket.
            Socket client = new Socket(host, port);

            //send msg to server.
            PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            writer.println(reader.readLine());
            writer.flush();

            writer.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
