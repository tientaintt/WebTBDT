package com.hcmute.edu.vn.WebTBDT.services.serviceImpl;

import com.hcmute.edu.vn.WebTBDT.entities.CartDetailEntity;
import com.hcmute.edu.vn.WebTBDT.repositorys.CartDetailEntityRepository;
import com.hcmute.edu.vn.WebTBDT.services.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartDetailServiceImpl implements CartDetailService {
    @Autowired
    CartDetailEntityRepository repository;
    @Override
    public List<CartDetailEntity> findAllByCartId(int id) {
        return repository.findAllByCartId(id);
    }
    @Override
    public void saveCartDetail(CartDetailEntity cartDetail){
        repository.save(cartDetail);
    }
    @Override
    public void deleteCartDetail(CartDetailEntity cartDetail){
        System.out.println("Xoa cart co id: " + cartDetail.getId());
        repository.delete(cartDetail);
    }
    @Override
    public void deleteCartDetail(int id){
        repository.deleteById(id);
    }
    @Override
    public void deleteCartDetail(List<CartDetailEntity> cartDetail){
        for(CartDetailEntity c : cartDetail)
            repository.delete(c);
    }
}
