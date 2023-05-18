package com.hcmute.edu.vn.WebTBDT.controllers;

import com.hcmute.edu.vn.WebTBDT.entities.CategoryEntity;
import com.hcmute.edu.vn.WebTBDT.entities.ProductEntity;
import com.hcmute.edu.vn.WebTBDT.repositorys.CategoryRepository;
import com.hcmute.edu.vn.WebTBDT.services.CategoryService;
import com.hcmute.edu.vn.WebTBDT.services.ProductService;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.CategoryServiceImpl;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.ProductServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    HttpSession session;
    @Autowired
    CategoryServiceImpl categoryService;
    @Autowired
    ProductServiceImpl productService;

    @GetMapping("/home")
    private String home(Model model){

        List<CategoryEntity> clist = categoryService.findAll();
        model.addAttribute("categorylist",clist);

        return "index";
    }
    @GetMapping("/")
    private  String home1(Model model){
        return "redirect:/home";
    }


}
