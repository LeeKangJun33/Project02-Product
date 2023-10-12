package com.example.project02.service.product;

import com.example.project02.dto.ProductDTO;
import com.example.project02.entity.Product;
import com.example.project02.entity.User;
import com.example.project02.exception.ProductNotFoundException;
import com.example.project02.mapper.ProductMapper;
import com.example.project02.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    // 쇼핑몰 판매 물품 등록
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    // 판매 물품 조회
    public ProductDTO getProductByName(String productName) {
        Product product = productRepository.findByProductName(productName)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다. 이름: " + productName));
        return productMapper.toDTO(product);
    }

    // 판매 물품 재고 수정
    public void updateProductStockBySeller(Long sellerId, Long productId, int newStockQuantity) {
        Product product = productRepository.findBySellerIdAndProductId(sellerId, productId)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다. ID: " + productId));
        product.setStock_quantity(newStockQuantity);
        productRepository.save(product);
    }
}
