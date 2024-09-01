package com.estore.repository;

import com.estore.dto.ReviewDTO;

import java.util.List;
import java.util.Map;

public interface ReviewRepository {
    void addReview(ReviewDTO review, String reviewType);
    List<Map<String, Object>> getReviewsByReferenceId(int referenceId, String type);
}
