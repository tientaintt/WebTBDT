package com.hcmute.edu.vn.WebTBDT.repositorys;

import com.hcmute.edu.vn.WebTBDT.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    @Override
    List<CategoryEntity> findAll();

}