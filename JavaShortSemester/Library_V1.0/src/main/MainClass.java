package main;

import java.util.Scanner;

public class MainClass {
    public MainClass() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("===============================");
            System.out.println("欢迎来到图书管理系统！");
            System.out.println("2. 关于系统");
            System.out.println("1. 测试功能");
            System.out.println("0. 退出系统");
            System.out.println("===============================");
            System.out.print("请选择：");

            int choice = input.nextInt();

            switch (choice) {
                case 2:
                    System.out.println("当前版本：1.0");
                    break;
                case 1:
                    System.out.println("测试功能运行成功！");
                    break;
                case 0:
                    System.out.println("感谢使用，系统已退出！");
                    return;
                    default:
                    System.out.println("输入错误，请重新选择！");
            }
        }
    }
    public static void main(String[] args) {
        new MainClass();
    }
}