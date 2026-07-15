package model;

import java.util.ArrayList;

public interface DatabaseOperation {
    boolean addBook(Book book);
    boolean deleteBook(String bookname);
    boolean changeBook(String oldBookname, Book newBook);
    ArrayList<Book>  searchByCondition(String author,int lowerPrice,int upperPrice);
    Book searchBook(String bookname);
    ArrayList<Book> showAllBooks();
}