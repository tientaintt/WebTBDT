package com.hcmute.edu.vn.WebTBDT.repositorys;

import com.hcmute.edu.vn.WebTBDT.entities.OrderBillDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderBillDetailEntityRepository extends JpaRepository<OrderBillDetailEntity, Integer> {
    List<OrderBillDetailEntity> findAllByOrderBillId(int id);
}