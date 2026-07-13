package control;

import java.sql.*;
import java.util.ArrayList;

import dbConnect.DataConnect;
import model.Book;
import model.DatabaseOperation;

public class Operator implements DatabaseOperation {
    public boolean addBook(Book book) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DataConnect.getConnection();
            stmt = conn.createStatement();
            String sql = "INSERT INTO booklist (bookname, author, price) VALUES ('"
                    + book.getName() + "', '" + book.getAuthor() + "', " + book.getPrice() + ")";
            int count = stmt.executeUpdate(sql);
            return count > 0;
        } catch (SQLException e) {
            System.out.println("添加图书失败：" + e.getMessage());
            return false;
        } finally {
            DataConnect.close(stmt, conn);
        }
    }

    public Book searchBook(String bookname) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DataConnect.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM booklist WHERE bookname = '" + bookname + "'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String name = rs.getString("bookname");
                String author = rs.getString("author");
                int price = rs.getInt("price");
                return new Book(name, author, price);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("查询图书失败：" + e.getMessage());
            return null;
        } finally {
            DataConnect.close(rs, stmt, conn);
        }
    }

    @Override
    public boolean deleteBook(String bookname) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DataConnect.getConnection();
            stmt = conn.createStatement();
            String sql = "DELETE FROM booklist WHERE bookname='" + bookname + "'";
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DataConnect.close(stmt, conn);
        }
    }

    @Override
    public boolean changeBook(String oldBookname, Book newBook) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DataConnect.getConnection();
            stmt = conn.createStatement();

            String sql = "UPDATE booklist SET bookname='" + newBook.getName() + "',price=" + newBook.getPrice() + ",author='" + newBook.getAuthor() + "' " + "WHERE bookname='" + oldBookname + "'";
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DataConnect.close(stmt, conn);
        }
    }

    @Override
    public ArrayList<Book> searchByCondition(String author, int lowerPrice, int upperPrice) {
        if (upperPrice > 0 && lowerPrice > upperPrice) {
            System.out.println("价格输入错误！");
            return null;
        }
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<Book> bookList = new ArrayList<>();
        int choice = 0;
        if (author.isBlank()) {
            choice += 0;
        } else {
            choice += 1;
        }
        if (lowerPrice < 0 || upperPrice < 0) {
            choice += 2;
        } else {
            choice += 4;
        }
        String sql = "";
        switch (choice) {
            case 2:
                System.out.println("输入错误！");
                return new ArrayList<>();
            case 3:
                System.out.println("按照作者查询：");
                sql="SELECT * FROM booklist WHERE author='"+author+"'";
                break;
            case 4:
                System.out.println("按照价格查询：");
                sql="SELECT * FROM booklist WHERE price BETWEEN "+lowerPrice+" AND "+upperPrice;
                break;
            case 5:
                System.out.println("按照价格或作者查询：");
                sql="SELECT * FROM booklist WHERE (price BETWEEN "+lowerPrice+" AND "+upperPrice+") OR (author='"+author+"')";
                break;
        }

        try {
            conn = DataConnect.getConnection();
            stmt = conn.createStatement();

            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("bookname");
                String author1 = rs.getString("author");
                int price = rs.getInt("price");
                bookList.add(new Book(name, author1, price));
            }
            return bookList;
        } catch (SQLException e) {
            System.out.println("查询图书失败：" + e.getMessage());
            return new ArrayList<>();
        } finally {
            DataConnect.close(rs, stmt, conn);
        }
    }

    @Override
    public ArrayList<Book> showAllBooks() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<Book> bookList = new ArrayList<>();
        try {
            conn = DataConnect.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM booklist";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("bookname");
                String author = rs.getString("author");
                int price = rs.getInt("price");
                bookList.add(new Book(name, author, price));
            }
            return bookList;
        } catch (SQLException e) {
            System.out.println("查询图书失败：" + e.getMessage());
            return new ArrayList<>();
        } finally {
            DataConnect.close(rs, stmt, conn);
        }
    }
}
