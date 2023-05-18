package com.hcmute.edu.vn.WebTBDT.services.serviceImpl;

import com.hcmute.edu.vn.WebTBDT.entities.CustomerEntity;
import com.hcmute.edu.vn.WebTBDT.repositorys.CustomerEntityRepository;
import com.hcmute.edu.vn.WebTBDT.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
@Autowired
    CustomerEntityRepository customerEntityRepository;
    @Override
    public CustomerEntity findUserByUsername(String username) {
        return customerEntityRepository.findCustomerByUsername(username);
    }
    @Override
    public void saveUser(CustomerEntity customer){
        customerEntityRepository.save(customer);
    }
    @Override
    public CustomerEntity findById(int id){
        return customerEntityRepository.findById(id);
    }
}
