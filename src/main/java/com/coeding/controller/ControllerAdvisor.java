package com.coeding.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import com.coeding.entity.Brand;
import com.coeding.entity.Category;
import com.coeding.entity.Product;
import com.coeding.entity.User;
import com.coeding.helper.UserHelper;
import com.coeding.service.BrandService;
import com.coeding.service.CategoryService;
import com.coeding.service.ProductService;
import com.coeding.service.UserService;


@ControllerAdvice
public class ControllerAdvisor  extends DefaultHandlerExceptionResolver  {


    private CategoryService categoryService;
    private BrandService brandService;
    private ProductService productService;
    private UserService userService;
    private UserHelper userHelper;

    @Autowired
    public ControllerAdvisor(CategoryService categoryService, BrandService brandService, ProductService productService, UserService userService,UserHelper userHelper) {
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.productService = productService;
        this.userService = userService;
        this.userHelper = userHelper;
    }


    @ModelAttribute("user")
    public User user(Authentication authentication){
        return userHelper.getUser(authentication,userService);
    }

    @ModelAttribute("categories")
    public List<Category> categories(){
        return  categoryService.findAll();
    }


    @ModelAttribute("brands")
    public List<Brand> brands(){
        return  brandService.findAll();
    }



    @ModelAttribute("brandsByCategory")
    public Map<String, Set<Brand>> filterBrandByCategory(){
        Map<String, Set<Brand>> map = new HashMap<>();
        categoryService.findAll().forEach(c->{
            Set<Brand> brands = new HashSet<>();
            productService.findByCategoryId(c.getId()).forEach(p->brands.add(p.getBrand()));
            map.put(c.getName(),brands);
        });
        return map;
    }




    @ModelAttribute("allProducts")
    public List<Product> allProducts(){
        return productService.findAllIgnoreStatus();
    }


    @ModelAttribute("topProducts")
    public List<Product> topProducts(){
        List<Product> list = new ArrayList<>();
        productService.findTop5().forEach(pid->list.add(productService.findById(pid)));
        return list;
    }



//commit

}
