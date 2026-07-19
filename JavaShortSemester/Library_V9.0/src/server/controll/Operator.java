package server.controll;

import server.dbConnect.DataConnection;
import server.model.Book;
import server.model.ReturnResult;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Operator {
    public ReturnResult addBook(Book book) {
        String name = book.getName();
        String author = book.getAuthor();
        double price = book.getPrice();
        Connection conn = DataConnection.getConnection();
        ReturnResult result = new ReturnResult();
        if (conn == null) {
            result.setSuccess(false);
            result.setFailReason("数据库连接失败，请检查MySQL服务是否启动");
            return result;
        }

        String sql = "INSERT INTO booklist (bookname, author, price) VALUES ('"
                + name + "', '"
                + author + "', "
                + price + ")";

        try (Statement statement = conn.createStatement()) {
            int i = statement.executeUpdate(sql);
            if (i > 0) {
                System.out.println("《" + name + "》，添加成功！");
                result.setSuccess(true);
            } else {
                result.setSuccess(false);
                result.setFailReason("书本添加失败！");
            }
        } catch (SQLException e) {
            result.setSuccess(false);
            result.setFailReason("数据库操作异常: " + e.getMessage());
        }
        return result;
    }

    public ReturnResult deleteBook(String delBook) {
        ReturnResult result = new ReturnResult();
        Connection conn = DataConnection.getConnection();

        if (conn == null) {
            result.setSuccess(false);
            result.setFailReason("数据库连接失败，请检查MySQL服务是否启动");
            return result;
        }

        String sql = "DELETE FROM booklist WHERE bookname = '" + delBook + "'";

        try (Statement statement = conn.createStatement()) {
            int i = statement.executeUpdate(sql);
            if (i > 0) {
                result.setSuccess(true);
                System.out.println("《" + delBook + "》，删除成功！");
            } else {
                result.setSuccess(false);
                result.setFailReason("系统没有书籍《" + delBook + "》");
            }
        } catch (SQLException e) {
            result.setSuccess(false);
            result.setFailReason("数据库操作异常: " + e.getMessage());
        }
        return result;
    }

    public ReturnResult changeBook(String oldBookname, Book newBook) {
        ReturnResult result = new ReturnResult();
        Connection conn = DataConnection.getConnection();
        if (conn == null) {
            result.setSuccess(false);
            result.setFailReason("数据库连接失败，请检查MySQL服务是否启动");
            return result;
        }
        String sql = "UPDATE booklist SET bookname='" + newBook.getName()
                + "', author='" + newBook.getAuthor()
                + "', price=" + newBook.getPrice()
                + " WHERE bookname='" + oldBookname + "'";
        try (Statement statement = conn.createStatement()) {
            int i = statement.executeUpdate(sql);
            if (i > 0) {
                result.setSuccess(true);
                System.out.println("《" + oldBookname + "》修改为《" + newBook.getName() + "》，成功！");
            } else {
                result.setSuccess(false);
                result.setFailReason("系统没有书籍《" + oldBookname + "》");
            }
        } catch (SQLException e) {
            result.setSuccess(false);
            result.setFailReason("数据库操作异常: " + e.getMessage());
        }
        return result;
    }

    public Book searchBook(String bookname) {
        Connection conn = DataConnection.getConnection();
        if (conn == null) {
            return null;
        }
        String sql = "SELECT * FROM booklist WHERE bookname='" + bookname + "'";
        try (Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            if (rs.next()) {
                String name = rs.getString("bookname");
                String author = rs.getString("author");
                double price = rs.getDouble("price");
                return new Book(name, author, price);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("查询图书异常: " + e.getMessage());
            return null;
        }
    }

    public ArrayList<Book> showAllBooks() {
        ArrayList<Book> bookList = new ArrayList<>();
        Connection conn = DataConnection.getConnection();
        if (conn == null) {
            return bookList;
        }
        String sql = "SELECT * FROM booklist";
        try (Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                String name = rs.getString("bookname");
                String author = rs.getString("author");
                double price = rs.getDouble("price");
                bookList.add(new Book(name, author, price));
            }
        } catch (SQLException e) {
            System.out.println("查询全部图书异常: " + e.getMessage());
        }
        return bookList;
    }
    public Book searchBookByAuthor(String author) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DataConnection.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM booklist WHERE author = '" + author + "'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String name = rs.getString("bookname");
                String author1 = rs.getString("author");
                int price = rs.getInt("price");
                JOptionPane.showMessageDialog(null, "🎉 搜索成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                Book book=new Book(name, author1, price);
                return new Book(name, author1, price);
            }
            return null;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ 搜索失败！", "错误", JOptionPane.ERROR_MESSAGE);
            return null;
        } finally {
            DataConnection.close(rs, stmt, conn);
        }
    }


}