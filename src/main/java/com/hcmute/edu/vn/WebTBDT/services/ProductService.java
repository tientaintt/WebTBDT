package com.hcmute.edu.vn.WebTBDT.services;

import com.hcmute.edu.vn.WebTBDT.entities.ProductEntity;

import java.util.List;

public interface ProductService {
    List<ProductEntity> findAll();
    List<ProductEntity> findProductByCategoryId(int id);
}
