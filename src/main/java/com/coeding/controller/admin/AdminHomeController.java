package com.coeding.controller.admin;

import com.coeding.entity.CustomerOrder;
import com.coeding.entity.Product;
import com.coeding.entity.UserDetail;
import com.coeding.service.OrderService;
import com.coeding.service.ProductService;
import com.coeding.service.UserService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 */
@Controller
@RequestMapping("/admin")
public class AdminHomeController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	@GetMapping
	public String customerHomePage(Authentication authentication, Model model) {
//		UserDetail userDetails = (UserDetail) authentication.getPrincipal();
//		model.addAttribute("user", userDetails.getUser());
		int size = orderService.numberNewOrder();
		model.addAttribute("numberNewOrder", size);
		

	

	
		
		return "template/admin/index";
	}
}
