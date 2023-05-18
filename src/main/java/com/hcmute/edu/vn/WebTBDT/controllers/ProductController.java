package com.hcmute.edu.vn.WebTBDT.controllers;

import com.hcmute.edu.vn.WebTBDT.entities.CategoryEntity;
import com.hcmute.edu.vn.WebTBDT.entities.CommentProductEntity;
import com.hcmute.edu.vn.WebTBDT.entities.ProductEntity;
import com.hcmute.edu.vn.WebTBDT.services.CommentService;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.CategoryServiceImpl;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.ProductServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    HttpSession session;
    @Autowired
    CategoryServiceImpl categoryService;
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    CommentService commentService;

    @GetMapping("/Product/{id}")
    private String productDetail(Model model, @PathVariable int id, RedirectAttributes redirAttrs) {
        List<CategoryEntity> clist = categoryService.findAll();
        model.addAttribute("categorylist", clist);

        ProductEntity product = productService.findById(id);
        CategoryEntity procCat = product.getCategory();
        model.addAttribute("Product", product);
        model.addAttribute("ProductCategory", procCat);

        List<ProductEntity> list = productService.find4ByCategoryId(procCat.getId(), product.getId());
        model.addAttribute("SimilarProduct", list);
        redirAttrs.addFlashAttribute("productId", id);
        session.setAttribute("productId", id);
        List<CommentProductEntity> comment = commentService.findAllProductId(id);
        model.addAttribute("commentList", comment);
        //        model.addAttribute("categoryName", categoryService.findById(idCategory).getName());
//        model.addAttribute("productList", product);
//        System.out.println(product.size());
        return "show_product_detail";
    }

    @GetMapping("/Category/{idCategory}")
    private String CategoryPage(Model model, @PathVariable int idCategory, RedirectAttributes redirAttrs) {
//        redirAttrs.addFlashAttribute("idCategory", idCategory);
        return "redirect:/Category/" + idCategory + "/page=" + 0;
    }

    @GetMapping("Category/{idCategory}/page={pageNum}")
    private String categoryPage(Model model, @PathVariable int idCategory, @PathVariable int pageNum) {
        List<CategoryEntity> clist = categoryService.findAll();
        model.addAttribute("categorylist", clist);
        Pageable pageable = PageRequest.of(pageNum, 8);
//        List<ProductEntity> product = productService.findProductByCategoryId(idCategory);
        Page<ProductEntity> product = productService.findProductByCategoryId(idCategory, pageable);

        model.addAttribute("categoryName", categoryService.findById(idCategory).getName());
        model.addAttribute("productList", product);
        model.addAttribute("thisIsShowCategory", true);
//        System.out.println(product.size());
        return "show_product";
    }

    //    @GetMapping(value = "/search")
//    private String search(Model model, @RequestParam("search_input") String name, RedirectAttributes redirAttrs){
////        List<CategoryEntity> clist = categoryService.findAll();
////        model.addAttribute("categorylist",clist);
////        System.out.println(name);
////        if(name != null || !name.isEmpty() || !name.isBlank()){
////            model.addAttribute("search_input", "Tất cả sản phẩm");
////        }
////        model.addAttribute("search_input", name);
////        List<ProductEntity> product = productService.findAllByProductName(name);
////        model.addAttribute("productList", product);
//        redirAttrs.addFlashAttribute("search_input", name);
//        return "redirect:/search/page/0";
//    }
    @GetMapping(value = "/search/page={pageNum}")
    private String searchPage(Model model, @RequestParam("search_input") String name, @PathVariable int pageNum) {
        List<CategoryEntity> clist = categoryService.findAll();
        model.addAttribute("categorylist", clist);
        Pageable pageable = PageRequest.of(pageNum, 8);
//        List<ProductEntity> product = productService.findProductByCategoryId(idCategory);
        Page<ProductEntity> product = productService.findByProductName(name, pageable);

//        model.addAttribute("categoryName", categoryService.findById(idCategory).getName());
        model.addAttribute("search_input", name);
        model.addAttribute("productList", product);
        model.addAttribute("thisIsShowCategory", false);

        return "show_product";
    }

}
