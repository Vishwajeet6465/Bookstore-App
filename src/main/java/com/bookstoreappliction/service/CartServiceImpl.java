package com.bookstoreappliction.service;

import com.bookstoreappliction.exception.IdNotFoundException;
import com.bookstoreappliction.model.Book;
import com.bookstoreappliction.model.Cart;
import com.bookstoreappliction.model.User;
import com.bookstoreappliction.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    public void setIdNull(long cid) {

        Cart cart=getCartById(cid);
        cart.setUser(null);
        cartRepository.save(cart);
    }



//    @Override
//    public Cart addToCart(long id, long bookId, int quantity) {
//        User user=userService.getUserbyId(id);
//        Book book=bookService.getBook(bookId);
//        Cart cart=findCartById(id);
//        if(cart==null){
//            Cart cart1=new Cart();
//            cart1.setCartId(id);
//            cart1.setUser(user);
//            List<Book> books = cart1.getBook();
//            books.add(book);
//            Map<Long,Integer> quantities=cart1.getQuantity();
//            quantities.put(bookId,quantity);
//            cart1.setQuantity(quantities);
//            double totalPrice = books.stream()
//                    .mapToDouble(b -> b.getPrice() * quantities.getOrDefault(b.getId(), b.getQuantity()))
//                    .sum();
//            cart1.setTotalPrice(totalPrice);
//            return cart1;
//        }
//        else {
//            List<Book> books = cart.getBook();
//            List<Book> books1=books.stream().filter(b -> b.getId() == bookId).toList();
//            if (books1.isEmpty()){
//                books.add(bookService.getBook(bookId));
//                cart.setBook(books);
//                Map<Long,Integer> quantities=cart.getQuantity();
//                quantities.put(bookId,quantity);
//                cart.setQuantity(quantities);
//                double totalPrice = books.stream()
//                        .mapToDouble(b -> b.getPrice() * quantities.getOrDefault(b.getId(), b.getQuantity()))
//                        .sum();
//                cart.setTotalPrice(totalPrice);
//            }
//            else {
//                Map<Long,Integer> quantities=cart.getQuantity();
//                int q=quantities.get(bookId);
//                q+=quantity;
//                quantities.put(bookId,q);
//                cart.setQuantity(quantities);
//                double totalPrice = books.stream()
//                        .mapToDouble(b -> b.getPrice() * quantities.getOrDefault(b.getId(), b.getQuantity()))
//                        .sum();
//                cart.setTotalPrice(totalPrice);
//
//            }
//
//            return cart;
//        }
//    }

    private List<Cart> findCartByUserAndBook(long id,long bookId) {
        return cartRepository.findAll().stream().filter(c->c.getUser().getId()==id && c.getBook().getId()==bookId).collect(Collectors.toList());
    }

    @Override
    public Cart addToCart(long id, long bookId, int quantity) {
        List<Cart> cart=findCartByUserAndBook(id,bookId);
        if(cart.isEmpty()){
            Cart cart1=new Cart();
            User user=userService.getUserbyId(id);
            Book book=bookService.getBook(bookId);
            cart1.setUser(user);
            cart1.setBook(book);
            cart1.setQuantity(quantity);
            //bookService.reduceStock(bookId,quantity);
            cart1.setTotalPrice(book.getPrice()*cart1.getQuantity());
            return cartRepository.save(cart1);
        }
        else {
            Cart cart1=cart.get(0);
            cart1.setQuantity(cart1.getQuantity()+quantity);
            //bookService.reduceStock(bookId,quantity);
            cart1.setTotalPrice(cart1.getBook().getPrice()*cart1.getQuantity());
            return cartRepository.save(cart1);
        }

    }

    @Override
    public String removeFromCart(long id,long cid) {
        Cart cart=getCartById(cid);
        if(cart.getUser().getId()==id){
            //bookService.addStock(cart.getBook().getId(),cart.getQuantity());
            cartRepository.deleteById(cid);
            return "Removed Successfully!";
        }
        else{
            return "Not your cart!";
        }

    }

    @Override
    public String removeAllByUser(long id) {
        List<Cart> carts=cartRepository.findAll().stream().filter(c->c.getUser().getId()==id).toList();
        if(carts.isEmpty()){
            return "Cart is already empty!";
        }
        //carts.forEach(cart -> bookService.addStock(cart.getBook().getId(), cart.getQuantity()));
        cartRepository.deleteAll(carts);
        return "Cart Emptied successfully!";
    }

    @Override
    public String updateQuantity(long id, long cid, int quantity) {
        Cart cart=getCartById(cid);
        if (cart.getUser().getId()==id){
            //int q=cart.getQuantity()-quantity;
            //bookService.addStock(cart.getBook().getId(),q);
            cart.setQuantity(quantity);
            cart.setTotalPrice(cart.getBook().getPrice()*cart.getQuantity());
            cartRepository.save(cart);
            return "Updated Successfully!";

        }
        return "Not your cart";
    }

    @Override
    public List<Cart> getallcartitemsforuser(long id) {
        return cartRepository.findAll().stream().filter(c->c.getUser().getId()==id).toList();
    }

    @Override
    public List<Cart> getallcartitems() {
        return cartRepository.findAll();
    }

    public Cart getCartById(long cid) {
        return cartRepository.findById(cid).orElseThrow(()->new IdNotFoundException("Cart ID not found"));
    }
}
