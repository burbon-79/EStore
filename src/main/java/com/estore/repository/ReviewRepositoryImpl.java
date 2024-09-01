package com.estore.repository;

import com.estore.dto.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository{
    @Autowired
    private JdbcOperations jdbcOperations;

    public void addReview(ReviewDTO review, String reviewType){
        String sql = "INSERT INTO review VALUES(DEFAULT, ?, ?, ?, ?, ?)";
        jdbcOperations.update(sql, review.getUsername(), review.getStars(), review.getText(), reviewType, review.getReferenceId());
    }

    public List<Map<String, Object>> getReviewsByReferenceId(int referenceId, String type){
        String sql = "SELECT * FROM review WHERE type = ? AND referenceId = ?;";
        return jdbcOperations.queryForList(sql, type, referenceId);
    }
}
