package com.hcmute.edu.vn.WebTBDT.controllers.admin;

import com.hcmute.edu.vn.WebTBDT.entities.OrderBillEntity;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.OrderBillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RevenueController {

    @Autowired
    OrderBillServiceImpl orderBillService;


    @GetMapping("/Admin_Revenue")
    private String showRevenue(Model model)
    {
        List<OrderBillEntity> orderBill =orderBillService.getPaidOrderBills();

        model.addAttribute("oderBill" , orderBill);

        return "Admin_Revenue";
    }




}
