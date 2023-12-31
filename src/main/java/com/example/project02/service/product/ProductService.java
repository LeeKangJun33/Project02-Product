package com.example.project02.service.product;

import com.example.project02.constant.ProductSerllStatus;
import com.example.project02.dto.ProductDTO;
import com.example.project02.dto.SaleRecord;
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
import java.util.ArrayList;
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

    //판매 물품 재고 수정
    public boolean updateProductStockByProductName(String productName, int newStockQuantity) {
        // productName을 사용하여 판매 물품을 찾습니다.
        Product product = productRepository.findByProductNameIgnoreCase(productName); // 대소문자 구분하지 않는 검색

        if (product != null) {
            // 제품을 찾았을 경우, 재고를 수정합니다.
            product.setStock_quantity(newStockQuantity);
            productRepository.save(product);
            return true;
        }

        return false; // 판매 물품을 찾을 수 없는 경우
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

    // 판매 종료된 제품의 상품을 조회하는 메소드
    public List<SaleRecord> getExpiredProductHistory(Long productId) {
        List<SaleRecord> saleRecords = new ArrayList<>();

        // productId를 사용하여 판매 종료된 제품을 조회
        List<Product> expiredProducts = productRepository.findExpiredProductsByProductId(productId);

        for (Product product : expiredProducts) {
            SaleRecord saleRecord = new SaleRecord();
            saleRecord.setProductId(product.getProductId());
            saleRecord.setProductName(product.getProductName());
            saleRecord.setSellerId(product.getSellerId());
            saleRecord.setSaleEndDate(product.getFieldPredictedSaleEnddate());
            saleRecord.setSalePrice(product.getPrice());

            saleRecords.add(saleRecord);
        }

        return saleRecords;
    }
}