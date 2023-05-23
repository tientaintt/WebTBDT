package com.hcmute.edu.vn.WebTBDT.services;

import com.hcmute.edu.vn.WebTBDT.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<ProductEntity> findAll();
    List<ProductEntity> findProductByCategoryId(int id);

    Page<ProductEntity> findProductByCategoryId(int id, Pageable pageable);

    Page<ProductEntity> findByProductName(String name, Pageable pageable);

    List<ProductEntity> find8ByCategoryId(int id);

    List<ProductEntity> findAllByProductName(String name);

    ProductEntity findById(int id);

    List<ProductEntity> find4ByCategoryId(int id, int procId);

    void saveProduct(ProductEntity product);


    Page<ProductEntity> findPage(int pageNumber , int pageSize);

    void deleteProductById(int id);

}
