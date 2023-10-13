package com.example.project02.controller;

import com.example.project02.dto.ProductDTO;
import com.example.project02.entity.Product;
import com.example.project02.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    // 쇼핑몰 판매 물품 등록
    @PostMapping("/sellproduct/register")
    public String createProduct(@ModelAttribute ProductDTO productDTO,Model model) {
       productService.createProduct(productDTO);
        return "sellProductRegister";  // sellProductRegister.html 뷰 반환
    }

    @PostMapping("/product/register")
    public ModelAndView registerProduct(
            @RequestParam String productName,
            @RequestParam String productDescription,
            @RequestParam Double price,
            @RequestParam Integer stockQuantity,
            @RequestParam Date registrationDate) {

        Product product = new Product();
        product.setProductName(productName);
        product.setProductDescription(productDescription);
        product.setPrice(price);
        product.setStock_quantity(stockQuantity);

        LocalDateTime currentDateTime = LocalDateTime.now();
        product.setRegister_date(currentDateTime);

        product.setField_predicted_sale_enddate(registrationDate);


        productService.registerProduct(product);

        ModelAndView modelAndView = new ModelAndView("successPage");  // successPage.html 뷰를 반환
        modelAndView.addObject("message", "물품이 성공적으로 등록되었습니다.");
        return modelAndView;
    }


    // 판매 물품 조회
    @GetMapping("/sellproduct/{productname}")
    public ResponseEntity<ProductDTO> getProductByName(@PathVariable String productName) {
        ProductDTO product = productService.getProductByName(productName);
        return ResponseEntity.ok(product);
    }



    // 판매 물품 재고 수정
    @PutMapping("/sellproduct/adjuststock{sellerId}/{productId}/stock")
    public ResponseEntity<String> updateProductStockBySeller(
            @PathVariable Long sellerId,
            @PathVariable Long productId,
            @RequestParam int newStockQuantity) {
        boolean success = productService.updateProductStockBySeller(sellerId, productId, newStockQuantity);

        if (success) {
            return ResponseEntity.ok("재고 수정이 성공적으로 이루어졌습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("재고 수정에 실패했습니다.");
        }
    }

    //이미지 등록
    @PostMapping("/product/image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {

            saveImage(file);
            return new ResponseEntity<>("이미지업로드에 성공했습니다:", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("이미지업로드에 실패했습니다: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void saveImage(MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
        } else {
            throw new Exception("빈 이미지 파일입니다");
        }
    }
}

