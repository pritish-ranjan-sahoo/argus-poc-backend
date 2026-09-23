package com.ecommerce.product;

import com.ecommerce.common.dto.ProductRequestDto;
import com.ecommerce.common.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    final ProductRepository productRepository;
    final ModelMapper modelMapper;

    public List<ProductResponseDto> findAllProducts(){
        List<Product> products= productRepository.findAll();
        for(Product product:products){
            System.out.println(product.getProductId());
        }
         return  products.stream().map(this::toResponse).toList();
    }

    //helper methods
    private ProductResponseDto toResponse(Product product){
        return modelMapper.map(product, ProductResponseDto.class);
    }
    private Product toProduct(ProductRequestDto productRequestDto){
        return modelMapper.map(productRequestDto,Product.class);
    }
}
