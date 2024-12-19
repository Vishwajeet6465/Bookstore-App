package com.bookstoreappliction.service;

import com.bookstoreappliction.dto.AddressRequest;
import com.bookstoreappliction.dto.OrderDTO;
import com.bookstoreappliction.model.Book;
import com.bookstoreappliction.model.Cart;
import com.bookstoreappliction.model.Orders;
import com.bookstoreappliction.repository.OrdersRepository;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    CartServiceImpl cartService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    BookServiceImpl bookService;

    @Override
    public ResponseEntity<?> orderById(long id, long cid, AddressRequest address) {
        Cart cart = cartService.getCartById(cid);
        if (cart.getUser().getId() == id) {
            Orders order = new Orders();
            order.setOrderDate(LocalDate.now());
            order.setCancelOrder(false);
            order.setPrice(cart.getTotalPrice());
            order.setQuantity(cart.getQuantity());
            boolean check = bookService.reduceStock(cid, order.getQuantity());
            if (!check)
                return ResponseEntity.ok("Item out of stock");
            order.setUserAddress(address.getAddress());
            order.setUser(userService.getUserbyId(id));
            List<Book> bookList = new java.util.ArrayList<>(List.of());
            bookList.add(cart.getBook());
            order.setBookList(bookList);
            cartService.setIdNull(cid);
            ordersRepository.save(order);
            ResponseEntity.ok("Order placed successfully!" + order);
        }
        return ResponseEntity.ok("Wrong Cart ID");
    }

    @Override
    public ResponseEntity<?> orderByUser(long id, AddressRequest address) {
        List<Cart> cart = cartService.getallcartitemsforuser(id);
        Orders order = new Orders();
        order.setOrderDate(LocalDate.now());
        order.setCancelOrder(false);
        order.setPrice(cart.stream().mapToDouble(Cart::getTotalPrice).reduce(0.0, Double::sum));
        order.setQuantity(cart.stream().mapToInt(Cart::getQuantity).reduce(0, Integer::sum));
        List<Long> cids=cart.stream().map(Cart::getCartId).toList();
        for (Long cid : cids) {
            boolean check = bookService.reduceStock(cid, order.getQuantity());
            if (!check) {
                return ResponseEntity.ok("Item out of stock");
            }
        }
        order.setUserAddress(address.getAddress());
        order.setUser(userService.getUserbyId(id));
        List<Book> bookList = cart.stream().map(Cart::getBook).toList();
        //bookList.add(cart.getBook());
        order.setBookList(bookList);
        for (Long cid : cids)
            cartService.setIdNull(cid);
        ordersRepository.save(order);
        ResponseEntity.ok("Order placed successfully!" + order);

        return ResponseEntity.ok("Wrong Cart ID");
    }
}
