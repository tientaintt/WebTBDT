package com.hcmute.edu.vn.WebTBDT.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RevenueController {


    @GetMapping("/Admin_Revenue")
    private String showRevenue(Model model)
    {
        return "Admin_Revenue";
    }
}
