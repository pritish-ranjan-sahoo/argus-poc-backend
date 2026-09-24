package com.ecommerce.product;

import com.ecommerce.common.dto.ProductRequestDto;
import com.ecommerce.common.dto.ProductResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    final ProductService productService;
    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> findAllProducts(@PageableDefault(page=0,size=10,sort="pricePerUnit") Pageable pageable){
        return ResponseEntity.ok(productService.findAllProducts(pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findProductById(@PathVariable("id") String id){
        return ResponseEntity.ok(productService.findProductById(id));
    }
    @PostMapping
    public ResponseEntity<ProductResponseDto> addProduct(@Valid @RequestBody ProductRequestDto req){
        return ResponseEntity.ok(productService.addProduct(req));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") String id,@Valid @RequestBody ProductRequestDto req){
        return ResponseEntity.ok(productService.updateProduct(id,req));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") String id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<Page<ProductResponseDto>> findProductsBySellerId(@PathVariable("sellerId") String sellerId,@PageableDefault(page=0,size=10,sort="pricePerUnit") Pageable pageable){
        return ResponseEntity.ok(productService.findProductsBySellerId(sellerId,pageable));
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<Page<ProductResponseDto>> findProductsByCategory(@PathVariable("category") String category,@PageableDefault(page=0,size=10,sort="pricePerUnit") Pageable pageable){
        return ResponseEntity.ok(productService.findProductsByCategory(category,pageable));
    }
}
