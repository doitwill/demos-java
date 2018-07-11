package com.willc.examples.socket.transferfile;

import java.io.*;
import java.net.Socket;

/**
 * Created by chengkeqian on 2018/7/10.
 */
public class SocketClient {
    private static final int BUFFER_SIZE = 10 * 1024; // 8k ~ 32K
    private String host = "127.0.0.1";
    private int port = 55555;

    private Socket socket = null;

    public void connect(String host, int port) throws Exception {
        this.host = host;
        this.port = port;
        this.connect();
    }

    public void connect() throws Exception {
        try {
            socket = new Socket(host, port);
            OutputStream os = socket.getOutputStream();

            String filePath = "/Users/wangyixiang/AOSP/Firefly-RK3399_Android7.1.2_git_20180126.7z";
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);

            byte[] bytes = new byte[BUFFER_SIZE];
            int len;
            long total = 0;
            while ((len = fis.read(bytes)) != -1) {
                os.write(bytes, 0, len);
                total += len;
                System.out.println("The size has been sent is " + total);
            }

            os.flush();
            os.close();
            socket.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) {
        SocketClient socketClient = new SocketClient();
        try {
            socketClient.connect("192.168.31.54", 55555);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
