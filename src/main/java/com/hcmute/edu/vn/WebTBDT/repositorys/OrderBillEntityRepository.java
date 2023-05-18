package com.hcmute.edu.vn.WebTBDT.repositorys;

import com.hcmute.edu.vn.WebTBDT.entities.OrderBillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderBillEntityRepository extends JpaRepository<OrderBillEntity, Integer> {
    List<OrderBillEntity> findAllByCustomerId(int id);

    OrderBillEntity findById(int id);
    OrderBillEntity findByIdAndCustomerId(int id, int cusId);
}