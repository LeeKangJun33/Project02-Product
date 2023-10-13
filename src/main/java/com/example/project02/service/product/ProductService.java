package com.example.project02.service.product;

import com.example.project02.dto.ProductDTO;
import com.example.project02.entity.Product;
import com.example.project02.exception.ImageUploadException;
import com.example.project02.exception.ProductNotFoundException;
import com.example.project02.mapper.ProductMapper;
import com.example.project02.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


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

    // 이미지를 저장할 디렉토리 경로
    private static final String UPLOAD_DIRECTORY = "/Users/lee/Desktop/Supercoding-project2_back/src/test/java/com/example/project02/resources/";

    public void saveImage(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                // 업로드된 파일의 원본 이름을 가져옴
                String originalFilename = file.getOriginalFilename();

                // 저장할 파일 경로
                String filePath = UPLOAD_DIRECTORY + originalFilename;

                // 파일을 지정된 경로에 저장
                File dest = new File(filePath);
                file.transferTo(dest);

                // 저장된 파일을 가공하거나 다른 작업을 수행할 수 있음
            } catch (IOException e) {
                // 이미지 업로드 중에 예외가 발생하면 ImageUploadException으로 래핑하여 던짐
                throw new ImageUploadException("Failed to upload image", e);
            }
        } else {
            throw new ImageUploadException("Uploaded file is empty");
        }
    }
}