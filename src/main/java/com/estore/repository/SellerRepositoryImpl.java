package com.estore.repository;

import com.estore.dto.SellerDTO;
import com.estore.repository.entity.SellerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository
public class SellerRepositoryImpl implements SellerRepository{
    @Autowired
    private JdbcOperations jdbcOperations;

    public void registerSeller(SellerDTO seller){
        String sql = "INSERT INTO seller VALUES(DEFAULT, ?, ?, DEFAULT, DEFAULT, DEFAULT)";
        jdbcOperations.update(sql, seller.getEmail(), seller.getPassword());
    }

    public int getIdByEmail(String email){
        String sql = "SELECT id FROM seller WHERE email = ?;";
        return jdbcOperations.queryForObject(sql, Integer.class, email);
    }

    public String getEmailById(int id){
        String sql = "SELECT email FROM seller WHERE id = ?;";
        return jdbcOperations.queryForObject(sql, String.class, id);
    }

    public String getStoreNameByEmail(String email){
        String sql = "SELECT store_name FROM seller WHERE email = ?;";
        return jdbcOperations.queryForObject(sql, String.class, email);
    }

    public String getStoreNameById(int id){
        String sql = "SELECT store_name FROM seller WHERE id = ?;";
        return jdbcOperations.queryForObject(sql, String.class, id);
    }

    public SellerEntity getStoreById(int id){
        String sql = "SELECT id, email, store_name, photo, about FROM seller WHERE id = ?;";
        return jdbcOperations.queryForObject(sql, new BeanPropertyRowMapper<>(SellerEntity.class), id);
    }

    public void setPhotoToStore(int storeId, String linkToFile){
        String sql = "UPDATE seller SET photo = ? WHERE id = ?;";
        jdbcOperations.update(sql, linkToFile, storeId);
    }

    public void setStoreName(int storeId, String storeName){
        String sql = "UPDATE seller SET store_name = ? WHERE id = ?;";
        jdbcOperations.update(sql, storeName, storeId);
    }

    public void setStoreAbout(int storeId, String text){
        String sql = "UPDATE seller SET about = ? WHERE id = ?;";
        jdbcOperations.update(sql, text, storeId);
    }
}
