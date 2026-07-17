package client.ui;

import client.net.Client;
import server.model.Book;
import server.control.Operator;
import server.model.ReturnResult;

import java.util.ArrayList;
import java.util.Scanner;

public class MainClass {

    private Operator operator = new Operator();
    private Scanner scanner = new Scanner(System.in);

    public MainClass() {
        boolean flag = true;

        while (flag) {
            System.out.println("===============================");
            System.out.println("欢迎来到图书管理系统（网络版）！");
            System.out.println("1. 添加图书");
            System.out.println("2. 删除图书");
            System.out.println("3. 修改图书");
            System.out.println("4. 查询图书");
            System.out.println("5. 显示所有图书");
            System.out.println("0. 退出系统");
            System.out.println("===============================");
            System.out.print("请选择：");

            String input = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("输入错误，请输入数字！");
                continue;
            }

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    deleteBook();
                    break;
                case 3:
                    changeBook();
                    break;
                case 4:
                    searchBook();
                    break;
                case 5:
                    showAllBooks();
                    break;
                case 0:
                    exitSystem();
                    flag = false;
                    break;
                default:
                    System.out.println("输入错误，请输入数字0-5！");
            }
        }
        scanner.close();
    }
    public void addBook() {
        System.out.println("\n===== 添加图书 =====");
        System.out.print("请输入书名：");
        String name = scanner.nextLine();
        System.out.print("请输入作者：");
        String author = scanner.nextLine();
        System.out.print("请输入价格：");
        double price;
        try {
            price = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("价格输入错误，必须是数字！");
            return;
        }

        Book book = new Book(name, author, price);
        ReturnResult result = operator.addBook(book);
        if (result.isSuccess()) {
            System.out.println("添加成功！");
        } else {
            System.out.println("添加失败：" + result.getFailReason());
        }
    }

    public void deleteBook() {
        System.out.println("\n===== 删除图书 =====");
        System.out.print("请输入要删除的书名：");
        String name = scanner.nextLine();

        ReturnResult result = operator.deleteBook(name);
        if (result.isSuccess()) {
            System.out.println("删除成功！");
        } else {
            System.out.println("删除失败：" + result.getFailReason());
        }
    }

    public void changeBook() {
        System.out.println("\n===== 修改图书 =====");
        System.out.print("请输入要修改的书名（用于定位）：");
        String oldName = scanner.nextLine();
        System.out.print("请输入新书名：");
        String newName = scanner.nextLine();
        System.out.print("请输入新作者：");
        String newAuthor = scanner.nextLine();
        System.out.print("请输入新价格：");
        double newPrice;
        try {
            newPrice = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("价格输入错误，必须是数字！");
            return;
        }

        Book newBook = new Book(newName, newAuthor, newPrice);
        ReturnResult result = operator.changeBook(oldName, newBook);
        if (result.isSuccess()) {
            System.out.println("修改成功！");
        } else {
            System.out.println("修改失败：" + result.getFailReason());
        }
    }

    public void searchBook() {
        System.out.println("\n===== 查询图书 =====");
        System.out.print("请输入要查询的书名：");
        String name = scanner.nextLine();

        Book book = operator.searchBook(name);
        if (book != null) {
            System.out.println("找到图书：" + book);
        } else {
            System.out.println("未找到该图书！");
        }
    }

    public void showAllBooks() {
        ArrayList<Book> books = operator.showAllBooks();

        if (books.isEmpty()) {
            System.out.println("\n系统为空，没有可显示的图书！");
            return;
        }

        System.out.println("\n===== 所有图书 =====");
        for (Book book : books) {
            System.out.println("  " + book);
        }
    }

    public void exitSystem() {
        System.out.println("\n正在关闭连接...");
        Client.sendMsg("closeserver");
        Client.closeClient();
        System.out.println("感谢使用，系统已退出！");
    }

    public static void main(String[] args) {
        new MainClass();
    }
}