package com.bookstoreappliction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue
    private long cartId;
    @ManyToOne
    private User user;
    @ManyToOne
    private Book book;
    private int quantity;
    private double totalPrice;

}
