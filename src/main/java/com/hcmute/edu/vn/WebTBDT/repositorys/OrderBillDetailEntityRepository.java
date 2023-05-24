package com.hcmute.edu.vn.WebTBDT.repositorys;

import com.hcmute.edu.vn.WebTBDT.Model.ProductSell;
import com.hcmute.edu.vn.WebTBDT.entities.OrderBillDetailEntity;
import com.hcmute.edu.vn.WebTBDT.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderBillDetailEntityRepository extends JpaRepository<OrderBillDetailEntity, Integer> {
    List<OrderBillDetailEntity> findAllByOrderBillId(int id);
    @Query("SELECT obd.product FROM order_bill_detail obd GROUP BY obd.product ORDER BY SUM(obd.quantity) DESC")
    List<ProductEntity> get8ProductMostQuantities();
    @Query("SELECT obd.product,obd.quantity as numSell FROM order_bill_detail obd GROUP BY obd.product ORDER BY SUM(obd.quantity) ")
    List<ProductSell> getProductSell();
}