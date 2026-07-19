package client.model;

public class Protocol {

    public String addBook(Book book) {
        String bookName = book.getName();
        String author = book.getAuthor();
        double price = book.getPrice();
        return "add:" + bookName + "," + author + "," + price;
    }
    public String deleteBook(String bookName) {
        return "delete:" + bookName;
    }

    public String changeBook(String oldBookname, Book newBook) {
        return "change:" + oldBookname + ","
                + newBook.getName() + ","
                + newBook.getAuthor() + ","
                + newBook.getPrice();
    }
    public String searchBook(String bookName) {
        return "search:" + bookName;
    }

    public String showAllBooks() {
        return "showall";
    }
    public String searchBookByAuthor(String authorName){
        return "searchAuthor:"+authorName;
    }

    public String closeServer() {
        return "closeserver";
    }
}