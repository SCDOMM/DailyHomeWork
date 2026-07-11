package model;

public interface BookOperation {
    void addBook();
    void deleteBook();
    void changeBook();
    void searchBook();
    void showAllBooks();
    void loadData();   // 新增
    void saveData();   // 新增
}