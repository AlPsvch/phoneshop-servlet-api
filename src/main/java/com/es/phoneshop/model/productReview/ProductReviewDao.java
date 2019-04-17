package com.es.phoneshop.model.productReview;

import java.util.List;

public interface ProductReviewDao {
    List<ProductReview> getReviews(Long id);

    void saveReview(ProductReview review);

    void deleteReview(ProductReview review);

    List<ProductReview> getSubmittedReviews();
}
