package com.hcmute.edu.vn.WebTBDT.services;

import com.hcmute.edu.vn.WebTBDT.Model.ProductSell;
import com.hcmute.edu.vn.WebTBDT.entities.CustomerEntity;
import com.hcmute.edu.vn.WebTBDT.entities.OrderBillDetailEntity;
import com.hcmute.edu.vn.WebTBDT.entities.ProductEntity;

import java.util.List;

public interface OrderBillDetailService {
    List<OrderBillDetailEntity> findAllByOrderBillId(int id);
    List<ProductEntity> find8ProductBestSell();
    void saveOrderBillDetail(OrderBillDetailEntity orderBillDetailEntity);
    List<ProductSell> getProductSell();

    List<Integer> recomendProduct(int customer);
    List<ProductSell> getTop4ProductHot();
}
