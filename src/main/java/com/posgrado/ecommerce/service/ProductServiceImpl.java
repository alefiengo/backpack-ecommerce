package com.posgrado.ecommerce.service;

import com.posgrado.ecommerce.dto.PageDto;
import com.posgrado.ecommerce.dto.ProductDto;
import com.posgrado.ecommerce.entity.Category;
import com.posgrado.ecommerce.entity.Product;
import com.posgrado.ecommerce.exception.EntityNotFoundException;
import com.posgrado.ecommerce.mapper.ProductMapper;
import com.posgrado.ecommerce.repository.ProductRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

  private ProductRepository productRepository;
  private CategoryService categoryService;
  private ProductMapper productMapper;

  @Override
  public Product create(ProductDto dto) {
    Category category = categoryService.getByID(dto.getCategoryId());
    Product product = productMapper.fromDto(dto);
    product.setCategory(category);
    return productRepository.save(product);
  }

  @Override
  public Product update(UUID id, ProductDto dto) {
    Product product = getById(id);
    Category category = categoryService.getByID(dto.getCategoryId());
    product.setName(dto.getName());
    product.setDescription(dto.getDescription());
    product.setPrice(dto.getPrice());
    product.setStock(dto.getStock());
    product.setImageUrl(dto.getImageUrl());
    product.setActive(dto.isActive());
    product.setCategory(category);
    return productRepository.save(product);
  }

  @Override
  public Product getById(UUID id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Product", id));
  }

  @Override
  public Page<Product> getProducts(Pageable pageable) {
    return productRepository.findAll(pageable);
  }

  @Override
  public PageDto<Product> getFilteredProducts(Double minPrice, Double maxPrice, Pageable pageable) {
    Page<Product> page = productRepository.findByPriceBetween(minPrice, maxPrice, pageable);
    return productMapper.fromEntity(page);
  }

  @Override
  public PageDto<Product> getFilteredProductsByCategoryId(UUID categoryId, Pageable pageable) {
    Page<Product> page = productRepository.findByCategoryId(categoryId, pageable);
    return productMapper.fromEntity(page);
  }
}
