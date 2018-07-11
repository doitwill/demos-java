package com.willc.examples.socket.muticlients;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 非异步的写法虽然能够循环处理多个Socket请求，但是当一个请求的处理比较耗时的时候，后面的请求将被阻塞，
 * 所以一般都是用多线程的方式来处理Socket，即每有一个Socket请求的时候，就创建一个线程来处理它。
 *
 * 不过在实际生产中，创建的线程会交给线程池来处理，为了：
 * 1. 线程复用，创建线程耗时，回收线程慢.
 * 2. 防止短时间内高并发（短时间创建大量线程导致资源耗尽，服务挂掉）。指定线程池大小，超过数量将等待.
 *
 * Created by chengkeqian on 2018/7/9.
 */
public class SocketServerAsync {
    public static void main(String args[]) throws IOException {
        // 监听指定的端口
        int port = 55533;
        ServerSocket server = new ServerSocket(port);
        System.out.println("server将一直等待连接的到来");

        ExecutorService threadPool = Executors.newFixedThreadPool(20);

        while(true){
            Socket socket = server.accept();
            System.out.println("connected with client: " + socket.toString());

            Runnable runnable = ()->{
                // 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
                InputStream inputStream = null;
                try {
                    inputStream = socket.getInputStream();
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            threadPool.submit(runnable);
        }
    }
}
