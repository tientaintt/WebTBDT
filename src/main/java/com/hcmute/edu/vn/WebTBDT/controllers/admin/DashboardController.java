package com.hcmute.edu.vn.WebTBDT.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {


    @GetMapping("Admin_DashBoard")
    public String showDashboard(Model model)
    {

        return "Admin_DashBoard";
    }
}
