package client.net;

import java.io.*;
import java.net.*;

public class Client {
    static Socket socket;
    static DataInputStream dis;
    static DataOutputStream dos;

    static {
        try {
            socket = new Socket("127.0.0.1", 8080);
            System.out.println("客户端连接服务器成功...");
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("连接服务器失败：" + e.getMessage());
        }
    }

    public static String sendMsg(String msg) {
        try {
            dos.writeUTF(msg);
            dos.flush();
            return dis.readUTF();
        } catch (IOException e) {
            System.err.println("通信异常：" + e.getMessage());
            return null;
        }
    }

    public static void closeClient() {
        try {
            if (dos != null) dos.close();
            if (dis != null) dis.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("关闭连接异常：" + e.getMessage());
        }
    }
}