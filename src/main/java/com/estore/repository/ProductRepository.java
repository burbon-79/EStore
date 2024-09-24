package com.estore.repository;

import com.estore.dto.ProductDTO;
import com.estore.repository.entity.ProductEntity;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface ProductRepository {
    int addNewProduct(ProductDTO product, int sellerId);
    void setPhotoToProduct(int productId, InputStream photoIS);
    ProductEntity getProductById(int productId);
    List<Map<String, Object>> getAllProducts();
    List<Map<String, Object>> getProductsOfThisCategory(int categoryId);
    List<Map<String, Object>> getProductsOfThisSeller(int sellerId);
    List<Map<String, Object>> searchByName(String stringPattern);
    byte[] getProductPhotoById(int productId);
}
