package com.hcmute.edu.vn.WebTBDT.repositorys;

import com.hcmute.edu.vn.WebTBDT.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductEntityRepository extends JpaRepository<ProductEntity, Integer> {
    @Override
    List<ProductEntity> findAll();
    List<ProductEntity> findAllByCategoryId(int id);
}