package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.productReview.ReviewService;
import com.es.phoneshop.model.productReview.ReviewServiceImpl;
import com.es.phoneshop.model.recentProducts.HttpSessionRecentService;
import com.es.phoneshop.model.recentProducts.RecentService;
import com.es.phoneshop.model.recentProducts.RecentViews;
import com.es.phoneshop.util.ProductUtility;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    protected static final String QUANTITY = "quantity";
    protected static final String ERROR = "error";
    private ProductDao productDao;
    private CartService cartService;
    private RecentService recentService;
    private ReviewService reviewService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
        recentService = HttpSessionRecentService.getInstance();
        reviewService = ReviewServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = ProductUtility.getProductId(request);
        RecentViews recentViews = recentService.getRecentViews(request);

        request.setAttribute("recentProducts", recentViews.getRecentlyViewed());
        request.setAttribute("product", productDao.getProduct(productId));
        request.setAttribute("cart", cartService.getCart(request));
        request.setAttribute("submittedComments", reviewService.getSubmittedComments());
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        recentService.add(recentViews, productId);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = ProductUtility.getProductId(request);
        Integer quantity;

        try {
            quantity = Integer.valueOf(request.getParameter(QUANTITY));
        } catch (NumberFormatException exception) {
            request.setAttribute(ERROR, "Not a number");
            doGet(request, response);
            return;
        }

        Cart cart = cartService.getCart(request);

        try {
            cartService.add(cart, productId, quantity);
        } catch (OutOfStockException exception) {
            request.setAttribute(ERROR, exception.getMessage());
            doGet(request, response);
            return;
        }

        response.sendRedirect(request.getRequestURI() + "?message=Added succesfully&quantity=" + quantity);
    }
}
