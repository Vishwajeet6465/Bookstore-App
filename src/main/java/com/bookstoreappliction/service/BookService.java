package com.bookstoreappliction.service;

import com.bookstoreappliction.model.Book;

import java.util.List;

public interface BookService {
    Book addBook(Book book);

    List<Book> allBooks();

    Book getBook(long id);

    void delBook(long id);

    Book updateBook(long id,Book book);

    Book ChangeBookQuantity(long id, int quantity);

    Book ChangeBookPrice(long id, double price);

    boolean reduceStock(long bookId, int quantity);

    void addStock(long bookId,int quantity);
}
