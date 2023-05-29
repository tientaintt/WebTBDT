package com.hcmute.edu.vn.WebTBDT.controllers.admin;

import com.hcmute.edu.vn.WebTBDT.entities.CustomerEntity;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.CustomerServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    HttpSession session;
    @Autowired
    CustomerServiceImpl customerService;
    @GetMapping("/Admin_User")
    private String showUser(Model model)
    {
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if(customer==null || customer.getRole()!=1){
            return "redirect:/home";
        }
        List<CustomerEntity> listAllUser = customerService.findAll();
        model.addAttribute("listAllUser" , listAllUser);
        return "Admin_User";
    }

    @GetMapping("/DeleteUser/{id}")
    private String deleteUser(@PathVariable (value = "id") long id ,  RedirectAttributes rd)
    {
        rd.addFlashAttribute("mesage" , "Đã xóa thành công");

        this.customerService.deleteUserById(id);

        return "redirect:/Admin_User" ;
    }


}
