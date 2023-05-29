package com.hcmute.edu.vn.WebTBDT.controllers.admin;

import com.hcmute.edu.vn.WebTBDT.Model.ProductSell;
import com.hcmute.edu.vn.WebTBDT.entities.CustomerEntity;
import com.hcmute.edu.vn.WebTBDT.entities.OrderBillDetailEntity;
import com.hcmute.edu.vn.WebTBDT.entities.OrderBillEntity;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.OrderBillDetailServiceImpl;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.OrderBillServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class DashboardController {
    @Autowired
    HttpSession session;

    @Autowired
    OrderBillServiceImpl orderBillService;

    @Autowired
    OrderBillDetailServiceImpl orderBillDetailService;

    @GetMapping("Admin_DashBoard")
    public String showDashboard(Model model)
    {
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if(customer==null || customer.getRole()!=1){
            return "redirect:/home";
        }
        List<OrderBillEntity> orderBillEntities = orderBillService.getPaidOrderBills();
        List<ProductSell> orderBillDetailEntities = orderBillDetailService.getTop4ProductHot();
        int totalPaidOrder = orderBillService.getTotalPaidOrders();
        BigDecimal totalPaidAmount =orderBillService.getTotalPaidAmount();


        model.addAttribute("productHot" ,orderBillDetailEntities);
        model.addAttribute("totalPaid" , totalPaidAmount);
        model.addAttribute("orderPaided" , orderBillEntities);
        model.addAttribute("countOrder",totalPaidOrder);

        return "Admin_DashBoard";
    }
}
