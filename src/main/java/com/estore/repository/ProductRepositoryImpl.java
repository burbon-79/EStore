package com.estore.repository;

import com.estore.repository.entity.ProductEntity;
import com.estore.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepositoryImpl implements ProductRepository{
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public int addNewProduct(ProductDTO product, int sellerId){
        String sql = "INSERT INTO products VALUES(DEFAULT, :name, DEFAULT, :price, :sellerId, :description, :categoryId)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("name", product.getName())
                .addValue("price", product.getPrice())
                .addValue("sellerId", sellerId)
                .addValue("description", product.getDescription())
                .addValue("categoryId", product.getCategoryId());
        jdbcOperations.update(sql, namedParameters);

        return jdbcOperations.queryForObject("SELECT LAST_INSERT_ID();", new HashMap<>(), Integer.class);
    }

    public void setPhotoToProduct(int productId, String linkToFile){
        String sql = "UPDATE products SET photo = :photo WHERE id = :id;";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("photo", linkToFile)
                .addValue("id", productId);
        jdbcOperations.update(sql, namedParameters);
    }

    public ProductEntity getProductById(int productId){
        String sql = "SELECT * FROM products WHERE id = :id;";
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", productId);
        return jdbcOperations.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(ProductEntity.class));
    }

    public List<Map<String, Object>> getAllProducts(){
        String sql = "SELECT * FROM products";
        return jdbcOperations.queryForList(sql, new HashMap<>());
    }

    public List<Map<String, Object>> getProductsOfThisCategory(int categoryId){
        String sql = "SELECT * FROM products WHERE category_id = :categoryId;";
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("categoryId", categoryId);
        return jdbcOperations.queryForList(sql, namedParameters);
    }

    public List<Map<String, Object>> getProductsOfThisSeller(int sellerId){
        String sql = "SELECT * FROM products WHERE seller_id = :sellerId;";
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("sellerId", sellerId);
        return jdbcOperations.queryForList(sql, namedParameters);
    }

    public List<Map<String, Object>> searchByName(String stringPattern){
        String sql = "SELECT * FROM products WHERE name LIKE :pattern";
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("pattern", '%' + stringPattern + '%');
        return jdbcOperations.queryForList(sql, namedParameters);
    }
}
