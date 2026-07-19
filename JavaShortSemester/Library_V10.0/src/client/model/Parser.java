package client.model;

import java.util.ArrayList;

public class Parser {
    public ReturnResult parseOperationResult(String response) {
        ReturnResult result = new ReturnResult();
        if (response.contains("成功")) {
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
            result.setFailReason(response);
        }
        return result;
    }

    public Book parseSearchResult(String response) {
        if (response == null || response.contains("未找到")) {
            return null;
        }
        String[] parts = response.split(",");
        String bookName = parts[0];
        String author = parts[1];
        double price = Double.parseDouble(parts[2]);
        return new Book(bookName, author, price);
    }

    public ArrayList<Book> parseShowAllResult(String response) {
        ArrayList<Book> bookList = new ArrayList<>();
        if (response == null || response.isEmpty() || response.contains("无数据")
                || response.contains("未找到") || response.contains("系统为空")) {
            return bookList;
        }
        String[] records = response.split(";");
        for (String record : records) {
            if (record == null || record.trim().isEmpty()) {
                continue;
            }
            String[] parts = record.split(",");
            String bookName = parts[0];
            String author = parts[1];
            double price = Double.parseDouble(parts[2]);
            bookList.add(new Book(bookName, author, price));
        }
        return bookList;
    }
}