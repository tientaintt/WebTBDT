package com.hcmute.edu.vn.WebTBDT.services;

import com.hcmute.edu.vn.WebTBDT.entities.OrderBillEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderBillService {
    List<OrderBillEntity> findAllByCustomerId(int id);

    OrderBillEntity saveOrderBill(OrderBillEntity orderBillEntity);


    OrderBillEntity findByIdAndCustomerId(int id, int cusId);

    OrderBillEntity findById(int id);

    void deleteOrderBill(int id);

    List<OrderBillEntity> finAll();
    Page<OrderBillEntity> findPage(int pageNumber , int pageSize);

}
