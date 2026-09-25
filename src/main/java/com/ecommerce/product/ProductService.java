package com.ecommerce.product;

import com.ecommerce.common.dto.ProductRequestDto;
import com.ecommerce.common.dto.ProductResponseDto;
import com.ecommerce.common.error.ResourceNotFoundException;
import com.ecommerce.user.AppUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    //    connect real repo
    private final AppUserRepos appUserRepos;

    public Page<ProductResponseDto> findAllProducts(Pageable pageable){
        Page<Product> products= productRepository.findAll(pageable);
         return  products.map(this::toResponse);
    }

    public ProductResponseDto findProductById(String id){
        UUID productUuid = UUID.fromString(id);
        Product product=productRepository.findByProductId(productUuid).orElseThrow(()->new ResourceNotFoundException("Product not found"));
        return toResponse(product);
    }

    public ProductResponseDto addProduct(ProductRequestDto req){
        Product product=toProduct(req);
        return toResponse(productRepository.save(product));
    }

    public ProductResponseDto updateProduct(String id, ProductRequestDto req) {
        UUID productUuid = UUID.fromString(id);
        Product existingProduct = productRepository.findById(productUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        existingProduct.setName(req.getName());
        existingProduct.setDescription(req.getDescription());
        existingProduct.setPricePerUnit(req.getPricePerUnit());
        existingProduct.setStock(req.getStock());
        existingProduct.setCategoryType(req.getCategoryType());
        existingProduct.setProductImageUrl(req.getProductImageUrl());
        return toResponse(productRepository.save(existingProduct));
    }

    public void deleteProduct(String id){
        UUID productUuid = UUID.fromString(id);
        Product existingProduct = productRepository.findById(productUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        productRepository.delete(existingProduct);
    }

    public Page<ProductResponseDto> findProductsBySellerId(String id,Pageable pageable){
        UUID uuid = UUID.fromString(id);
        Page<Product> products= productRepository.findBySellerId(uuid,pageable);
        return products.map(this::toResponse);
    }

    public Page<ProductResponseDto> findProductsByCategory(String category,Pageable pageable) {
        try {
            CategoryType categoryEnum = CategoryType.valueOf(category.toUpperCase().trim());
            Page<Product> products = productRepository.findByCategoryType(categoryEnum,pageable);
            return products
                    .map(this::toResponse);
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException("Not known category " + category);
        }
    }

    public ProductResponseDto updateProductStock(String id, Long stock) {
        UUID productUuid = UUID.fromString(id);
        Product existingProduct = productRepository.findById(productUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        existingProduct.setStock(stock);
        return toResponse(productRepository.save(existingProduct));
    }

    public ProductResponseDto updateProductPrice(String id, BigDecimal price) {
        UUID productUuid = UUID.fromString(id);
        Product existingProduct = productRepository.findById(productUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        existingProduct.setPricePerUnit(price);
        return toResponse(productRepository.save(existingProduct));
    }
    //helper methods
    private ProductResponseDto toResponse(Product product){
        return modelMapper.map(product, ProductResponseDto.class);
    }
    private Product toProduct(ProductRequestDto req) {
        AppUser seller = appUserRepos.findById(req.getSellerId())
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with ID: " + req.getSellerId()));
        return Product.builder()
                .name(req.getName())
                .description(req.getDescription())
                .pricePerUnit(req.getPricePerUnit())
                .stock(req.getStock())
                .categoryType(req.getCategoryType())
                .seller(seller)
                .productImageUrl(req.getProductImageUrl())
                .build();
    }
}
