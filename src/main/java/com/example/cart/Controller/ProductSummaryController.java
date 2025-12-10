package com.example.cart.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.cart.Model.User;
import com.example.cart.Repository.ProductRepository;
import com.example.cart.Repository.UserRepository;
import com.example.cart.Security.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test/products")
public class ProductSummaryController {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserRepository userRepository;
	
	@Autowired 
	private JwtUtil jwtUtil;

    @GetMapping("/summary")
    public Map<String, Object> getUserSummary(
            @RequestHeader("Authorization") String token) {

        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userRepository.findByUsername(username);

        Long count = productRepository.countByUser(user);
        Double total = productRepository.getTotalPriceByUser(user);

        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("total", total);

        return result;
    }

}
