package com.estore.repository;

import com.estore.repository.entity.ProductEntity;
import com.estore.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ProductRepositoryImpl implements ProductRepository{
    @Autowired
    private JdbcOperations jdbcOperations;

    public int addNewProduct(ProductDTO product, int sellerId){
        String sql = "INSERT INTO products VALUES(DEFAULT, ?, DEFAULT, ?, ?, ?, ?)";
        jdbcOperations.update(sql, product.getName(), product.getPrice(), sellerId, product.getDescription(), product.getCategoryId());

        return jdbcOperations.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);
    }

    public void setPhotoToProduct(int productId, String linkToFile){
        String sql = "UPDATE products SET photo = ? WHERE id = ?;";
        jdbcOperations.update(sql, linkToFile, productId);
    }

    public ProductEntity getProductById(int productId){
        String sql = "SELECT * FROM products WHERE id = ?;";
        return jdbcOperations.queryForObject(sql, new BeanPropertyRowMapper<>(ProductEntity.class), productId);
    }

    public List<Map<String, Object>> getAllProducts(){
        String sql = "SELECT * FROM products";
        return jdbcOperations.queryForList(sql);
    }

    public List<Map<String, Object>> getProductsOfThisCategory(int categoryId){
        String sql = "SELECT * FROM products WHERE category_id = ?;";
        return jdbcOperations.queryForList(sql, categoryId);
    }

    public List<Map<String, Object>> getProductsOfThisSeller(int sellerId){
        String sql = "SELECT * FROM products WHERE seller_id = ?;";
        return jdbcOperations.queryForList(sql, sellerId);
    }

    public List<Map<String, Object>> searchByName(String stringPattern){
        String sql = "SELECT * FROM products WHERE name LIKE ?";
        return jdbcOperations.queryForList(sql, '%' + stringPattern + '%');
    }
}
