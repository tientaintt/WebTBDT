package com.hcmute.edu.vn.WebTBDT.controllers;

import com.hcmute.edu.vn.WebTBDT.entities.*;
import com.hcmute.edu.vn.WebTBDT.services.CloudinaryService;
import com.hcmute.edu.vn.WebTBDT.services.CommentService;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.CategoryServiceImpl;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.ImageServicelmpl;
import com.hcmute.edu.vn.WebTBDT.services.serviceImpl.ProductServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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
    @Autowired
    ImageServicelmpl imageService;
    @Autowired
    CloudinaryService cloudinaryService;





    @GetMapping("/Add_Product")
    private String formAddPro(Model model)
    {
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if(customer==null || customer.getRole()!=1){
            return "redirect:/home";
        }
        model.addAttribute("Product" , new ProductEntity());
        List<CategoryEntity> clist = categoryService.findAll();
        model.addAttribute("categorylist", clist);
        ImageEntity imageEntity = new ImageEntity();


        return "Add_Product";
    }

    @PostMapping("/Add_Product/save_product")
    private String saveProduct(Model model, @ModelAttribute(name ="name") String namePro,
                               @ModelAttribute(name="category") int category,
                               @ModelAttribute(name="quantity") int quantity,
                               @ModelAttribute(name="available") int available,
                               @ModelAttribute(name="description") String description,
                               @ModelAttribute("image") MultipartFile[] image,
                               @ModelAttribute(name="price") int price,
                               RedirectAttributes rd)
    {
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if(customer == null || customer.getRole() != 1) {
            return "redirect:/home";
        }
        ProductEntity itemProduct = new ProductEntity();

        itemProduct.setQuantity(quantity);
        itemProduct.setDescription(description);
        itemProduct.setPrice(price);
        itemProduct.setName(namePro);
        itemProduct.setAvailable(available);
        itemProduct.setCategory(categoryService.findById(category));
        productService.saveProduct(itemProduct);

        List<ImageEntity> imageEntityList = new ArrayList<>();
        for (MultipartFile itemimage : image)
        {
            if(itemimage != null && !itemimage.isEmpty())
            {
                String uimage =  cloudinaryService.uploadFile(itemimage);
                ImageEntity imageEntity = new ImageEntity();
                imageEntity.setUrlImage(uimage);
                imageEntity.setProduct(itemProduct);
                imageService.saveImage(imageEntity);
            }
        }

        itemProduct.setImagelist(imageEntityList);
        rd.addFlashAttribute("mesage", "Đã thêm sản phẩm thành công");
        return "redirect:/Admin_Product";
    }


    @GetMapping("/Admin_Product/page/{pageNumber}")
    private String getOnePage(@PathVariable(value = "pageNumber") int pageNumber , Model model)
    {
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if(customer==null || customer.getRole()!=1){
            return "redirect:/home";
        }
        int pageSize = 10;
        Page<ProductEntity> productEntityPage = productService.findPage(pageNumber,pageSize);
        int totalPage = productEntityPage.getTotalPages();
        Long totalItems = productEntityPage.getTotalElements();
        List<ProductEntity> productEntities = productEntityPage.getContent();
        model.addAttribute("currentPage" , pageNumber);
        model.addAttribute("totalPages" , totalPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("products" ,productEntities);
        return "Admin_Product";
    }

    @GetMapping("/Admin_Product")
    public String viewHomePage(Model model)
    {
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if(customer==null || customer.getRole()!=1){
            return "redirect:/home";
        }

        return getOnePage(1,model);
    }

    @GetMapping("/deleteProduct/{id}")
    public String deletePro(@PathVariable (value = "id") Integer id , RedirectAttributes rd)
    {
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if(customer==null || customer.getRole()!=1){
            return "redirect:/home";
        }
        rd.addFlashAttribute("message" , "Xóa Sản phẩm thành công");
        productService.deleteProductById(id);
        return "redirect:/Admin_Product" ;
    }

    @GetMapping("/Admin_Product/infor_Product/{id}")
    public String showInforPro(Model model , @PathVariable(value = "id") Integer id)
    {
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if(customer==null || customer.getRole()!=1){
            return "redirect:/home";
        }
        ProductEntity product = productService.findById(id);
        CategoryEntity category = product.getCategory();

        List<ImageEntity> images = imageService.findImageByProductId(id);
        model.addAttribute("itemCategory" , category);
        model.addAttribute("image" , images);

        model.addAttribute("itemProduct" , product);

        return "infor_Product" ;
    }



    @GetMapping("/Admin_Product/edit_info_Product/{id}")
    public String editInforPro(Model model, @PathVariable(value = "id") Integer id)
    {

        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if(customer==null || customer.getRole()!=1){
            return "redirect:/home";
        }
        List<CategoryEntity> listCate = categoryService.findAll();

        ProductEntity product = productService.findById(id);
        CategoryEntity category = product.getCategory();
        List<ImageEntity> images = imageService.findImageByProductId(id);
        model.addAttribute("listCate" , listCate);
        model.addAttribute("itemCategory" , category);
        model.addAttribute("image" , images);

        model.addAttribute("itemProduct" , product);

        return "edit_info_Product";
    }

    @PostMapping("/edit_info_Product/save_product/{id}")
    private String UpdatePro(Model model, @ModelAttribute(name ="name") String namePro,
                             @ModelAttribute(name="category") int category,
                             @ModelAttribute(name="quantity") int quantity,
                             @ModelAttribute(name="available") int available,
                             @ModelAttribute(name="description") String description,
                             @ModelAttribute(name="image") MultipartFile[] image,
                             @ModelAttribute(name="price") int price,
                             @PathVariable int id,
                             RedirectAttributes rd)
    {
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if(customer==null || customer.getRole()!=1){
            return "redirect:/home";
        }

        ProductEntity itemProduct=new ProductEntity();
        itemProduct.setId(id);
        itemProduct.setQuantity(quantity);
        itemProduct.setDescription(description);
        itemProduct.setPrice(price);
        itemProduct.setName(namePro);
        itemProduct.setAvailable(available);
        itemProduct.setCategory(categoryService.findById(category));
        productService.saveProduct(itemProduct);

        List<ImageEntity> imageEntityList = new ArrayList<>();

            for (MultipartFile itemImage : image)
            {
                if(!itemImage.isEmpty())
                {
                    String uimage = cloudinaryService.uploadFile(itemImage);
                    ImageEntity imageEntity = new ImageEntity();
                    imageEntity.setUrlImage(uimage);
                    imageEntity.setProduct(itemProduct);
                    imageService.saveImage(imageEntity);
                }
            }


            itemProduct.setImagelist(imageEntityList);

//        productService.updateProduct(itemProduct);

        rd.addFlashAttribute("mesage" , "Đã câp nhật thành công");
        return "redirect:/Admin_Product";
    }

    @GetMapping("/Admin_Product/deletePro/{id}")
    private String deleteProduct(@PathVariable(value = "id") int id, RedirectAttributes rd)
    {
        CustomerEntity customer = (CustomerEntity) session.getAttribute("account");
        if(customer==null || customer.getRole()!=1){
            return "redirect:/home";
        }
        rd.addFlashAttribute("mesage" ,"Đã xóa thành công");
        productService.deleteProductById(id);
        return "redirect:/Admin_Product";


    }











    //User User    User    User    User    User    User    User

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
