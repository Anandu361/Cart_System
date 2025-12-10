package com.example.cart.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_list")
public class ProductModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private Double price;
	private Integer quantity;
	private LocalDateTime createdAt;
	
	public ProductModel() {
		this.createdAt = LocalDateTime.now();
	}
	
	public ProductModel(String name, Double price, Integer quantity) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.createdAt = LocalDateTime.now();
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public void setUser(User user) {
	    this.user = user;
	}


}