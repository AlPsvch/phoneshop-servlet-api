package com.es.phoneshop.model.productReview;

import com.es.phoneshop.model.productReview.ProductReview;

import java.util.List;

public interface ReviewService {
    void save(ProductReview productReview);

    List<ProductReview> getSubmittedComments();
}
