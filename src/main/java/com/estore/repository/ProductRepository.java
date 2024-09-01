package com.estore.repository;

import com.estore.dto.ProductDTO;
import com.estore.repository.entity.ProductEntity;

import java.util.List;
import java.util.Map;

public interface ProductRepository {
    int addNewProduct(ProductDTO product, int sellerId);
    void setPhotoToProduct(int productId, String linkToFile);
    ProductEntity getProductById(int productId);
    List<Map<String, Object>> getAllProducts();
    List<Map<String, Object>> getProductsOfThisCategory(int categoryId);
    List<Map<String, Object>> getProductsOfThisSeller(int sellerId);
    List<Map<String, Object>> searchByName(String stringPattern);
}
