package com.hcmute.edu.vn.WebTBDT.services;

import com.hcmute.edu.vn.WebTBDT.entities.CartEntity;

import java.util.List;

public interface CartService {
    List<CartEntity> findAllByCustomerId(int id);

    CartEntity findByCustomerId(int id);

    void saveCart(CartEntity cart);

    void deleteCart(CartEntity cart);

    void deleteCart(List<CartEntity> cart);
}
