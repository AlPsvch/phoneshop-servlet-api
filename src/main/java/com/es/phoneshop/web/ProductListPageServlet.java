package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
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

public class ProductListPageServlet extends HttpServlet {
    protected static final String QUERY = "query";
    protected static final String ORDER = "order";
    protected static final String SORT = "sort";
    private ProductDao productDao;
    private RecentService recentService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        productDao = ArrayListProductDao.getInstance();
        recentService = HttpSessionRecentService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter(QUERY);
        String order = request.getParameter(ORDER);
        String sort = request.getParameter(SORT);
        RecentViews recentViews = recentService.getRecentViews(request);

        request.setAttribute("recentProducts", recentViews.getRecentlyViewed());
        request.setAttribute("products", productDao.findProducts(query, order, sort));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
