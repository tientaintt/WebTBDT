package com.hcmute.edu.vn.WebTBDT.controllers.admin;

import com.hcmute.edu.vn.WebTBDT.entities.CategoryEntity;
import com.hcmute.edu.vn.WebTBDT.entities.CustomerEntity;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.CategoryServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    HttpSession session;
    @Autowired
    CategoryServiceImpl categoryService;

    @GetMapping("/Admin_Category")
    private String showCategory(Model model)
    {
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if(customer==null || customer.getRole()!=1){
            return "redirect:/home";
        }
        List<CategoryEntity> listAll = categoryService.findAll();
        model.addAttribute("listCategory" , listAll);

        return "Admin_Category";
    }

    @GetMapping("/Add_category")
    public String addCategory(Model model)
    {
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if(customer==null || customer.getRole()!=1){
            return "redirect:/home";
        }
        model.addAttribute("category" , new CategoryEntity());
        return "Add_category";
    }

    @PostMapping("/Add_category/save")
    private String saveCategory(CategoryEntity itemCategory , RedirectAttributes rd){
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if(customer==null || customer.getRole()!=1){
            return "redirect:/home";
        }
        categoryService.save(itemCategory);

        rd.addFlashAttribute("mesage" , "Đã thêm thành công");
        return "redirect:/Admin_Category";
    }

}
