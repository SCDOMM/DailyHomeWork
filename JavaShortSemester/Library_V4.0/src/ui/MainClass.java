package ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import model.Book;
import model.BookOperation;

public class MainClass implements BookOperation {

    private ArrayList<Book> bookList = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    public MainClass() {

        boolean flag = true;

        while (flag) {
            System.out.println("===============================");
            System.out.println("欢迎来到图书管理系统！");
            System.out.println("1. 添加图书");
            System.out.println("2. 删除图书");
            System.out.println("3. 修改图书");
            System.out.println("4. 查询图书");
            System.out.println("5. 显示所有图书");
            System.out.println("6. 关于系统");
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
                case 6:
                System.out.println("当前版本：4.0");
                break;
                case 0:
                    System.out.println("感谢使用，系统已退出！");
                    flag = false;
                    break;
                default:
                    System.out.println("输入错误，请输入数字0-5！");
            }
        }
        scanner.close();
    }

    @Override
    public void addBook() {
        System.out.println("\n===== 添加图书 =====");
        System.out.print("请输入书名：");
        String name = scanner.nextLine();
        System.out.print("请输入作者：");
        String author = scanner.nextLine();
        System.out.print("请输入价格：");
        double price = scanner.nextDouble();
        scanner.nextLine();

        Book book = new Book(name, author, price);
        bookList.add(book);

        System.out.println("添加成功：" + book);
        System.out.println("系统目前的书籍数为：" + bookList.size() + "本。");
    }

    @Override
    public void deleteBook() {
        if (bookList.isEmpty()) {
            System.out.println("错误！图书为空！");
            return;
        }
        System.out.println("请输入书名");
        String name = scanner.nextLine();
        Iterator<Book> iterator = bookList.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getName().equals(name)) {
                iterator.remove();
                return;
            }
        }
        System.out.println("未找到相关书籍！");

    }

    @Override
    public void changeBook() {
        if (bookList.isEmpty()) {
            System.out.println("错误！图书为空！");
            return;
        }
        System.out.println("请输入书名");
        String name = scanner.nextLine();
        for (int i=0;i<bookList.size();i++){
            if (bookList.get(i).getName().equals(name)){
                System.out.print("请输入新作者：");
                String author = scanner.nextLine();
                System.out.print("请输入新价格：");
                double price =Double.parseDouble(scanner.nextLine());
                bookList.get(i).setAuthor(author);
                bookList.get(i).setPrice(price);
                return;
            }
        }
        System.out.println("未找到相关书籍！");

    }


    @Override
    public void searchBook() {
        if (bookList.isEmpty()) {
            System.out.println("系统为空，没有可查询的书籍！");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n===== 查询图书 =====");
        System.out.print("请输入查询关键字：");
        String keyword = scanner.nextLine();

        boolean found = false;
        System.out.println("查询结果：");
        for (Book book : bookList) {
            if (book.getName().contains(keyword)) {
                System.out.println("  " + book);
                found = true;
            }
        }

        if (!found) {
            System.out.println("  未找到包含\"" + keyword + "\"的图书。");
        }
    }

    public void showAllBooks() {
        if (bookList.isEmpty()) {
            System.out.println("系统为空，没有可显示的图书！");
            return;
        }

        System.out.println("\n系统所有图书：");
        for (Book book : bookList) {
            System.out.println("  " + book);
        }
    }

    public static void main(String[] args) {
        new MainClass();
    }
}