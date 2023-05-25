package com.hcmute.edu.vn.WebTBDT.Model;

import com.hcmute.edu.vn.WebTBDT.entities.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class ProductSell {
    private ProductEntity product;
    private int numSell;
}
