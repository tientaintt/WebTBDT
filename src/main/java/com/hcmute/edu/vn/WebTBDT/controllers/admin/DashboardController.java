package com.hcmute.edu.vn.WebTBDT.controllers.admin;

import com.hcmute.edu.vn.WebTBDT.entities.OrderBillDetailEntity;
import com.hcmute.edu.vn.WebTBDT.entities.OrderBillEntity;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.OrderBillDetailServiceImpl;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.OrderBillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class DashboardController {


    @Autowired
    OrderBillServiceImpl orderBillService;

    @Autowired
    OrderBillDetailServiceImpl orderBillDetailService;

    @GetMapping("Admin_DashBoard")
    public String showDashboard(Model model)
    {
        List<OrderBillEntity> orderBillEntities = orderBillService.getPaidOrderBills();
        List<OrderBillDetailEntity> orderBillDetailEntities = orderBillDetailService.getTop4ProductHot();
        int totalPaidOrder = orderBillService.getTotalPaidOrders();
        BigDecimal totalPaidAmount =orderBillService.getTotalPaidAmount();


        model.addAttribute("productHot" ,orderBillDetailEntities);
        model.addAttribute("totalPaid" , totalPaidAmount);
        model.addAttribute("orderPaided" , orderBillEntities);
        model.addAttribute("countOrder",totalPaidOrder);

        return "Admin_DashBoard";
    }
}
