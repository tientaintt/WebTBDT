package com.hcmute.edu.vn.WebTBDT.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Table
@Entity(name="customer")
@Data
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String username;
    private String passWord;
    private String avatar;
    private int role;
    private int activate;
    @OneToMany(mappedBy = "customer")
    private List<CartEntity> cartEntityList;
    @OneToMany(mappedBy = "customer")
    private List<CommentProductEntity>commentProductEntityList;
    @OneToMany(mappedBy = "customer")
    private List<OrderBillEntity>orderBillEntityList;
}
