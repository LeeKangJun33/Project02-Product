package com.example.project02.controller;

import com.example.project02.dto.ProductDTO;
import com.example.project02.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    //이미지 등록
    @PostMapping("/product/image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // 이미지를 저장하거나 다른 처리를 수행하는 서비스 메서드를 호출
            // 이 예제에서는 저장만 하고 에러 처리를 하지 않았습니다
            saveImage(file);
            return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to upload image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void saveImage(MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            // 이미지를 저장하거나 다른 처리를 수행
            // 여기에 파일 저장 로직을 구현하십시오
        } else {
            throw new Exception("Uploaded file is empty");
        }
    }
}

