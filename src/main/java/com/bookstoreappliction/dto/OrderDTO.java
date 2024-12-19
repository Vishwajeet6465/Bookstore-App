package com.bookstoreappliction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private long userId;
    private long bookId;
    private int quantity;
    private String userAddress;
}
