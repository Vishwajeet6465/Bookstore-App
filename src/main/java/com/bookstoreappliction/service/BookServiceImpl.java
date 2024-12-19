package com.bookstoreappliction.service;

import com.bookstoreappliction.exception.IdNotFoundException;
import com.bookstoreappliction.model.Book;
import com.bookstoreappliction.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    BookRepository bookRepository;

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> allBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBook(long id) {
        return bookRepository.findById(id).orElseThrow(()-> new IdNotFoundException("Book not found"));
    }

    @Override
    public void delBook(long id) {
        getBook(id);
        bookRepository.deleteById(id);
    }

    @Override
    public Book updateBook(long id,Book book) {
        Book book1=getBook(id);
        book1.setAuthor(book.getAuthor());
        book1.setDescript(book.getDescript());
        book1.setLogo(book.getLogo());
        book1.setPrice(book.getPrice());
        book1.setQuantity(book.getQuantity());
        book1.setBname(book.getBname());
        return bookRepository.save(book1);
    }

    @Override
    public Book ChangeBookQuantity(long id, int quantity) {
        Book book=getBook(id);
        book.setQuantity(quantity);
        return bookRepository.save(book);
    }

    @Override
    public Book ChangeBookPrice(long id, double price) {
        Book book=getBook(id);
        book.setPrice(price);
        return bookRepository.save(book);
    }

    @Override
    public boolean reduceStock(long bookId, int quantity) {
        Book book=getBook(bookId);
        int b=book.getQuantity()-quantity;
        if(b<0){
            return false;
        }
        book.setQuantity(b);
        bookRepository.save(book);
        return true;
    }

    @Override
    public void addStock(long bookId,int quantity) {
        Book book=getBook(bookId);
        book.setQuantity(book.getQuantity()+quantity);
        bookRepository.save(book);

    }

}
