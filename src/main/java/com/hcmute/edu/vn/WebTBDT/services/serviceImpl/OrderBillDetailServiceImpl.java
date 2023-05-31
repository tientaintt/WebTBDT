package com.hcmute.edu.vn.WebTBDT.services.serviceImpl;

import com.hcmute.edu.vn.WebTBDT.Model.ProductSell;
import com.hcmute.edu.vn.WebTBDT.entities.CustomerEntity;
import com.hcmute.edu.vn.WebTBDT.entities.OrderBillDetailEntity;
import com.hcmute.edu.vn.WebTBDT.entities.ProductEntity;
import com.hcmute.edu.vn.WebTBDT.repositorys.OrderBillDetailEntityRepository;
import com.hcmute.edu.vn.WebTBDT.repositorys.OrderBillEntityRepository;
import com.hcmute.edu.vn.WebTBDT.repositorys.ProductEntityRepository;
import com.hcmute.edu.vn.WebTBDT.services.OrderBillDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderBillDetailServiceImpl implements OrderBillDetailService {
    @Autowired
    OrderBillDetailEntityRepository repository;
    @Autowired
    private OrderBillEntityRepository orderBillEntityRepository;
    @Autowired
    private ProductEntityRepository productEntityRepository;

    @Override
    public List<OrderBillDetailEntity> findAllByOrderBillId(int id){
        return repository.findAllByOrderBillId(id);
    }

    @Override
    public List<ProductEntity> find8ProductBestSell() {
        PageRequest pageRequest = PageRequest.of(0,8);

        return repository.get8ProductMostQuantities(pageRequest);
    }

    @Override
    public void saveOrderBillDetail(OrderBillDetailEntity orderBillDetailEntity){
        repository.save(orderBillDetailEntity);
    }

    @Override
    public List<ProductSell> getProductSell() {
        return repository.getProductSell();
    }

    @Override
    public List<Integer> recomendProduct(int idcustomer) {
        PageRequest pageRequest = PageRequest.of(0,8);
        return repository.recomendProduct(idcustomer,pageRequest);
    }

    @Override
    public List<ProductSell> getTop4ProductHot() {
        PageRequest pageRequest = PageRequest.of(0,4);

        List<Object[]> result = repository.findTop4Producthot(pageRequest);

        List<ProductSell> productSells = new ArrayList<>();
        for (Object[] obj : result) {
            ProductEntity productEntity = (ProductEntity) obj[0];
            int numSell = ((Number) obj[1]).intValue();

            ProductSell productSell = new ProductSell(productEntity, numSell);
            productSells.add(productSell);
        }

        return productSells;
    }



}
