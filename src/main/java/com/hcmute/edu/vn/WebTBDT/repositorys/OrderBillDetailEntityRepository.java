package com.hcmute.edu.vn.WebTBDT.repositorys;

import com.hcmute.edu.vn.WebTBDT.Model.ProductSell;
import com.hcmute.edu.vn.WebTBDT.entities.CustomerEntity;
import com.hcmute.edu.vn.WebTBDT.entities.OrderBillDetailEntity;
import com.hcmute.edu.vn.WebTBDT.entities.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderBillDetailEntityRepository extends JpaRepository<OrderBillDetailEntity, Integer> {
    List<OrderBillDetailEntity> findAllByOrderBillId(int id);
    @Query("SELECT obd.product FROM order_bill_detail obd  GROUP BY obd.product ORDER BY  SUM(obd.quantity) DESC ")
    List<ProductEntity> get8ProductMostQuantities(Pageable pageable);
    @Query("SELECT obd.product,obd.quantity as numSell FROM order_bill_detail obd GROUP BY obd.product ORDER BY SUM(obd.quantity) ")
    List<ProductSell> getProductSell();

    @Query("SELECT obd.product,SUM(obd.quantity) as numSell FROM order_bill_detail obd  GROUP BY obd.product  ORDER BY SUM(obd.quantity)  DESC ")
    List<Object[]> findTop4Producthot(Pageable pageable);
//   @Query(value=" SELECT FROM order_bill_detail obd LEFT OUTER JOIN (SELECT ob.id FROM order_bill as ob WHERE ob.customer_id=?2) as ob_id ON obd.order_bill_id=ob_id WHERE ",nativeQuery = true)
@Query(value="SELECT DISTINCT p.id FROM order_bill_detail obd " +
        "INNER JOIN oder_bill ob ON obd.oder_bill_id = ob.id " +
        "INNER JOIN product p ON obd.product_id = p.id " +
        "INNER JOIN category c ON p.category_id = c.id " +
        "WHERE ob.customer_id = ?1 " +
        "AND c.id IN (SELECT c2.id FROM product p2 " +
        "                      INNER JOIN category c2 ON p2.category_id = c2.id " +
        "                      WHERE p2.id IN (SELECT obd3.product_id FROM order_bill_detail obd3 " +
        "                                              INNER JOIN oder_bill ob3 ON obd3.oder_bill_id = ob3.id " +
        "                                              WHERE ob3.customer_id = ?1 " +
        "                                              GROUP BY obd3.product_id " +
        "                                              ORDER BY COUNT(*) DESC " +
        "                                              ))", nativeQuery = true)

//    @Query(value = "SELECT * FROM product as p RIGHT OUTER JOIN (SELECT p.id product FROM oder_bill AS o JOIN order_bill_detail AS oi ON o.order_id = oi.order_bill_id JOIN product AS p ON oi.product_id = p.id WHERE o.customer_id = ?1 AND p.id NOT IN (SELECT oi2.product_id  FROM oder_bill AS o2  JOIN order_bill_detail AS oi2 ON o2.order_id = oi2.id WHERE o2.customer_id)) as t ON p.id=t",nativeQuery = true)
    List<Integer> recomendProduct( int idcustomer,Pageable pageable);
}