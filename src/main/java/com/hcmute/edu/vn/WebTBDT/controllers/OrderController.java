package com.hcmute.edu.vn.WebTBDT.controllers;

import com.hcmute.edu.vn.WebTBDT.entities.*;
import com.hcmute.edu.vn.WebTBDT.services.MailService;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    HttpSession session;
    @Autowired
    CustomerServiceImpl customerService;
    @Autowired
    CategoryServiceImpl categoryService;
    @Autowired
    CartServiceImpl cartService;
    @Autowired
    CartDetailServiceImpl cartDetailService;
    @Autowired
    ProductServiceImpl productService;

    @Autowired
    OrderBillServiceImpl orderBillService;
    @Autowired
    OrderBillDetailServiceImpl orderBillDetailService;
    @Autowired
    MailService mailService;




//    Admin

//    @GetMapping("/Admin_Order")
//    private String showOrder(Model model)
//    {
//
//        List<OrderBillEntity> order = orderBillService.finAll();
//
//        model.addAttribute("order" , order);
//
//        return "Admin_Order";
//    }

    @GetMapping("/Admin_Order/page/{pageNumber}")
    private String getOnePage(@PathVariable(value = "pageNumber") int pageNumber , Model model)
    {
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if(customer==null || customer.getRole()!=1){
            return "redirect:/home";
        }
        int pageSize = 5;


        Page<OrderBillEntity> orderBillEntityPage = orderBillService.findPage(pageNumber,pageSize);

        int totalPage = orderBillEntityPage.getTotalPages();
        Long totalItems = orderBillEntityPage.getTotalElements();
        List<OrderBillEntity> orderBillEntities = orderBillEntityPage.getContent();
        model.addAttribute("currentPage" , pageNumber);
        model.addAttribute("totalPages" , totalPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("orderBill" ,orderBillEntities);

        return "Admin_Order";
    }

    @GetMapping("/Admin_Order")
    public String viewHomePage(Model model)
    {
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if(customer==null || customer.getRole()!=1){
            return "redirect:/home";
        }
        return getOnePage(1,model);
    }











    // User
    @GetMapping("/Order")
    private String order(Model model) {
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        List<CategoryEntity> clist = categoryService.findAll();
        model.addAttribute("categorylist", clist);
        if (customer != null) {
            model.addAttribute("CustomerForm", customer);
            CartEntity cart = cartService.findByCustomerId(customer.getId());
            if (cart != null) {
                List<CartDetailEntity> list = cart.getCartDetailEntityList();
                if (list != null) {
                    model.addAttribute("CartDetail", list);
                    model.addAttribute("CartTotal", cart.getTotalPrice());
                }
            }
        }
        return "Check_out";
    }

    @PostMapping("/Order")
    private String orderPost(Model model, @ModelAttribute("CustomerForm") CustomerEntity customerUpdate) {
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if (customer != null) {
            customer.setName(customerUpdate.getName());
            customer.setAddress(customerUpdate.getAddress());
            customer.setEmail(customerUpdate.getEmail());
            customer.setPhone(customerUpdate.getPhone());

            customerService.saveUser(customer);

            CartEntity cart = cartService.findByCustomerId(customer.getId());
            if (cart != null) {
//                List<CartDetailEntity> cartList = cart.getCartDetailEntityList();
                List<CartDetailEntity> cartList = cartDetailService.findAllByCartId(cart.getId());
                int flag = 0;
                if (cartList != null) {
                    OrderBillEntity orderBill = orderBillService.saveOrderBill(new OrderBillEntity());
                    List<OrderBillDetailEntity> orderBillList = new ArrayList<>();
                    int total = 0;
                    for (CartDetailEntity c : cartList) {
                        ProductEntity product = productService.findById(c.getProduct().getId());
                        if (c.getQuantity() <= product.getQuantity()) {
                            OrderBillDetailEntity detail = new OrderBillDetailEntity();
                            detail.setProduct(c.getProduct());
                            detail.setQuantity(c.getQuantity());
                            detail.setOrderBill(orderBill);
                            total += c.getQuantity() * product.getPrice();
                            orderBillList.add(detail);

                            orderBillDetailService.saveOrderBillDetail(detail);

                            product.setQuantity(product.getQuantity() - c.getQuantity());
                            productService.saveProduct(product);
                            int id = c.getId();
                            cartDetailService.deleteCartDetail(id);

                            flag = 1;
                        }
                    }

                    orderBill.setCustomer(customer);
                    orderBill.setTotalPrice(total);
                    orderBill.setOrderBillDetailEntityList(orderBillList);

                    orderBillService.saveOrderBill(orderBill);

                    cart = cartService.findByCustomerId(customer.getId());
                    cartList = cartDetailService.findAllByCartId(cart.getId());
//                    System.out.println(cartList);
                    if (cartList.isEmpty()) {
                        cartService.deleteCart(cart);
                    }
                    if (flag == 0) {
                        orderBillService.deleteOrderBill(orderBill.getId());
                    } else {
                        mailService.sendOrderMail(orderBill);
                        return "redirect:/Invoice/"+orderBill.getId();
                    }
                }
            }

        }
        return "redirect:/Order";
    }
    @GetMapping("/Orders")
    private String orders(Model model){
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        List<CategoryEntity> clist = categoryService.findAll();
        model.addAttribute("categorylist", clist);
        if (customer != null) {
            List<OrderBillEntity> orderBill = orderBillService.findAllByCustomerId(customer.getId());
            if (orderBill != null) {
                model.addAttribute("BillDetail", orderBill);
//                model.addAttribute("CartTotalPrice", cart.getTotalPrice());
            }
        }
        return "Order";
    }

    @GetMapping("/Invoice")
    private String Invoice(Model model) {
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if (customer != null) {
            OrderBillEntity orderBill = (OrderBillEntity) session.getAttribute("orderBill");
            if (orderBill != null) {
                List<OrderBillDetailEntity> list = orderBillDetailService.findAllByOrderBillId(orderBill.getId());
                if (list != null) {
                    int id = orderBill.getId();
                    model.addAttribute("billId", id);
                    model.addAttribute("bill", orderBill);
                    model.addAttribute("billDetail", list);
                }

            }
        }
        return "Bill";
    }

    @GetMapping("/Invoice/{id}")
    private String invoice(@PathVariable("id") int id, Model model) {
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        System.out.println(id);
        if (customer != null) {
            OrderBillEntity orderBill = orderBillService.findByIdAndCustomerId(id, customer.getId());
            if (orderBill != null) {
                session.setAttribute("orderBill", orderBill);
            }
        }
        return "redirect:/Invoice";
    }
}
