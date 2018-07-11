package com.willc.examples.socket.transferfile;

import java.io.*;
import java.net.Socket;

/**
 * Created by chengkeqian on 2018/7/10.
 */
public class ThreadServer extends Thread {
    private static final int BUFFER_SIZE = 10 * 1024; // 8k ~ 32K
    private Socket socket;

    public ThreadServer(Socket sock){
        this.socket = sock;
    }

    public void run(){
        boolean flag = false;

        try{
            InputStream in = null;
            FileOutputStream out = null;
            try {
                File tempFile = new File("/Users/tianjing/AOSP/firefly/", "copied.tmp");
                if (tempFile.exists()) {
                    tempFile.delete();
                }
                if (!tempFile.createNewFile())
                    throw new FileNotFoundException(tempFile.getAbsolutePath() + " is not exist.");

                in = socket.getInputStream();
                out = new FileOutputStream(tempFile);

                //long totalLen = urlConnection.getContentLength();
                //int oldProgress = 0;

                byte[] buffer = new byte[BUFFER_SIZE];
                long curLen = 0l;
                int temp = 0;
                while ((temp = in.read(buffer)) != -1) {
                    out.write(buffer, 0, temp);
                    curLen += temp;

                    //int progress = (int) (((float) curLen / totalLen) * 100);
                    // 如果进度与之前进度相等，则不更新，如果更新太频繁，否则会造成界面卡顿
                    /*if (progress != oldProgress) {
                        callback.onDownloadProgress(progress, curLen, totalLen);
                        //updateProgress(progress);
                    }
                    oldProgress = progress;*/
                    System.out.println("The size of copied is " + curLen);
                }
                out.flush();

                //下载完成，Rename to remove .tmp
                File apkFile = new File("/Users/tianjing/AOSP/firefly/", "Firefly-RK3399_Android7.1.2_git_20180126.7z");
                tempFile.renameTo(apkFile);

                //isRunning = false;
                //callback.onDownloadCompleted(apkFile);
                //installApk(apkFile);
            } catch (Exception e) {
                System.out.println("File copied error " + e.getMessage());
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                socket.close();
            }
        }catch(IOException ex){
            System.out.println(ex);
        }
    }
}
