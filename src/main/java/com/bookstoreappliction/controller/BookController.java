package com.bookstoreappliction.controller;

import com.bookstoreappliction.config.TokenUtility;
import com.bookstoreappliction.model.Book;
import com.bookstoreappliction.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    TokenUtility tokenUtility;

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestAttribute("role") String role, @RequestBody Book book) {
        if ("Admin".equals(role))
            return ResponseEntity.ok(bookService.addBook(book));
        else
            return ResponseEntity.status(403).body("Access Denied");

    }

    @GetMapping("/all")
    public List<Book> allBooks() {
        return bookService.allBooks();
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable long id) {
        return bookService.getBook(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delBook(@RequestAttribute("role") String role, @PathVariable long id) {
        if ("Admin".equals(role)) {
            bookService.delBook(id);
            return ResponseEntity.ok("Book deleted");
        }
        else {
            return ResponseEntity.status(403).body("Access Denied");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@RequestAttribute("role") String role, @PathVariable long id,@RequestBody Book book) {
        if ("Admin".equals(role))
            return ResponseEntity.ok(bookService.updateBook(id,book));
        else
            return ResponseEntity.status(403).body("Access Denied");
    }

    @PutMapping("/changeQuantity")
    public ResponseEntity<?> ChangeBookQuantity(@RequestAttribute("role") String role,@RequestParam long id,@RequestParam int quantity){
        if ("Admin".equals(role))
            return ResponseEntity.ok(bookService.ChangeBookQuantity(id,quantity));
        else
            return ResponseEntity.status(403).body("Access Denied");
    }

    @PutMapping("/changePrice")
    public ResponseEntity<?> ChangeBookPrice(@RequestAttribute("role") String role,@RequestParam long id,@RequestParam double price){
        if ("Admin".equals(role))
            return ResponseEntity.ok(bookService.ChangeBookPrice(id,price));
        else
            return ResponseEntity.status(403).body("Access Denied");
    }

}
