package com.bookstoreappliction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    @Id
    @GeneratedValue
    private long orderId;
    private LocalDate orderDate;
    private double price;
    private int quantity;
    private String userAddress;
    @ManyToOne
    private User user;
    @ManyToMany
    private List<Book> bookList;
    private boolean cancelOrder;

}
