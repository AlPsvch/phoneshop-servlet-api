package com.es.phoneshop.web;

import com.es.phoneshop.model.productReview.ProductReview;
import com.es.phoneshop.model.productReview.ReviewService;
import com.es.phoneshop.model.productReview.ReviewServiceImpl;
import com.es.phoneshop.util.ReviewUtility;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ProductReviewsServlet extends HttpServlet {
    private ReviewService reviewService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        reviewService = ReviewServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/fragments/productReview.jsp").include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
        String productIdStr = request.getParameter("productId");
        Long productId = Long.valueOf(productIdStr);

        Optional<ProductReview> productReviewOptional = ReviewUtility.getReview(request, productId);
        if (!productReviewOptional.isPresent()) {
            responce.sendRedirect(request.getContextPath() + "/products/" + productId + "?reviewError=Incorrect review");
            return;
        }

        ProductReview productReview = productReviewOptional.get();
        reviewService.save(productReview);

        responce.sendRedirect(request.getContextPath() + "/products/" + productId + "?reviewMessage=Commented successfully");
    }
}
