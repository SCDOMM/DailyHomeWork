package dbConnect;

import java.sql.*;

public class DataConnect {

    static final String url = "jdbc:mysql://localhost:3306/JavaShortSemester?characterEncoding=utf8&useSSL=false&&allowPublicKeyRetrieval=true";
    static final String user = "root";
    static final String password = "Aa3318752853";
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("数据库驱动加载成功");
        } catch (ClassNotFoundException e) {
            System.out.println("驱动加载失败：" + e.getMessage());
        }
    }
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("数据库连接失败：" + e.getMessage());
            return null;
        }
    }

    public static void close(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("关闭资源失败：" + e.getMessage());
        }
    }

    public static void close(Statement stmt, Connection conn) {
        close(null, stmt, conn);
    }
}
