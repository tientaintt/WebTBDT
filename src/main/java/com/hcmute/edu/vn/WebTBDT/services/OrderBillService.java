package com.hcmute.edu.vn.WebTBDT.services;

import com.hcmute.edu.vn.WebTBDT.entities.OrderBillEntity;

import java.util.List;

public interface OrderBillService {
    List<OrderBillEntity> findAllByCustomerId(int id);

    OrderBillEntity saveOrderBill(OrderBillEntity orderBillEntity);


    OrderBillEntity findByIdAndCustomerId(int id, int cusId);

    OrderBillEntity findById(int id);

    void deleteOrderBill(int id);
}
