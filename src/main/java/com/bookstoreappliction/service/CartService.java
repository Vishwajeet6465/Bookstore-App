package com.bookstoreappliction.service;

import com.bookstoreappliction.model.Book;
import com.bookstoreappliction.model.Cart;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {
    Cart addToCart(long id, long bookId, int quantity);

    String removeFromCart(long id,long cid);

    String removeAllByUser(long id);

    String updateQuantity(long id, long cid, int quantity);

    List<Cart> getallcartitemsforuser(long id);

    List<Cart> getallcartitems();
}
