package com.hcmute.edu.vn.WebTBDT.services.serviceImpl;

import com.hcmute.edu.vn.WebTBDT.entities.ImageEntity;
import com.hcmute.edu.vn.WebTBDT.entities.ProductEntity;
import com.hcmute.edu.vn.WebTBDT.repositorys.ImageEntityRepository;
import com.hcmute.edu.vn.WebTBDT.repositorys.ProductEntityRepository;
import com.hcmute.edu.vn.WebTBDT.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService  {
    @Autowired
    ProductEntityRepository repository;
    @Autowired
    ImageEntityRepository imageEntityRepository;
    @Override
    public List<ProductEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductEntity> findProductByCategoryId(int id) {
        return repository.findAllByCategoryId(id);
    }
    @Override
    public Page<ProductEntity> findProductByCategoryId(int id, Pageable pageable) {
        return repository.findAllByCategoryId(id, pageable);
    }
    @Override
    public Page<ProductEntity> findByProductName(String name, Pageable pageable){
        return repository.findDistinctByNameContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(name, name, pageable);

    }
    @Override
    public List<ProductEntity> find8ByCategoryId(int id){
        return repository.find8ByCategoryId(id);
    }
    @Override
    public List<ProductEntity> findAllByProductName(String name){
        if(name != null || !name.isEmpty() || !name.isBlank()) {
            return repository.findDistinctByNameContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(name, name);
        }
        return repository.findAll();
    }
    @Override
    public ProductEntity findById(int id){
        return repository.findById(id);
    }

    @Override
    public List<ProductEntity> find4ByCategoryId(int id, int procId) {
        return repository.find4ByCategoryId(id, procId);
    }
    @Override
    public void saveProduct(ProductEntity product){
        repository.save(product);
    }


    public Page<ProductEntity> findPage(int pageNumber , int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNumber -1 ,pageSize);
        return this.repository.findAll(pageable);
    }

    @Override
    public void deleteProductById(int  productId) {

        ProductEntity product = repository.findById(productId);
        List<ImageEntity> imageEntityList = product.getImagelist();
        imageEntityRepository.deleteAll(imageEntityList);
        repository.delete(product);

    }








}
