package com.hcmute.edu.vn.WebTBDT.services.serviceImpl;

import com.hcmute.edu.vn.WebTBDT.entities.OrderBillEntity;
import com.hcmute.edu.vn.WebTBDT.repositorys.OrderBillEntityRepository;
import com.hcmute.edu.vn.WebTBDT.services.OrderBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderBillServiceImpl implements OrderBillService {
    @Autowired
    OrderBillEntityRepository repository;
    @Override
    public List<OrderBillEntity> findAllByCustomerId(int id) {
        return repository.findAllByCustomerId(id);
    }
    @Override
    public OrderBillEntity saveOrderBill(OrderBillEntity orderBillEntity){
        orderBillEntity.setOrderDate(new Date(new java.util.Date().getTime()));
        return repository.save(orderBillEntity);
    }
    @Override
    public OrderBillEntity findByIdAndCustomerId(int id, int cusId){
        return repository.findByIdAndCustomerId(id, cusId);
    }
    @Override
    public OrderBillEntity findById(int id){
        return repository.findById(id);
    }
    @Override
    public void deleteOrderBill(int id){
        repository.deleteById(id);
    }

    @Override
    public List<OrderBillEntity> finAll() {
        return repository.findAll();
    }

    @Override
    public Page<OrderBillEntity> findPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber -1 ,pageSize);
        return this.repository.findAll(pageable);
    }
}
