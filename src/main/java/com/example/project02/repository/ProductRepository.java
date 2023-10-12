package com.example.project02.repository;

import com.example.project02.entity.Product;
import com.example.project02.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.productName = :productName")
    List<Product> findProductByProduct_name(@Param("productName") String productName);

    Optional<Product> findBySellerIdAndProductId(Long sellerId, Long productId);

    Optional<Product> findByProductName(String productName);






}
