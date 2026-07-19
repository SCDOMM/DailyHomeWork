package server.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {

    private final ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private static final AtomicInteger onlineClientCount = new AtomicInteger(0);

    public Server() {
        try {
            ServerSocket ss = new ServerSocket(8080);
            System.out.println("服务器启动，监听8080端口...");
            System.out.println("【多线程模式·线程池优化】可同时处理多个客户端连接");

            int clientCount = 1;
            while (true) {
                Socket socket = ss.accept();
                System.out.println("客户端" + clientCount + "连接：" + socket.getInetAddress());

                ServerThread serverThread = new ServerThread(socket);
                threadPool.submit(serverThread);
                onlineClientCount.incrementAndGet();

                System.out.println("客户端" + clientCount + "已分配线程处理，当前在线客户端数：" + onlineClientCount.get());
                clientCount++;
            }

        } catch (IOException e) {
            System.out.println("服务器连接异常：" + e.getMessage());
        }
    }

    public static void decrementClientCount() {
        int remaining = onlineClientCount.decrementAndGet();
        System.out.println("客户端断开连接，当前在线客户端数：" + remaining);
    }

    public static void main(String[] args) {
        System.out.println("服务器启动...");
        System.out.println("版本：V10.0（多线程并发版·课后练习优化版）");
        new Server();
    }
}