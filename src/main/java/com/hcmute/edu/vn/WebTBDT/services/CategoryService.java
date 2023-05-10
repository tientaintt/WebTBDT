package com.hcmute.edu.vn.WebTBDT.services;

import com.hcmute.edu.vn.WebTBDT.entities.CategoryEntity;

import java.util.List;


public interface CategoryService {
    List<CategoryEntity> findAll();
}
