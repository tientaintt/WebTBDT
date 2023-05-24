package com.hcmute.edu.vn.WebTBDT.services.serviceImpl;

import com.hcmute.edu.vn.WebTBDT.Model.ProductSell;
import com.hcmute.edu.vn.WebTBDT.entities.OrderBillDetailEntity;
import com.hcmute.edu.vn.WebTBDT.entities.ProductEntity;
import com.hcmute.edu.vn.WebTBDT.repositorys.OrderBillDetailEntityRepository;
import com.hcmute.edu.vn.WebTBDT.services.OrderBillDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderBillDetailServiceImpl implements OrderBillDetailService {
    @Autowired
    OrderBillDetailEntityRepository repository;
    @Override
    public List<OrderBillDetailEntity> findAllByOrderBillId(int id){
        return repository.findAllByOrderBillId(id);
    }

    @Override
    public List<ProductEntity> find8ProductBestSell() {
        return repository.get8ProductMostQuantities();
    }

    @Override
    public void saveOrderBillDetail(OrderBillDetailEntity orderBillDetailEntity){
        repository.save(orderBillDetailEntity);
    }

    @Override
    public List<ProductSell> getProductSell() {
        return repository.getProductSell();
    }

}
