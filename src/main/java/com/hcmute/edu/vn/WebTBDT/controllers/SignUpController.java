package com.hcmute.edu.vn.WebTBDT.controllers;

import com.hcmute.edu.vn.WebTBDT.Model.Mail;
import com.hcmute.edu.vn.WebTBDT.entities.CustomerEntity;
import com.hcmute.edu.vn.WebTBDT.services.CloudinaryService;
import com.hcmute.edu.vn.WebTBDT.services.MailService;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.CustomerServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Base64;

@Controller
public class SignUpController {

    @Autowired
    HttpSession session;
    @Autowired
    CustomerServiceImpl customerService;
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    MailService mailService;

    @GetMapping("/Signin")
    private String Signin(Model model) {
        if (session.getAttribute("error") != null) {
            model.addAttribute("error", session.getAttribute("error"));
            session.removeAttribute("error");
        }
        session.removeAttribute("code");
        session.removeAttribute("username");
        return "login";
    }

    @GetMapping("/SignOut")
    private String signOut(Model model) {
        session.removeAttribute("account");
        return "redirect:/home";
    }

    @PostMapping("SignUp")
    private String signUp(Model model, @ModelAttribute(name = "Customername") String customername,
                          @ModelAttribute(name = "phonenumber") String phonenumber,
                          @ModelAttribute(name = "username") String username,
                          @ModelAttribute(name = "password") String password,
                          @ModelAttribute(name = "confirmpass") String confirmpass) {
        if (password == null || username == null) {
            model.addAttribute("error", "Vui lòng nhập đủ thông tin !!");
            return "SignUp";
        }
        CustomerEntity customer = customerService.findUserByUsername(username);
        System.out.println(username);
        if (customer == null) {
            if (password.trim().equals(confirmpass.trim())) {

                password = Base64.getEncoder().encodeToString(password.trim().getBytes());
                CustomerEntity customer1 = new CustomerEntity();
                customer1.setActivate(1);
                customer1.setName(customername.trim());
                customer1.setPhone(phonenumber.trim());
                customer1.setRole(0);
                customer1.setPassWord(password.trim());
                customer1.setUsername(username.trim());


                customer1.setAvatar("https://res.cloudinary.com/dqy4p8xug/image/upload/v1684272398/userImage_oujnyf.png");

                customerService.saveUser(customer1);

                return "redirect:/Signin";
            } else {
                model.addAttribute("error", "Mật khẩu xác nhận không đúng !!");
                return "SignUp";
            }
        } else {
            model.addAttribute("error", "Username đã tồn tại !!");
            return "SignUp";
        }

    }

    @GetMapping("signin")
    private String SignIn(Model model) {
        return "redirect:/Signin";
    }

    @GetMapping("/forget_password")
    private String forget(Model model, @ModelAttribute("enteredCode") String enteredCode) {
        return "forget_password";
    }

    @PostMapping("/forget_password/username")
    private String check(Model model,
                         @ModelAttribute("username") String username) {
        CustomerEntity customer = customerService.findUserByUsername(username);
        System.out.println("==" + username);
        if (customer != null) {
            session.setAttribute("username", username);
            return "redirect:/forget_password/code";
        } else {
            model.addAttribute("errorUsername", true);
        }
        return "forget_password";
    }
    @GetMapping("/forget_password/code")
    private String codec(Model model){
        int c = (int) Math.floor(((Math.random() * 8999) + 1000));
        session.setAttribute("code", c);
        String username = (String) session.getAttribute("username");
        CustomerEntity customer = customerService.findUserByUsername(username);
        Mail mail = new Mail();
        mail.setMailFrom("nguyentientaigoboi@gmail.com");
        mail.setMailTo(customer.getEmail());
        mail.setMailSubject("Tạo mới mật khẩu - Web Bán hàng Điện Tử Online: ");
        mail.setMailContent("Mã code của bạn là: " + c + "\nHãy nhập mã được gửi ở đây để tạo mới mật khẩu!!");
        mailService.sendMail(mail);
        return "forget_password";
    }
    @PostMapping("/forget_password/code")
    private String code(Model model, @ModelAttribute("code") String code) {
        int c = (int) session.getAttribute("code");
        if(c == Integer.parseInt(code)){
            session.setAttribute("reset", true);
            return "redirect:/forget_password/reset";
        }else{
            model.addAttribute("errorCode", true);
        }
        return "forget_password";
    }
    @GetMapping("/forget_password/reset")
    private String resett(Model model) {
        return "forget_password";
    }
    @PostMapping("/forget_password/reset")
    private String reset(Model model, @ModelAttribute("newPass") String newPass,
                         @ModelAttribute("reNewPass")String reNewPass) {
        if(newPass.isBlank() || newPass.isEmpty() || reNewPass.isBlank() || reNewPass.isEmpty()){
            model.addAttribute("errorPass", "Mật khẩu không hợp lệ");
        }else if(!reNewPass.equals(newPass)){
            model.addAttribute("errorPass", "Mật khẩu xác nhận không đúng!!");
        }else{
            String username = (String)session.getAttribute("username");
            CustomerEntity customer = customerService.findUserByUsername(username);
            newPass = Base64.getEncoder().encodeToString(newPass.trim().getBytes());
            customer.setPassWord(newPass);
            customerService.saveUser(customer);
            return "redirect:/Signin";
        }
        return "forget_password";
    }



}
