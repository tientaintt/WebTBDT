package com.hcmute.edu.vn.WebTBDT.controllers;

import com.hcmute.edu.vn.WebTBDT.entities.CustomerEntity;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.CustomerServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Base64;

@Controller
public class SignUpController {

    @Autowired
    HttpSession httpSession;
    @Autowired
    CustomerServiceImpl customerService;
    @PostMapping("SignUp")
    private String signUp(Model model, @ModelAttribute(name ="Customername") String customername,
                          @ModelAttribute(name = "phonenumber") String phonenumber,
                          @ModelAttribute(name="username") String username,
                          @ModelAttribute(name="password") String password,
                          @ModelAttribute(name="confirmpass")String confirmpass){
        if(password==null|| username==null){
            model.addAttribute("error", "Vui lòng nhập đủ thông tin !!");
            return "SignUp";
        }
        CustomerEntity customer=customerService.findUserByUsername(username);
        System.out.println(username);
        if(customer==null){
            if(password.trim().equals(confirmpass.trim())){

                password=Base64.getEncoder().encodeToString(password.trim().getBytes());
                CustomerEntity customer1=new CustomerEntity();
                customer1.setActivate(1);
                customer1.setName(customername.trim());
                customer1.setPhone(phonenumber.trim());
                customer1.setRole(0);
                customer1.setPassWord(password.trim());
                customer1.setUsername(username.trim());
                customerService.saveUser(customer1);

                return "redirect:/Signin";
            }
            else {
                model.addAttribute("error", "Mật khẩu xác nhận không đúng !!");
                return "SignUp";
            }
        }
        else{
            model.addAttribute("error", "Username đã tồn tại !!");
            return "SignUp";
        }

    }
    @GetMapping("signin")
    private String SignIn(Model model){
        return "redirect:/Signin";
    }
}
