import java.util.Scanner;

public class MainClass {

    private int bookNum = 0;
    private static final int MAXNUM = 100;
    private String[] bookList = new String[MAXNUM];

    public MainClass() {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        while (flag) {
            System.out.println("===============================");
            System.out.println("欢迎来到图书管理系统！");
            System.out.println("1. 添加图书");
            System.out.println("2. 删除图书");
            System.out.println("3. 修改图书");
            System.out.println("4. 查询图书");
            System.out.println("5. 关于系统");
            System.out.println("0. 退出系统");
            System.out.println("===============================");
            System.out.print("请选择：");

            int choice = scanner.nextInt();

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
                    System.out.println("当前系统版本为2.0");
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

    public void addBook() {
        if (bookNum >= MAXNUM) {
            System.out.println("书籍容量已经达到最大，无法添加！");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n===== 添加图书 =====");
        System.out.print("请输入书名：");
        String bookname = scanner.nextLine();

        bookList[bookNum] = bookname;
        bookNum++;

        System.out.println("第" + bookNum + "本书《" + bookList[bookNum - 1] + "》添加成功！");
        System.out.println("系统目前的书籍数为：" + bookNum + "本。");
    }


    public void searchBook() {
        Scanner scanner = new Scanner(System.in);

        if (bookNum == 0) {
            System.out.println("系统为空，没有可查询的书籍！");
            return;
        }

        System.out.println("\n===== 查询图书 =====");
        System.out.print("请输入查询关键字：");
        String keyword = scanner.nextLine();

        boolean found = false;
        System.out.println("查询结果：");
        for (int i = 0; i < bookNum; i++) {
            if (bookList[i].contains(keyword)) {
                System.out.println("  " + (i + 1) + ". 《" + bookList[i] + "》");
                found = true;
            }
        }

        if (!found) {
            System.out.println("  未找到包含\"" + keyword + "\"的图书。");
        }
    }

    public void deleteBook() {
        if (bookNum==0){
            System.out.println("错误！库存书为0！");
            return;
        }
        System.out.println("\n===== 请输入库存书名 =====");
        Scanner sc=new Scanner(System.in);
        String bookName=sc.nextLine();

        for(int i = 0; i<bookNum; i++){
            if (bookName.equals(bookList[i])){
                for (int j=bookNum-1;j>i;j--){
                    bookList[j-1]=bookList[j];
                }
                bookList[bookNum]=null;
                bookNum--;
                System.out.println("删除成功！");
                return;
            }
        }
        System.out.println("图书不存在！");
    }

    public void changeBook() {
        if (bookNum==0){
            System.out.println("错误！库存书为0！");
            return;
        }
        System.out.println("\n===== 请输入库存书名 =====");
        Scanner sc=new Scanner(System.in);
        String bookName=sc.nextLine();

        for(int i = 0; i<bookNum; i++){
            if (bookName.equals(bookList[i])){
                System.out.println("请输入新书名");
                String newBookName=sc.nextLine();
                bookList[i]=newBookName;
                System.out.println("修改成功！");
                return;
            }
        }
        System.out.println("图书不存在！");

    }

    public static void main(String[] args) {
        new MainClass();
    }
}