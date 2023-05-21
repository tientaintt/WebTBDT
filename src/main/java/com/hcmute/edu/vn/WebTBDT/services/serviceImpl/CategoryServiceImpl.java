package com.hcmute.edu.vn.WebTBDT.services.serviceImpl;

import com.hcmute.edu.vn.WebTBDT.entities.CategoryEntity;
import com.hcmute.edu.vn.WebTBDT.repositorys.CategoryRepository;
import com.hcmute.edu.vn.WebTBDT.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public List<CategoryEntity> findAll() {
       return categoryRepository.findAll();

    }

    public void save(CategoryEntity item)
    {
        categoryRepository.save(item);
    }

    @Override
    public CategoryEntity findById(int id) {
        return categoryRepository.findById(id);
    }
}
