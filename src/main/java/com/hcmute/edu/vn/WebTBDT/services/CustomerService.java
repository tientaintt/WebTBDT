package com.hcmute.edu.vn.WebTBDT.services;

import com.hcmute.edu.vn.WebTBDT.entities.CustomerEntity;

public interface CustomerService {
    CustomerEntity findUserByUsername(String username);

    void saveUser(CustomerEntity customer);
}
