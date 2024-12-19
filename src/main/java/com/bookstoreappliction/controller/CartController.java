package com.bookstoreappliction.controller;

import com.bookstoreappliction.model.Cart;
import com.bookstoreappliction.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestAttribute("id") long id, @RequestParam long bookId,@RequestParam int quantity){
        Cart cart=cartService.addToCart(id,bookId,quantity);
        return ResponseEntity.ok("Hello "+cart.getUser()+"\nThe book :"+cart.getBook()+" with quantity:"+cart.getQuantity()+" was added to cart, successfully!"+"\nThe cost total of cart is $"+cart.getTotalPrice());

    }

    @GetMapping("/remove/{cid}")
    public ResponseEntity<?> removeFromCart(@RequestAttribute("id") long id,@PathVariable long cid){
        String r=cartService.removeFromCart(id,cid);
        return ResponseEntity.ok(r);
    }

    @GetMapping("/remove/all")
    public ResponseEntity<?> removeAllByUser(@RequestAttribute("id") long id){
        String r=cartService.removeAllByUser(id);
        return ResponseEntity.ok(r);
    }

    @GetMapping("/updatequantity")
    public ResponseEntity<?> updateQuantity(@RequestAttribute("id") long id,@RequestParam long cid,@RequestParam int quantity){

        String r=cartService.updateQuantity(id,cid,quantity);
        return ResponseEntity.ok(r);
    }
    @GetMapping("/carts")
    public ResponseEntity<?> getallcartitemsforuser(@RequestAttribute("id") long id){

        return ResponseEntity.ok(cartService.getallcartitemsforuser(id));
    }

    @GetMapping("/allcarts")
    public ResponseEntity<?> getallcartitems(@RequestAttribute("role") String role){
        if("Admin".equalsIgnoreCase(role))
            return ResponseEntity.ok(cartService.getallcartitems());
        else {
            return ResponseEntity.status(403).body("Access Denied");
        }
    }



}
