package com.es.phoneshop.util;

import com.es.phoneshop.model.productReview.ProductReview;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ReviewUtility {
    public static Optional<ProductReview> getReview(HttpServletRequest request, Long productId) {
        boolean hasError = false;

        String commentatorName = request.getParameter("commentatorName");
        if(isInvalid(commentatorName)) {
            hasError = true;
            request.setAttribute("nameError", "Name is required");
        }

        String rating = request.getParameter("rating");
        if(isInvalid(rating) || isInvalidRating(rating)) {
            hasError = true;
            request.setAttribute("ratingError", "Rating is required");
        }

        String comment = request.getParameter("comment");
        if(isInvalid(comment)) {
            hasError = true;
            request.setAttribute("commentError", "Comment is required");
        }

        if(hasError) {
            return Optional.empty();
        } else {
            return Optional.of(new ProductReview(productId, commentatorName, rating, comment));
        }
    }

    private static boolean isInvalid(String s) {
        return s == null || s.isEmpty();
    }

    private static boolean isInvalidRating(String rating) {
        Integer intRating;
        try {
            intRating = Integer.valueOf(rating);
        } catch (NumberFormatException e) {
            return true;
        }

        return intRating > 5 || intRating < 1;
    }
}
