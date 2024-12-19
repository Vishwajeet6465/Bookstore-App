package com.bookstoreappliction.service;

import com.bookstoreappliction.dto.AddressRequest;
import org.springframework.http.ResponseEntity;

public interface OrdersService {
    ResponseEntity<?> orderById(long id, long cid, AddressRequest address);

    ResponseEntity<?> orderByUser(long id, AddressRequest address);
}
