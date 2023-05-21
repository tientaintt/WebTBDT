package com.hcmute.edu.vn.WebTBDT.services;

import com.hcmute.edu.vn.WebTBDT.entities.CustomerEntity;

import java.util.List;

public interface CustomerService {
    CustomerEntity findUserByUsername(String username);

    void saveUser(CustomerEntity customer);

    CustomerEntity findById(int id);
    List<CustomerEntity> findAll();

    void deleteUserById(long id);
}
