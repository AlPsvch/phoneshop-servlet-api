package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.recentProducts.HttpSessionRecentService;
import com.es.phoneshop.model.recentProducts.RecentService;
import com.es.phoneshop.model.recentProducts.RecentViews;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class ProductListPageServlet extends HttpServlet {
    protected static final String QUERY = "query";
    protected static final String ORDER = "order";
    protected static final String SORT = "sort";
    private ProductDao productDao;
    private RecentService recentService;
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        productDao = ArrayListProductDao.getInstance();
        recentService = HttpSessionRecentService.getInstance();
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter(QUERY);
        String order = request.getParameter(ORDER);
        String sort = request.getParameter(SORT);
        RecentViews recentViews = recentService.getRecentViews(request);

        request.setAttribute("recentProducts", recentViews.getRecentlyViewed());
        request.setAttribute("products", productDao.findProducts(query, order, sort));
        request.setAttribute("cart", cartService.getCart(request));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
