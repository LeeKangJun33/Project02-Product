package com.example.project02.controller;

import com.example.project02.dto.ProductDTO;
import com.example.project02.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    // 쇼핑몰 판매 물품 등록
    @PostMapping("/sellproduct/register")
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    // 판매 물품 조회
    @GetMapping("/sellproduct/{productname}")
    public ResponseEntity<ProductDTO> getProductByName(@PathVariable String productName) {
        ProductDTO product = productService.getProductByName(productName);
        return ResponseEntity.ok(product);
    }


    // 판매 물품 재고 수정
    @PutMapping("/sellproduct/{productname}/stock/{sellerId}/{productId}/stock")
    public void updateProductStockBySeller(
            @PathVariable Long sellerId,
            @PathVariable Long productId,
            @RequestParam int newStockQuantity) {
        productService.updateProductStockBySeller(sellerId, productId, newStockQuantity);
    }

}
