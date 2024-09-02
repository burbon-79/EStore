package com.estore.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository{
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public String getCategoryNameById(int categoryId){
        String sql = "SELECT name FROM category WHERE id = :id;";
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", categoryId);
        return jdbcOperations.queryForObject(sql, namedParameters, String.class);
    }
}
