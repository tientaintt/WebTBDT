package com.hcmute.edu.vn.WebTBDT.repositorys;

import com.hcmute.edu.vn.WebTBDT.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductEntityRepository extends JpaRepository<ProductEntity, Integer> {
    @Override
    List<ProductEntity> findAll();
    List<ProductEntity> findAllByCategoryId(int id);
    Page<ProductEntity> findAllByCategoryId(int id, Pageable pageable);

    List<ProductEntity> findAllByNameContainingIgnoreCase(String name);

    List<ProductEntity> findDistinctByNameContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(String name, String categoryName);

    ProductEntity findById(int id);
    Page<ProductEntity> findDistinctByNameContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(String name, String categoryName, Pageable pageable);
    @Query(value = "select * from product p where ?1 = p.category_id and ?2 != p.id LIMIT 4", nativeQuery = true)
    List<ProductEntity> find4ByCategoryId(int id, int procId);

    @Query(value = "select * from product p where ?1 = p.category_id LIMIT 8", nativeQuery = true)
    List<ProductEntity> find8ByCategoryId(int id);

//    @Query("update product p set p.name = :name , p.category = :category , p.quantity = :quantity , p.description = :description , p.price = :price ,p.available = :available" )
//    void updateProduct(@Param("name") String name , @Param("category")CategoryEntity category ,@Param("quantity") int quantity , @Param("description") String description , @Param("price") int price, @Param("available") int available);








}