package com.example.cart.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.cart.Model.ProductModel;
import com.example.cart.Model.User;
import com.example.cart.Repository.ProductRepository;
import com.example.cart.Repository.UserRepository;
import com.example.cart.Security.JwtUtil;

@RestController
@RequestMapping("/api/test/products")
public class ProductController {
	@Autowired
    private ProductRepository productRepository;
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired 
	private JwtUtil jwtUtil;

	@PostMapping("/add")
	public ProductModel addProduct(@RequestBody ProductModel product,
	                               @RequestHeader("Authorization") String token) {

	    String username = jwtUtil.extractUsername(token.substring(7));
	    User user = userRepository.findByUsername(username);

	    product.setUser(user);
	    return productRepository.save(product);
	}


	@GetMapping("/all")
	public List<ProductModel> getUserProducts(@RequestHeader("Authorization") String token) {

	    String username = jwtUtil.extractUsername(token.substring(7));
	    User user = userRepository.findByUsername(username);

	    return productRepository.findByUser(user);
	}

    
    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
    
    @GetMapping("/{id}")
    public ProductModel getProductById(@PathVariable Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @PutMapping("/update/{id}")
    public ProductModel updateProduct(@PathVariable Long id,
                                       @RequestBody ProductModel newProduct) {

        ProductModel p = productRepository.findById(id).orElse(null);

        if (p != null) {
            p.setName(newProduct.getName());
            p.setPrice(newProduct.getPrice());
            p.setQuantity(newProduct.getQuantity());
            return productRepository.save(p);
        }

        return null;
    }
    
    

}