package com.bookstoreappliction.controller;

import com.bookstoreappliction.dto.AddressRequest;
import com.bookstoreappliction.dto.OrderDTO;
import com.bookstoreappliction.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    OrdersService ordersService;

    @PostMapping("/order/{cid}")
    public ResponseEntity<?> orderByCid(@RequestAttribute("id") long id, @PathVariable long cid, @RequestBody AddressRequest address){
        return ResponseEntity.ok(ordersService.orderById(id,cid,address));
    }

    @PostMapping("/order")
    public ResponseEntity<?> orderByUser(@RequestAttribute("id") long id, @RequestBody AddressRequest address){
        return ResponseEntity.ok(ordersService.orderByUser(id,address));
    }




}
