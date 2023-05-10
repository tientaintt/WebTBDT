package com.hcmute.edu.vn.WebTBDT.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Table
@Entity(name = "cart_detail")
@Data
public class CartDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;
    private int price;
    @ManyToOne
    @JoinColumn(name = "productId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ProductEntity product;
    @ManyToOne
    @JoinColumn(name = "cartId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private CartEntity cart;
}
