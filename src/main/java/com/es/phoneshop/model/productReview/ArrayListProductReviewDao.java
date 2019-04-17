package com.es.phoneshop.model.productReview;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductReviewDao implements ProductReviewDao {
    private static ProductReviewDao instance;

    List<ProductReview> reviews = new ArrayList<>();
    List<ProductReview> submittedReviews = new ArrayList<>();

    public static synchronized ProductReviewDao getInstance() {
        if(instance == null) {
            instance = new ArrayListProductReviewDao();
        }
        return instance;
    }

    @Override
    public synchronized List<ProductReview> getReviews(Long id) {
        return reviews.stream()
                .filter(review -> review.getProductId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void saveReview(ProductReview review) {
        reviews.add(review);
    }

    @Override
    public synchronized void deleteReview(ProductReview review) {
        reviews.removeIf(review1 -> review1.equals(review));
    }

    @Override
    public List<ProductReview> getSubmittedReviews() {
        return submittedReviews;
    }
}
