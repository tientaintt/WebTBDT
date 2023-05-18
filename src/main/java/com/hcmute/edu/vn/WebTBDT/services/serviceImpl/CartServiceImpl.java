package com.hcmute.edu.vn.WebTBDT.services.serviceImpl;

import com.hcmute.edu.vn.WebTBDT.entities.CartEntity;
import com.hcmute.edu.vn.WebTBDT.repositorys.CartEntityRepository;
import com.hcmute.edu.vn.WebTBDT.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartEntityRepository repository;
    @Override
    public List<CartEntity> findAllByCustomerId(int id){
        return repository.findAllByCustomerId(id);
    }
    @Override
    public CartEntity findByCustomerId(int id){
        return repository.findByCustomerId(id);
    }
    @Override
    public void saveCart(CartEntity cart) {
        repository.save(cart);
    }
    @Override
    public void deleteCart(CartEntity cart){
        repository.delete(cart);
    }

    @Override
    public void deleteCart(List<CartEntity> cart){
        for(CartEntity c : cart)
            repository.delete(c);
    }
}
