package com.hcmute.edu.vn.WebTBDT.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Table
@Entity(name="oder_bill")
@Data
public class OrderBillEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int available;
    private int totalPrice;
    private Date orderDate=new Date(new java.util.Date().getTime());
    @OneToMany(mappedBy = "orderBill", cascade = CascadeType.ALL)
    private List<OrderBillDetailEntity>orderBillDetailEntityList;
    @ManyToOne
    @JoinColumn(name = "customerId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private CustomerEntity customer;
}
