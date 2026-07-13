package ui;

import model.Book;
import control.Operator;  // 【新增】引入数据库操作类
import java.util.ArrayList;
import java.util.Scanner;

public class MainClass {

    private Scanner scanner = new Scanner(System.in);
    private Operator operator = new Operator();

    public MainClass() {
        boolean flag = true;
        while (flag) {
            System.out.println("===== 图书管理系统 =====");
            System.out.println("1. 添加图书");
            System.out.println("2. 删除图书");
            System.out.println("3. 修改图书信息");
            System.out.println("4. 查询图书");
            System.out.println("5. 显示全部图书");
            System.out.println("6. 进行条件查询");
            System.out.println("0. 退出");
            System.out.print("请选择：");
            int choice = scanner.nextInt();
            scanner.nextLine();
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
                    searchByCondition();
                    break;
                case 0:
                    System.out.println("感谢使用！");
                    flag = false;
                    break;
                default:
                    System.out.println("输入错误，请输入数字0-5！");
            }
        }
    }

    public void addBook() {
        System.out.print("请输入书名：");
        String bookname = scanner.nextLine();
        System.out.print("请输入作者：");
        String author = scanner.nextLine();
        System.out.print("请输入价格：");
        int price = scanner.nextInt();
        scanner.nextLine();

        Book book = new Book(bookname, author, price);
        if (operator.addBook(book)) {
            System.out.println("添加成功！");
        } else {
            System.out.println("添加失败！");
        }
    }

    public void deleteBook() {
        System.out.print("请输入要删除的书名：");
        String bookname = scanner.nextLine();

        if (operator.deleteBook(bookname)) {
            System.out.println("删除成功！");
        } else {
            System.out.println("删除失败（图书不存在）！");
        }
    }

    public void changeBook() {
        System.out.print("请输入要修改的书名（用于定位）：");
        String bookname = scanner.nextLine();
        System.out.print("请输入新书名：");
        String newBookname = scanner.nextLine();
        System.out.print("请输入新作者：");
        String newAuthor = scanner.nextLine();
        System.out.print("请输入新价格：");
        int newPrice = scanner.nextInt();
        scanner.nextLine();

        Book newBook = new Book(newBookname, newAuthor, newPrice);

        if (operator.changeBook(bookname, newBook)) {
            System.out.println("修改成功！");
        } else {
            System.out.println("修改失败（图书不存在）！");
        }
    }

    public void searchBook() {
        System.out.print("请输入要查询的书名：");
        String bookname = scanner.nextLine();

        Book book = operator.searchBook(bookname);
        if (book != null) {
            System.out.println("找到图书：" + book.getName()
                    + "，作者：" + book.getAuthor()
                    + "，价格：" + book.getPrice());
        } else {
            System.out.println("未找到该图书！");
        }
    }
    public void showAllBooks() {
        ArrayList<Book> books = operator.showAllBooks();
        if (books.isEmpty()) {
            System.out.println("图书列表为空！");
        } else {
            for (Book book : books) {
                System.out.println(book.getName() + " | "
                        + book.getAuthor() + " | "
                        + book.getPrice());
            }
        }
    }
    public void searchByCondition() {
        System.out.print("请输入作者,为空格则不查询作者：");
        String author = scanner.nextLine();
        System.out.print("请输入最低价格,为负数则不划定价格范围：");
        int lowerPrice = Integer.parseInt(scanner.nextLine());
        System.out.print("请输入最高价格,为负数则不划定价格范围：");
        int upperPrice = Integer.parseInt(scanner.nextLine());

        ArrayList<Book> books = operator.searchByCondition(author,lowerPrice,upperPrice);
        if (books.isEmpty()) {
            System.out.println("图书列表为空！");
        } else {
            for (Book book : books) {
                System.out.println(book.getName() + " | "
                        + book.getAuthor() + " | "
                        + book.getPrice());
            }
        }
    }







}