package com.hcmute.edu.vn.WebTBDT.controllers;

import com.hcmute.edu.vn.WebTBDT.entities.CommentProductEntity;
import com.hcmute.edu.vn.WebTBDT.entities.CustomerEntity;
import com.hcmute.edu.vn.WebTBDT.entities.ProductEntity;
import com.hcmute.edu.vn.WebTBDT.services.CommentService;
import com.hcmute.edu.vn.WebTBDT.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CommentController {
    @Autowired
    HttpSession session;
    @Autowired
    CommentService commentService;
    @Autowired
    ProductService productService;

    @PostMapping("/Comment")
    public String comment(Model model, @ModelAttribute("comment") String comment, HttpServletRequest request){
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if(customer != null){
            CommentProductEntity commentProduct = new CommentProductEntity();
            int id = (int) session.getAttribute("productId");
            ProductEntity product = productService.findById(id);
            if(product != null){
                if(!comment.isBlank() || !comment.isEmpty()){
                    commentProduct.setCustomer(customer);
                    commentProduct.setProduct(product);
                    commentProduct.setDescription(comment);

                    commentService.saveComment(commentProduct);
                }
            }
        }
        return "redirect:"+request.getHeader("Referer");
    }
}
