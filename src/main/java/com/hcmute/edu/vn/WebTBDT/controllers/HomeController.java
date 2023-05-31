package com.hcmute.edu.vn.WebTBDT.controllers;

import com.hcmute.edu.vn.WebTBDT.entities.CategoryEntity;
import com.hcmute.edu.vn.WebTBDT.entities.CustomerEntity;
import com.hcmute.edu.vn.WebTBDT.entities.ProductEntity;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.CategoryServiceImpl;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.OrderBillDetailServiceImpl;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.ProductServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    HttpSession session;
    @Autowired
    CategoryServiceImpl categoryService;
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    OrderBillDetailServiceImpl orderBillDetailService;
    @GetMapping("/home")
    private String home(Model model){

        List<CategoryEntity> clist = categoryService.findAll();
        model.addAttribute("categorylist",clist);
        List<ProductEntity> pList= orderBillDetailService.find8ProductBestSell();
        model.addAttribute("top8ProductBS",pList);
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if(customer!=null ){
            List<ProductEntity> recommendProduct=new ArrayList<>();
            List<Integer>listid=orderBillDetailService.recomendProduct(customer.getId());
            for(int i:listid){
                ProductEntity product=new ProductEntity();
                product=productService.findById(i);
                recommendProduct.add(product);
            }
            model.addAttribute("reProduct",recommendProduct);
        }
        return "index";

    }
    @GetMapping("/")
    private  String home1(){
        return "redirect:/home";
    }



}
