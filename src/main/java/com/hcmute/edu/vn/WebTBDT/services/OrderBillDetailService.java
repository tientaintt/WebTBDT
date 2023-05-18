package com.hcmute.edu.vn.WebTBDT.services;

import com.hcmute.edu.vn.WebTBDT.entities.OrderBillDetailEntity;

import java.util.List;

public interface OrderBillDetailService {
    List<OrderBillDetailEntity> findAllByOrderBillId(int id);

    void saveOrderBillDetail(OrderBillDetailEntity orderBillDetailEntity);
}
