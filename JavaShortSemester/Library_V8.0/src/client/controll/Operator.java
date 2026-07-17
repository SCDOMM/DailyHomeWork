package client.controll;

import client.net.*;
import client.model.*;

public class Operator {

    private Protocol protocol = new Protocol();
    private Parser parser = new Parser();
    public ReturnResult addBook(Book book) {
        String msg = protocol.addBook(book);
        String response = Client.sendMsg(msg);
        return parser.parseOperationResult(response);
    }
    public ReturnResult deleteBook(String bookName) {
        String msg = protocol.deleteBook(bookName);
        String response = Client.sendMsg(msg);
        return parser.parseOperationResult(response);
    }

    public ReturnResult changeBook(String oldBookname, Book newBook) {
        String msg = protocol.changeBook(oldBookname, newBook);
        String response = Client.sendMsg(msg);
        return parser.parseOperationResult(response);
    }

    public Book searchBook(String bookName) {
        String msg = protocol.searchBook(bookName);
        String response = Client.sendMsg(msg);
        return parser.parseSearchResult(response);
    }

    public java.util.ArrayList<Book> showAllBooks() {
        String msg = protocol.showAllBooks();
        String response = Client.sendMsg(msg);
        return parser.parseShowAllResult(response);
    }
}