package com.hcmute.edu.vn.WebTBDT.repositorys;

import com.hcmute.edu.vn.WebTBDT.entities.CommentProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentProductEntityRepository extends JpaRepository<CommentProductEntity, Integer> {
    List<CommentProductEntity> findAllByProductId(int id);
}