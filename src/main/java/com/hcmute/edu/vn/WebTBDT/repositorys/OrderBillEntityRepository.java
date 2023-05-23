package com.hcmute.edu.vn.WebTBDT.repositorys;

import com.hcmute.edu.vn.WebTBDT.entities.OrderBillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface OrderBillEntityRepository extends JpaRepository<OrderBillEntity, Integer> {
    List<OrderBillEntity> findAllByCustomerId(int id);

    OrderBillEntity findById(int id);
    OrderBillEntity findByIdAndCustomerId(int id, int cusId);


    List<OrderBillEntity> findByAvailableEquals(int available);


    @Query("SELECT SUM( o.totalPrice) FROM oder_bill  o where o.available=0")
    BigDecimal getToTalPaidAmount();

    @Query("select count(o ) FROM oder_bill o WHERE  o.available=0")
    int getTotalPaidOrderCustomer();

}