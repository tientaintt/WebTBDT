package com.hcmute.edu.vn.WebTBDT.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.Locale;

@Data
@Table
@Entity(name="product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int available;
    private int quantity;
    private int price;

    @Column(length = 10000)
    private String description;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private CategoryEntity category;

    @OneToMany(mappedBy = "product")
    private List<ImageEntity>imagelist;
    @OneToMany(mappedBy = "product")
    private List<CartDetailEntity> cartDetailEntityList;
    @OneToMany(mappedBy = "product")
    private List<CommentProductEntity>commentProductEntityList;
    @OneToMany(mappedBy = "product")
    private List<OrderBillDetailEntity>orderBillDetailEntityList;

}
