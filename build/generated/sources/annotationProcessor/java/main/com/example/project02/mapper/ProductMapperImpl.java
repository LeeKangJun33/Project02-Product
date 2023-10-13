package com.example.project02.mapper;

import com.example.project02.dto.CategoryDTO;
import com.example.project02.dto.ProductDTO;
import com.example.project02.entity.Category;
import com.example.project02.entity.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-13T21:01:59+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.1.jar, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDTO toDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setProductId( product.getProductId() );
        productDTO.setProductName( product.getProductName() );
        productDTO.setPrice( product.getPrice() );
        productDTO.setImg1( product.getImg1() );
        productDTO.setImg2( product.getImg2() );
        productDTO.setImg3( product.getImg3() );
        productDTO.setCategory( categoryToCategoryDTO( product.getCategory() ) );
        productDTO.setSellerId( product.getSellerId() );

        return productDTO;
    }

    @Override
    public Product toEntity(ProductDTO productDTO) {
        if ( productDTO == null ) {
            return null;
        }

        Product product = new Product();

        product.setProductId( productDTO.getProductId() );
        product.setCategory( categoryDTOToCategory( productDTO.getCategory() ) );
        product.setProductName( productDTO.getProductName() );
        product.setPrice( productDTO.getPrice() );
        product.setImg1( productDTO.getImg1() );
        product.setImg2( productDTO.getImg2() );
        product.setImg3( productDTO.getImg3() );
        product.setSellerId( productDTO.getSellerId() );

        return product;
    }

    protected CategoryDTO categoryToCategoryDTO(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setName( category.getName() );

        return categoryDTO;
    }

    protected Category categoryDTOToCategory(CategoryDTO categoryDTO) {
        if ( categoryDTO == null ) {
            return null;
        }

        Category category = new Category();

        category.setName( categoryDTO.getName() );

        return category;
    }
}
