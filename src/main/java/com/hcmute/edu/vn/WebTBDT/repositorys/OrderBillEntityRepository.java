package com.hcmute.edu.vn.WebTBDT.repositorys;

import com.hcmute.edu.vn.WebTBDT.entities.OrderBillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderBillEntityRepository extends JpaRepository<OrderBillEntity, Integer> {
    List<OrderBillEntity> findAllByCustomerId(int id);

    OrderBillEntity findById(int id);
    OrderBillEntity findByIdAndCustomerId(int id, int cusId);
    @Query(value = "SELECT * FROM oder_bill o WHERE o.order_date >= ?1 AND o.order_date <= ?2", nativeQuery = true)
    List<OrderBillEntity> findOrdersByDateRange(String startDate,String endDate);

    List<OrderBillEntity> findByAvailableEquals(int available);


    @Query("SELECT SUM( o.totalPrice) FROM oder_bill  o where o.available=0")
    BigDecimal getToTalPaidAmount();

    @Query("select count(o ) FROM oder_bill o WHERE  o.available=0")
    int getTotalPaidOrderCustomer();

}