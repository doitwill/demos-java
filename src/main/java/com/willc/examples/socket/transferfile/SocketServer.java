package com.willc.examples.socket.transferfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by chengkeqian on 2018/7/10.
 */
public class SocketServer {
    private static final int port = 55555;
    private ServerSocket serverSocket = null;
    private Socket socket = null;

    DataInputStream inStream = null;
    DataOutputStream outStream = null;
    FileInputStream fis = null;

    public SocketServer() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void waitForClient() {
        try {
            while (true) {
                socket = serverSocket.accept();
                ThreadServer thread = new ThreadServer(socket);
                thread.start();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) {
        SocketServer socketServer = new SocketServer();
        socketServer.waitForClient();
    }
}
