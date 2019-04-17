package com.es.phoneshop.model.productReview;

import java.util.List;

public class ReviewServiceImpl implements ReviewService {
    private static ReviewService instance;

    public static synchronized ReviewService getInstance() {
        if (instance == null) {
            instance = new ReviewServiceImpl();
        }
        return instance;
    }

    @Override
    public synchronized void save(ProductReview productReview) {
        ArrayListProductReviewDao.getInstance().saveReview(productReview);
    }

    @Override
    public List<ProductReview> getSubmittedComments() {
        return ArrayListProductReviewDao.getInstance().getSubmittedReviews();
    }
}
