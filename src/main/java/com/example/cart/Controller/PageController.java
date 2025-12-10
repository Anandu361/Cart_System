package com.example.cart.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; 
    }

    @GetMapping("/home")
    public String homePage() {
        return "home"; 
    }
    
    @GetMapping("/register")
    public String registerPage() {
    	return "register";
    }
    
    @GetMapping("/products")
    public String productsPage() {
    	return "products";
    }
    
    @GetMapping("/add-product")
    public String addProductPage() {
    	return "add-product";
    }
    
    @GetMapping("/edit-product")
    public String editProductPage() {
        return "edit-product";
    }
}
