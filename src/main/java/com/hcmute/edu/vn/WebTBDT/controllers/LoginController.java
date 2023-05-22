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
public class LoginController {
    @Autowired
    HttpSession session;
    @Autowired
    CustomerServiceImpl customerService;

    @GetMapping("/signup")
    private String signUp(Model model) {

        return "SignUp";
    }

    @PostMapping("login")
    private String login(Model model, @ModelAttribute(name = "username") String username,
                         @ModelAttribute(name = "password") String password) {
        if (password.isEmpty() || username.isEmpty()) {
            model.addAttribute("error", "Vui lòng nhập đủ thông tin !!");
            session.setAttribute("error", "Vui lòng nhập đủ thông tin !!");
            return "redirect:/Signin";
        }
        CustomerEntity customer = customerService.findUserByUsername(username);
        if (customer != null) {

            String decodedValue = new String(Base64.getDecoder().decode(customer.getPassWord()));
            if (decodedValue.equals(password.trim())) {
                session.setAttribute("account", customer);
                if (customer.getRole()==1) {
                    return "redirect:/Admin_DashBoard";
                } else {
                    return "redirect:/home";
                }
            } else {
                model.addAttribute("error", "Mật khẩu hoặc tài khoản không đúng !!");
                session.setAttribute("error", "Mật khẩu hoặc tài khoản không đúng !!");
                return "redirect:/Signin";
            }
        } else {
            model.addAttribute("error", "Mật khẩu hoặc tài khoản không đúng !!");
            session.setAttribute("error", "Mật khẩu hoặc tài khoản không đúng !!");
            return "redirect:/Signin";
        }

    }

}
