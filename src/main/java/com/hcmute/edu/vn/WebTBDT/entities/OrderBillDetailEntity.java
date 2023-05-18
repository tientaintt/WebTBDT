package com.hcmute.edu.vn.WebTBDT.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Table
@Entity(name="order_bill_detail")
@Data
public class OrderBillDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;
    private int available;
    @ManyToOne
    @JoinColumn(name = "productId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ProductEntity product;
    @ManyToOne
    @JoinColumn(name = "oderBillId")

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private OrderBillEntity orderBill;
}
