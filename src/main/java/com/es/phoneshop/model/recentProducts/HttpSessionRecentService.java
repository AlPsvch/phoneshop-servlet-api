package com.es.phoneshop.model.recentProducts;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

public class HttpSessionRecentService implements RecentService {
    private static final String SESSION_RECENT_PRODUCTS_KEY = "sessionRecentViews";
    private static RecentService instance;

    public static synchronized RecentService getInstance() {
        if (instance == null) {
            instance = new HttpSessionRecentService();
        }
        return instance;
    }

    @Override
    public synchronized RecentViews getRecentViews(HttpServletRequest request) {
        HttpSession session = request.getSession();
        RecentViews recentViews = (RecentViews) session.getAttribute(SESSION_RECENT_PRODUCTS_KEY);
        if (recentViews == null) {
            recentViews = new RecentViews();
            session.setAttribute(SESSION_RECENT_PRODUCTS_KEY, recentViews);
        }
        return recentViews;
    }

    @Override
    public synchronized void add(RecentViews recentViews, long productId) {
        Product product = ArrayListProductDao.getInstance().getProduct(productId);
        Deque<Product> recentViewsList = recentViews.getRecentlyViewed();

        Optional<Product> productOptional = recentViewsList.stream()
                .filter(p -> Long.valueOf(productId).equals(p.getId()))
                .findAny();

        if (productOptional.isPresent()) {
            recentViewsList.remove(product);
            recentViewsList.addFirst(product);
        } else {
            recentViewsList.addFirst(product);
            if (recentViewsList.size() > 3) {
                recentViewsList.removeLast();
            }
        }
    }
}
