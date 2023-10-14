package com.example.project02.service.product;

import com.example.project02.constant.ProductSerllStatus;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;


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

    public void registerProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("물품 정보가 없습니다.");
        }

        productRepository.save(product);
    }

    // 판매 물품 조회
    public ProductDTO getProductByName(String productName) {
        Product product = productRepository.findByProductName(productName)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다. 이름: " + productName));
        return productMapper.toDTO(product);
    }

    // 판매 물품 재고 수정
    public boolean updateProductStockBySeller(Long sellerId, Long productId, int newStockQuantity) {
        Optional<Product> productOptional = productRepository.findBySellerIdAndProductId(sellerId, productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setStock_quantity(newStockQuantity);
            productRepository.save(product);
            return true; // 업데이트 성공
        }

        return false; // 업데이트 실패: 상품을 찾을 수 없음
    }

    // 이미지를 저장할 디렉토리 경로
    private static final String UPLOAD_DIRECTORY = "/Users/lee/Desktop/Supercoding-project2_back/src/test/java/com/example/project02/resources/";

    public void saveImage(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String originalFilename = file.getOriginalFilename();

                String filePath = UPLOAD_DIRECTORY + originalFilename;

                File dest = new File(filePath);
                file.transferTo(dest);

            } catch (IOException e) {

                throw new ImageUploadException("이미지업로드에 실패했습니다", e);
            }
        } else {
            throw new ImageUploadException("오류입니다");
        }
    }

   //판매종료 날짜가 지난 판매 물품 내역 조회
    public List<Product> getExpiredProducts() {
        Date currentDate = new Date();
        return productRepository.findByFieldPredictedSaleEnddateBeforeAndProductSerllStatus(currentDate, ProductSerllStatus.ACTIVE);
    }
}