package server.net;

import server.controll.Operator;
import server.model.Book;
import server.model.ReturnResult;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread implements Runnable {

    private final Socket socket;
    private long lastHeartbeatTime = System.currentTimeMillis(); // 课后练习4：心跳时间戳

    public ServerThread(Socket socket) {
        this.socket = socket;
    }
    public void updateHeartbeatTime() {
        this.lastHeartbeatTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        try {
            System.out.println("【线程" + Thread.currentThread().getId() + "】开始服务客户端 " + socket.getRemoteSocketAddress());

            try (DataInputStream dis = new DataInputStream(socket.getInputStream());
                 DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {

                System.out.println(socket.getRemoteSocketAddress() + " 已上线...");

                while (true) {
                    long now = System.currentTimeMillis();
                    if (now - lastHeartbeatTime > 30000) {
                        System.out.println("【线程" + Thread.currentThread().getId() + "】客户端心跳超时，自动断开连接");
                        break;
                    }

                    String msg = dis.readUTF();
                    System.out.println("【线程" + Thread.currentThread().getId() + "】收到消息：" + msg);

                    updateHeartbeatTime();

                    if (msg.equals("exit") || msg.equals("closeserver")) {
                        System.out.println(socket.getRemoteSocketAddress() + " 下线了...");
                        dos.writeUTF("close");
                        break;
                    }

                    String[] parts = msg.split(":", 2);
                    String head = parts[0];
                    String body = parts.length > 1 ? parts[1] : "";
                    if (head.equals("add")) {
                        try {
                            String[] bookInfo = body.split(",");
                            String bookName = bookInfo[0];
                            String bookAuthor = bookInfo[1];
                            double bookPrice = Double.parseDouble(bookInfo[2]);
                            Book book = new Book(bookName, bookAuthor, bookPrice);
                            Operator operator = new Operator();
                            ReturnResult result = operator.addBook(book);
                            if (result.isSuccess()) {
                                dos.writeUTF("书籍添加成功！");
                            } else {
                                dos.writeUTF(result.getFailReason());
                            }
                        } catch (Exception e) {
                            System.out.println("【线程" + Thread.currentThread().getId() + "】处理add命令异常：" + e.getMessage());
                            e.printStackTrace();
                            dos.writeUTF("服务器处理异常：" + e.getMessage());
                        }
                    }
                    else if (head.equals("delete")) {
                        try {
                            Operator operator = new Operator();
                            ReturnResult result = operator.deleteBook(body);
                            if (result.isSuccess()) {
                                dos.writeUTF("书籍删除成功");
                            } else {
                                dos.writeUTF(result.getFailReason());
                            }
                        } catch (Exception e) {
                            System.out.println("【线程" + Thread.currentThread().getId() + "】处理delete命令异常：" + e.getMessage());
                            e.printStackTrace();
                            dos.writeUTF("服务器处理异常：" + e.getMessage());
                        }
                    }
                    else if (head.equals("change")) {
                        try {
                            String[] bookInfo = body.split(",");
                            String oldBookName = bookInfo[0];
                            String newBookName = bookInfo[1];
                            String newBookAuthor = bookInfo[2];
                            double newBookPrice = Double.parseDouble(bookInfo[3]);

                            Book newBook = new Book(newBookName, newBookAuthor, newBookPrice);
                            Operator operator = new Operator();
                            ReturnResult result = operator.changeBook(oldBookName, newBook);
                            if (result.isSuccess()) {
                                dos.writeUTF("书籍修改成功");
                            } else {
                                dos.writeUTF(result.getFailReason());
                            }
                        } catch (Exception e) {
                            System.out.println("【线程" + Thread.currentThread().getId() + "】处理change命令异常：" + e.getMessage());
                            e.printStackTrace();
                            dos.writeUTF("服务器处理异常：" + e.getMessage());
                        }
                    }
                    else if (head.equals("search")) {
                        try {
                            Operator operator = new Operator();
                            Book book = operator.searchBook(body);
                            if (book == null) {
                                dos.writeUTF("未找到");
                            } else {
                                dos.writeUTF(book.getName() + ","
                                        + book.getAuthor() + ","
                                        + book.getPrice());
                            }
                        } catch (Exception e) {
                            System.out.println("【线程" + Thread.currentThread().getId() + "】处理search命令异常：" + e.getMessage());
                            e.printStackTrace();
                            dos.writeUTF("服务器处理异常：" + e.getMessage());
                        }
                    }

                    else if (head.equals("showall")) {
                        try {
                            Operator operator = new Operator();
                            ArrayList<Book> bookList = operator.showAllBooks();
                            if (bookList == null || bookList.isEmpty()) {
                                dos.writeUTF("无数据");
                            } else {
                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < bookList.size(); i++) {
                                    Book book = bookList.get(i);
                                    sb.append(book.getName()).append(",")
                                            .append(book.getAuthor()).append(",")
                                            .append(book.getPrice());
                                    if (i < bookList.size() - 1) {
                                        sb.append(";");
                                    }
                                }
                                dos.writeUTF(sb.toString());
                            }
                        } catch (Exception e) {
                            System.out.println("【线程" + Thread.currentThread().getId() + "】处理showall命令异常：" + e.getMessage());
                            e.printStackTrace();
                            dos.writeUTF("服务器处理异常：" + e.getMessage());
                        }
                    }

                    else {
                        dos.writeUTF("未知命令：" + head);
                    }
                }

            } catch (IOException e) {
                System.out.println("【线程" + Thread.currentThread().getId() + "】客户端连接异常：" + e.getMessage());
            }

            System.out.println("【线程" + Thread.currentThread().getId() + "】结束服务");

        } catch (Exception e) {
            System.out.println("【线程" + Thread.currentThread().getId() + "】服务器异常：" + e.getMessage());
        } finally {
            Server.decrementClientCount();
        }
    }
}