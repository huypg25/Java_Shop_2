package com.coeding.controller.user;

import com.coeding.entity.*;
import com.coeding.helper.UserHelper;
import com.coeding.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 */
@Controller
@RequestMapping("/customer/order")
public class CustomerOrderController {

    private CustomerOrderService customerOrderService;
    private CustomerService customerService;
 
    private UserService userService;
    private UserHelper userHelper;
    @Autowired
    public CustomerOrderController(CustomerOrderService customerOrderService, CustomerService customerService,UserService userService,UserHelper userHelper) {
        this.customerOrderService = customerOrderService;
        this.customerService = customerService;
        this.userService = userService;
        this.userHelper=userHelper;
    }

    @GetMapping
    public String customerOrderPage(Authentication authentication, Model model) {
        User user = userHelper.getUser(authentication,userService);
        Customer customer = customerService.findByUserId(user.getId());
        List<CustomerOrder> orders;

        try{
            orders =  customerOrderService.findAllOrderByCustomerId(customer.getId());
        }catch (Exception e){
            orders = new ArrayList<>();
        }

        model.addAttribute("customerOrders",orders );
        return "template/user/customer/invoice/invoice-page";
    }

    @GetMapping("/detail")
    public String customerOrderDetail(Authentication authentication, Model model, @RequestParam("id") Long id) {

        CustomerOrder order = customerOrderService.findById(id);


        model.addAttribute("order", order);
        return "template/user/customer/invoice/invoice-detail";
    }

    @GetMapping("/json/order")
    @ResponseBody
    public List<CustomerOrder> getJsonOrder(Authentication authentication) {
        User user = userHelper.getUser(authentication,userService);
        Customer customer = customerService.findByUserId(user.getId());
        return customerOrderService.findAllOrderByCustomerId(customer.getId());
    }
}
