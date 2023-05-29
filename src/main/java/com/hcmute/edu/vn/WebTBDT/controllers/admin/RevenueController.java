package com.hcmute.edu.vn.WebTBDT.controllers.admin;

import com.hcmute.edu.vn.WebTBDT.entities.CustomerEntity;
import com.hcmute.edu.vn.WebTBDT.entities.OrderBillEntity;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.OrderBillServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
public class RevenueController {
    @Autowired
    HttpSession session;

    @Autowired
    OrderBillServiceImpl orderBillService;


    @GetMapping("/Admin_Revenue")
    private String showRevenue(Model model){
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if(customer!=null && customer.getRole()==1){
            List<OrderBillEntity> orderBill = orderBillService.getPaidOrderBills();

            model.addAttribute("orderBill", orderBill);
        }else {
            return "redirect:/home";
        }

        return "Admin_Revenue";
    }


    @PostMapping("/findOrderOfDate")
    public String findOrderByDate(Model model, @ModelAttribute("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
                                  @ModelAttribute("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate,
                                  RedirectAttributes rd) throws ParseException {
//        String pattern = "yyyy-MM-dd";
//        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
//        Date startDate1 = dateFormat.parse(startDate);
//        Date endDate1 = dateFormat.parse(endDate);
//        System.out.println(startDate);
//        System.out.println(endDate);
//        System.out.println(dateFormat.format(startDate1));
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if(customer==null || customer.getRole()!=1){
            return "redirect:/home";
        }
        List<OrderBillEntity> orderBill = orderBillService.findOrderOfDate(startDate, endDate);
        if (orderBill == null) {
            rd.addFlashAttribute("error", "Không có đơn hàng trong khoảng thời gian này");
            System.out.println(1);
        }
        model.addAttribute("orderBill", orderBill);
        return "Admin_Revenue";

//        return "redirect:/Admin_Revenue";
    }

}
