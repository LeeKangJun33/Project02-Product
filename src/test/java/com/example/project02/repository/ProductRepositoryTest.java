package com.example.project02.repository;

import com.example.project02.constant.ProductSerllStatus;
import com.example.project02.entity.Category;
import com.example.project02.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public void createProductList() {
        // 카테고리를 먼저 생성하고 저장
        Category category = new Category();
        category.setName("패션");
        Category savedCategory = categoryRepository.save(category);

        for (int i = 1; i <= 10; i++) {
            Product product = new Product();
            product.setProduct_name("테스트상품" + i);
            product.setPrice(10000 + i);
            product.setStock_quantity(100);
            product.setProductSerllStatus(ProductSerllStatus.SELL);
            product.setRegister_date(LocalDateTime.now());
            product.setField_predicted_sale_enddate(new Date());

            // 위에서 생성한 카테고리를 설정
            product.setCategory(savedCategory);

            Product savedProduct = productRepository.save(product);
        }
    }

    @Test
    @Rollback(false)
    @DisplayName("상품,카테고리 저장테스트")
    public void testSaveProduct() {
        // Given: 카테고리 생성
        Category category = new Category();
        category.setName("패션");
        Category savedCategory = categoryRepository.save(category);

        // When: 상품 생성 및 저장
        Product product = new Product();
        product.setCategory(savedCategory);
        product.setProduct_name("후드티");
        product.setPrice(1000);
        product.setStock_quantity(10);
        product.setRegister_date(LocalDateTime.now());
        product.setField_predicted_sale_enddate(new Date());
        product.setProductSerllStatus(ProductSerllStatus.IN_STOCK);
        product.setImg1("clothes_image1.jpg");
        product.setImg2("clothes_image2.jpg");
        product.setImg3("clothes_image3.jpg");

        Product savedProduct = productRepository.save(product);

        // Then: 저장된 상품 검증
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getProduct_id()).isNotNull();

        // Then: 저장된 상품을 다시 조회하여 검증
        Product retrievedProduct = productRepository.findById(savedProduct.getProduct_id()).orElse(null);
        assertThat(retrievedProduct).isNotNull();
        assertThat(retrievedProduct.getProduct_name()).isEqualTo("후드티");
    }

    @Test
    @DisplayName("상품명으로 상품 조회 테스트")
    public void findByProductTest() {
        // 상품 목록 생성
        createProductList();

        // 특정 상품명으로 조회
        String productNameToFind = "테스트상품3"; // 원하는 상품명을 설정

        List<Product> productList = productRepository.findProductByProduct_name(productNameToFind);

        // 조회된 상품 리스트 출력
        for (Product product : productList) {
            System.out.println(product.toString());
            assertThat(product.getProduct_name()).isEqualTo(productNameToFind);
        }
    }
        @Test
        @Transactional
        @Rollback(false) // 테스트에서 롤백을 하지 않도록 설정
        @DisplayName("상품 등록 테스트")
        public void registerProductTest() {
            // Given: 카테고리 생성 및 저장
            Category category = new Category();
            category.setName("패션");
            Category savedCategory = categoryRepository.save(category);

            // When: 새로운 상품 등록
            Product product = new Product();
            product.setCategory(savedCategory);
            product.setProduct_name("신발"); // 상품명 설정
            product.setPrice(50000); // 가격 설정
            product.setStock_quantity(50); // 재고 설정
            product.setRegister_date(LocalDateTime.now());
            product.setField_predicted_sale_enddate(new Date());
            product.setProductSerllStatus(ProductSerllStatus.SELL);
            product.setImg1("shoes_image1.jpg");
            product.setImg2("shoes_image2.jpg");
            product.setImg3("shoes_image3.jpg");

            Product savedProduct = productRepository.save(product);

            // Then: 상품 등록 확인
            assertThat(savedProduct).isNotNull();
            assertThat(savedProduct.getProduct_id()).isNotNull();

            // Then: 등록한 상품 조회하여 검증
            Product retrievedProduct = productRepository.findById(savedProduct.getProduct_id()).orElse(null);
            assertThat(retrievedProduct).isNotNull();
            assertThat(retrievedProduct.getProduct_name()).isEqualTo("신발"); // 상품명 확인
        }
    }

