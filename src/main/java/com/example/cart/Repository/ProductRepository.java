package com.example.cart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


import com.example.cart.Model.ProductModel;
import com.example.cart.Model.User;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
	@Query("SELECT SUM(p.price * p.quantity) FROM ProductModel p")
	Double getTotalPrice();
	List<ProductModel> findByUser(User user);
	Long countByUser(User user);

	@Query("SELECT SUM(p.price * p.quantity) FROM ProductModel p WHERE p.user = :user")
	Double getTotalPriceByUser(@Param("user") User user);

}