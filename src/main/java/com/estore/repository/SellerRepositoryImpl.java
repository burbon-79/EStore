package com.estore.repository;

import com.estore.dto.SellerDTO;
import com.estore.repository.entity.SellerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.io.InputStream;

@Repository
public class SellerRepositoryImpl implements SellerRepository{
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public void registerSeller(SellerDTO seller){
        String sql = "INSERT INTO seller VALUES(DEFAULT, :email, :password, DEFAULT, DEFAULT, DEFAULT)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(seller);
        jdbcOperations.update(sql, namedParameters);
    }

    public int getIdByEmail(String email){
        String sql = "SELECT id FROM seller WHERE email = :email;";
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("email", email);
        return jdbcOperations.queryForObject(sql, namedParameters, Integer.class);
    }

    public String getEmailById(int id){
        String sql = "SELECT email FROM seller WHERE id = :id;";
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
        return jdbcOperations.queryForObject(sql, namedParameters, String.class);
    }

    public String getStoreNameByEmail(String email){
        String sql = "SELECT store_name FROM seller WHERE email = :email;";
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("email", email);
        return jdbcOperations.queryForObject(sql, namedParameters, String.class);
    }

    public String getStoreNameById(int id){
        String sql = "SELECT store_name FROM seller WHERE id = :id;";
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
        return jdbcOperations.queryForObject(sql, namedParameters, String.class);
    }

    public byte[] getStorePhotoById(int id){
        String sql = "SELECT photo FROM seller WHERE id = :id;";
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
        return jdbcOperations.queryForObject(sql, namedParameters, byte[].class);
    }

    public SellerEntity getStoreById(int id){
        String sql = "SELECT id, email, store_name, photo, about FROM seller WHERE id = :id;";
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
        return jdbcOperations.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(SellerEntity.class));
    }

    public void setPhotoToStore(int storeId, InputStream photoIS){
        String sql = "UPDATE seller SET photo = :photo WHERE id = :id;";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("photo", photoIS)
                .addValue("id", storeId);
        jdbcOperations.update(sql, namedParameters);
    }

    public void setStoreName(int storeId, String storeName){
        String sql = "UPDATE seller SET store_name = :storeName WHERE id = :id;";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("storeName", storeName)
                .addValue("id", storeId);
        jdbcOperations.update(sql, namedParameters);
    }

    public void setStoreAbout(int storeId, String text){
        String sql = "UPDATE seller SET about = :about WHERE id = :id;";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("about", text)
                .addValue("id", storeId);
        jdbcOperations.update(sql, namedParameters);
    }
}
