package com.estore.repository;

import com.estore.dto.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository{
    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    public void addReview(ReviewDTO review, String reviewType){
        String sql = "INSERT INTO review VALUES(DEFAULT, :username, :stars, :text, :type, :referenceId)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("username", review.getUsername())
                .addValue("stars", review.getStars())
                .addValue("text", review.getText())
                .addValue("type", reviewType)
                .addValue("referenceId", review.getReferenceId());
        jdbcOperations.update(sql, namedParameters);
    }

    public List<Map<String, Object>> getReviewsByReferenceId(int referenceId, String type){
        String sql = "SELECT * FROM review WHERE type = :type AND referenceId = :referenceId;";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("type", type)
                .addValue("referenceId", referenceId);
        return jdbcOperations.queryForList(sql, namedParameters);
    }
}
