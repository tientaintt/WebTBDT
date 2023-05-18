package com.hcmute.edu.vn.WebTBDT.controllers;

import com.hcmute.edu.vn.WebTBDT.entities.*;
import com.hcmute.edu.vn.WebTBDT.services.CartDetailService;
import com.hcmute.edu.vn.WebTBDT.services.CartService;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.CartDetailServiceImpl;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.CartServiceImpl;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.CategoryServiceImpl;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.ProductServiceImpl;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    HttpSession session;
    @Autowired
    CategoryServiceImpl categoryService;
    @Autowired
    CartServiceImpl cartService;
    @Autowired
    CartDetailServiceImpl cartDetailService;


    @Autowired
    ProductServiceImpl productService;

    @GetMapping("/Cart")
    private String cart(Model model) {
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        List<CategoryEntity> clist = categoryService.findAll();
        model.addAttribute("categorylist", clist);
        if (customer != null) {
            CartEntity cart = cartService.findByCustomerId(customer.getId());
            if (cart != null) {
                model.addAttribute("CartDetail", cart.getCartDetailEntityList());
                model.addAttribute("CartTotalPrice", cart.getTotalPrice());
            }
        }

        return "gio_hang";
    }

    @GetMapping("DeleteCart/{id}")
    private String delete(@PathVariable int id) {
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if (customer != null) {
            cartDetailService.deleteCartDetail(id);
            CartEntity cart = cartService.findByCustomerId(customer.getId());
            if (cart != null) {
                List<CartDetailEntity> list = cart.getCartDetailEntityList();
                if (list == null) {
                    cartService.deleteCart(cart);
                } else {
                    int total = 0;
                    for (CartDetailEntity c : list)
                        total += c.getPrice();
                    cart.setTotalPrice(total);
                    cartService.saveCart(cart);
                }
            }
        }
        return "redirect:/Cart";
    }

    @GetMapping("DeleteCart")
    private String deleteAll() {
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if (customer != null) {
            CartEntity cart = cartService.findByCustomerId(customer.getId());
            if (cart != null) {
                cartDetailService.deleteCartDetail(cart.getCartDetailEntityList());
                cartService.deleteCart(cart);
            }
        }
        return "redirect:/Cart";
    }

    @GetMapping("Cart/{id}")
    private String addcart(Model model, @PathVariable int id) {
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if (customer != null) {
            CartEntity cart = cartService.findByCustomerId(customer.getId());
            List<CartDetailEntity> cartDetailList = new ArrayList<>();
            ProductEntity product = productService.findById(id);
            if (cart != null) {
                cartDetailList = cart.getCartDetailEntityList();
                if (cartDetailList != null) {
                    int flag = 0;
                    CartDetailEntity cartDetail = new CartDetailEntity();
                    for (CartDetailEntity c : cartDetailList) {
                        if (c.getProduct().getId() == id) {
                            c.setQuantity(c.getQuantity() + 1);
                            if (product.getQuantity() >= (c.getQuantity())) {
                                c.setPrice(c.getQuantity() * c.getProduct().getPrice());
                                cartDetail = c;
                                cartDetailService.saveCartDetail(cartDetail);
                                flag = 1;
                                System.out.println(flag);

                                break;
                            }else{
                                flag = -1;
                            }
                        }
                    }
                    if (flag == 0) {
                        cartDetailList.add(cartDetail);
                        cartDetail.setCart(cart);
                        cartDetail.setQuantity(1);
                        cartDetail.setProduct(product);
                        cartDetail.setPrice(cartDetail.getQuantity() * product.getPrice());

                        cartDetailService.saveCartDetail(cartDetail);
                    }
                }
            } else {
                cart = new CartEntity();
                cart.setCustomer(customer);
                cartService.saveCart(cart);
                CartDetailEntity cartDetail = new CartDetailEntity();
                cartDetail.setCart(cart);
                cartDetail.setProduct(product);
                cartDetail.setQuantity(1);
                cartDetail.setPrice(cartDetail.getQuantity() * product.getPrice());

                cartDetailList.add(cartDetail);

                cartDetailService.saveCartDetail(cartDetail);
            }

            int total = 0;
            for (CartDetailEntity c : cartDetailList)
                total += c.getPrice();
            cart.setTotalPrice(total);
            cartService.saveCart(cart);


        }
        return "redirect:/Cart";
    }
    @PostMapping("/UpdateCart")
    private String updateCart(Model model, HttpServletRequest request){
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if(customer != null){
            CartEntity cart = cartService.findByCustomerId(customer.getId());
            if(cart != null){
                List<CartDetailEntity> list = cart.getCartDetailEntityList();
                if(list != null){
                    int i = 0;
                    String[] q = request.getParameterValues("cartDetailQuantity");
                    for(CartDetailEntity c : list){
                        c.setQuantity(Integer.parseInt(q[i].trim()));
                        c.setPrice(c.getQuantity() * c.getProduct().getPrice());
                        i++;
                    }
                    int total = 0;
                    for (CartDetailEntity c : list)
                        total += c.getPrice();
                    cart.setTotalPrice(total);
                    cart.setCartDetailEntityList(list);
                    cartService.saveCart(cart);
                }
            }
        }
        return "redirect:/Cart";
    }

}
