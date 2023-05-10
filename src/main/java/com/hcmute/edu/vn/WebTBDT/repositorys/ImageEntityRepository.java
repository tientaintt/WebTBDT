package com.hcmute.edu.vn.WebTBDT.repositorys;

import com.hcmute.edu.vn.WebTBDT.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageEntityRepository extends JpaRepository<ImageEntity, Integer> {

    List<ImageEntity> findAllByProductId(int id);
}