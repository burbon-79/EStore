package com.estore.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository{
    @Autowired
    private JdbcOperations jdbcOperations;

    public String getCategoryNameById(int categoryId){
        String sql = "SELECT name FROM category WHERE id = ?;";
        return jdbcOperations.queryForObject(sql, String.class, categoryId);
    }
}
