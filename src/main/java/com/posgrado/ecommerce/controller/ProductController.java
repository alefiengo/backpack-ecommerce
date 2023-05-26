package com.posgrado.ecommerce.controller;

import com.posgrado.ecommerce.dto.PageDto;
import com.posgrado.ecommerce.dto.ProductDto;
import com.posgrado.ecommerce.entity.Product;
import com.posgrado.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Product")
@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

  private ProductService productService;

  @Operation(
      summary = "Save a new product"
  )
  @SecurityRequirement(
      name = "bearerAuth"
  )
  @PostMapping
  public ResponseEntity<Product> create(@Valid @RequestBody ProductDto dto) {
    Product productSaved = productService.create(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(productSaved);
  }

  @Operation(
      summary = "Update a product"
  )
  @SecurityRequirement(
      name = "bearerAuth"
  )
  @PutMapping("/{id}")
  public ResponseEntity<Product> update(@PathVariable UUID id, @Valid @RequestBody ProductDto dto) {
    Product productUpdated = productService.update(id, dto);
    return ResponseEntity.status(HttpStatus.OK).body(productUpdated);
  }

  @Operation(
      summary = "Get product by ID"
  )
  @GetMapping("/{id}")
  public ResponseEntity<Product> getById(@PathVariable UUID id) {
    Product productFound = productService.getById(id);
    return ResponseEntity.status(HttpStatus.OK).body(productFound);
  }

  @Hidden
  @GetMapping("/pageable")
  public ResponseEntity<Page<Product>> getProducts(@RequestParam int page, @RequestParam int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Product> productPage = productService.getProducts(pageable);
    return ResponseEntity.status(HttpStatus.OK).body(productPage);
  }

  @Operation(
      summary = "Get filtered products with pagination"
  )
  @GetMapping
  public ResponseEntity<PageDto<Product>> getFilteredProducts(
      @RequestParam Double minPrice,
      @RequestParam Double maxPrice,
      @RequestParam int page,
      @RequestParam int size,
      @RequestParam String sortField,
      @RequestParam String sortOrder
  ) {
    Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
    Pageable pageable = PageRequest.of(page, size, sort);
    PageDto<Product> productPage = productService.getFilteredProducts(minPrice, maxPrice, pageable);
    return ResponseEntity.status(HttpStatus.OK).body(productPage);
  }

  @Operation(
      summary = "Get filtered products by Category ID with pagination"
  )
  @GetMapping("/category/{categoryId}")
  public ResponseEntity<PageDto<Product>> getFilteredProductsByCategoryId(
      @PathVariable UUID categoryId,
      @RequestParam int page,
      @RequestParam int size,
      @RequestParam String sortField,
      @RequestParam String sortOrder
  ) {
    Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
    Pageable pageable = PageRequest.of(page, size, sort);
    PageDto<Product> productPage = productService.getFilteredProductsByCategoryId(categoryId,
        pageable);
    return ResponseEntity.status(HttpStatus.OK).body(productPage);
  }
}
