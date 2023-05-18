package com.hcmute.edu.vn.WebTBDT.repositorys;

import com.hcmute.edu.vn.WebTBDT.entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartEntityRepository extends JpaRepository<CartEntity, Integer> {
    List<CartEntity> findAllByCustomerId(int id);

    CartEntity findByCustomerId(int id);
}