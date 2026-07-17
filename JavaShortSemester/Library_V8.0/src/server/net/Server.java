package server.net;

import server.*;
import server.control.*;
import server.model.*;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {

    public Server() {
        try {
            ServerSocket ss = new ServerSocket(8080);
            System.out.println("服务器启动，监听8080端口...");

            try (Socket s = ss.accept();
                 DataInputStream dis = new DataInputStream(s.getInputStream());
                 DataOutputStream dos = new DataOutputStream(s.getOutputStream())) {

                System.out.println(s.getRemoteSocketAddress() + " 已连接...");

                while (true) {
                    String msg = dis.readUTF();
                    System.out.println("客户端信息：" + msg);

                    if (msg.equals("exit") || msg.equals("closeserver")) {
                        System.out.println(s.getRemoteSocketAddress() + " 断开连接...");
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
                            System.out.println("处理add命令异常：" + e.getMessage());
                            e.printStackTrace();
                            dos.writeUTF("服务器处理异常：" + e.getMessage());
                        }
                    } else if (head.equals("delete")) {
                        try {
                            Operator operator = new Operator();
                            ReturnResult result = operator.deleteBook(body);

                            if (result.isSuccess()) {
                                dos.writeUTF("书籍删除成功");
                            } else {
                                dos.writeUTF(result.getFailReason());
                            }
                        } catch (Exception e) {
                            System.out.println("处理delete命令异常：" + e.getMessage());
                            e.printStackTrace();
                            dos.writeUTF("服务器处理异常：" + e.getMessage());
                        }
                    } else if (head.equals("change")) {
                        try {
                            String[] bookInfo = body.split(",");
                            String oldBookName = bookInfo[0];          // 原书名
                            String newBookName = bookInfo[1];          // 新书名
                            String newBookAuthor = bookInfo[2];        // 新作者
                            double newBookPrice = Double.parseDouble(bookInfo[3]); // 新价格

                            Book newBook = new Book(newBookName, newBookAuthor, newBookPrice);
                            Operator operator = new Operator();
                            ReturnResult result = operator.changeBook(oldBookName, newBook);
                            if (result.isSuccess()) {
                                dos.writeUTF("书籍修改成功");
                            } else {
                                dos.writeUTF(result.getFailReason());
                            }
                        } catch (Exception e) {
                            System.out.println("处理change命令异常：" + e.getMessage());
                            e.printStackTrace();
                            dos.writeUTF("服务器处理异常：" + e.getMessage());
                        }
                    } else if (head.equals("search")) {
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
                            System.out.println("处理search命令异常：" + e.getMessage());
                            e.printStackTrace();
                            dos.writeUTF("服务器处理异常：" + e.getMessage());
                        }
                    } else if (head.equals("showall")) {
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
                            System.out.println("处理showall命令异常：" + e.getMessage());
                            e.printStackTrace();
                            dos.writeUTF("服务器处理异常：" + e.getMessage());
                        }
                    } else {
                        dos.writeUTF("未知命令：" + head);
                    }
                }

            } catch (IOException e) {
                System.out.println("客户端连接异常：" + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("服务器连接异常：" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("服务器启动...");
        new Server();
    }
}