package com.hcmute.edu.vn.WebTBDT.controllers;

import com.hcmute.edu.vn.WebTBDT.entities.CategoryEntity;
import com.hcmute.edu.vn.WebTBDT.entities.CustomerEntity;
import com.hcmute.edu.vn.WebTBDT.services.CategoryService;
import com.hcmute.edu.vn.WebTBDT.services.CloudinaryService;
import com.hcmute.edu.vn.WebTBDT.services.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Base64;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    HttpSession session;
    @Autowired
    CustomerService customerService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    CloudinaryService cloudinaryService;
    @GetMapping("/Profile")
    public String profile(Model model){
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        List<CategoryEntity> clist = categoryService.findAll();
        model.addAttribute("categorylist", clist);
        if(customer != null){
            model.addAttribute("CustomerForm", customer);

        }
        return "my_profile";
    }

    @PostMapping("/UpdateProfile")
    public String update(Model model,
                         @ModelAttribute("name") String name,
                         @ModelAttribute("address") String address,
                         @ModelAttribute("email") String email,
                         @ModelAttribute("phone") String phone,
                         @ModelAttribute("avatar") MultipartFile avatar,
                         RedirectAttributes redirAttrs){
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if(customer != null){
            customer.setName(name);
            customer.setAddress(address);
            customer.setEmail(email);
            customer.setPhone(phone);
            if(!avatar.isEmpty()){
                String uimage = cloudinaryService.uploadFile(avatar);
                customer.setAvatar(uimage);
            }
            redirAttrs.addFlashAttribute("changeProfileSuccess", "Change Profile Success!!");
            customerService.saveUser(customer);
            session.setAttribute("account", customer);

        }

        return "redirect:/Profile";
    }
    @PostMapping("/UpdatePassword")
    public String updatePass(Model model, @ModelAttribute("current_password") String current_password,
                             @ModelAttribute("new_password") String new_password,
                             @ModelAttribute("confirm_password") String confirm_password,
                             RedirectAttributes redirAttrs){
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if(customer != null){
            String decodedValue = new String(Base64.getDecoder().decode(customer.getPassWord()));
            if (!decodedValue.equals(current_password)) {
                redirAttrs.addFlashAttribute("changePasswordError", "Current Password not correct!");
                return "redirect:/Profile";
            } else {
                if (!new_password.equals(confirm_password)) {
                    redirAttrs.addFlashAttribute("changePasswordError", "Confirm New Password not valid!");
                    return "redirect:/Profile";
                } else {
                    String encodedValue = Base64.getEncoder().encodeToString(new_password.getBytes());
                    customer.setPassWord(encodedValue);
                    customerService.saveUser(customer);
                    session.setAttribute("account", customer);
                    redirAttrs.addFlashAttribute("changePasswordSuccess", "Change Password Success!!");

                }
            }
        }
        return "redirect:/Profile";
    }
}
