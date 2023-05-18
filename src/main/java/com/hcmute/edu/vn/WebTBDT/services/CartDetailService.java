package com.hcmute.edu.vn.WebTBDT.services;

import com.hcmute.edu.vn.WebTBDT.entities.CartDetailEntity;
import com.hcmute.edu.vn.WebTBDT.repositorys.CartDetailEntityRepository;

import java.util.List;

public interface CartDetailService {
    List<CartDetailEntity> findAllByCartId(int id);

    void saveCartDetail(CartDetailEntity cartDetail);

    void deleteCartDetail(CartDetailEntity cartDetail);

    void deleteCartDetail(int id);

    void deleteCartDetail(List<CartDetailEntity> cartDetail);
}
