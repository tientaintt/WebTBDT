package com.hcmute.edu.vn.WebTBDT.repositorys;

import com.hcmute.edu.vn.WebTBDT.entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartEntityRepository extends JpaRepository<CartEntity, Integer> {
}