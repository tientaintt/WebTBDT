package com.hcmute.edu.vn.WebTBDT.repositorys;

import com.hcmute.edu.vn.WebTBDT.entities.CartDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartDetailEntityRepository extends JpaRepository<CartDetailEntity, Integer> {
    List<CartDetailEntity> findAllByCartId(int id);
}