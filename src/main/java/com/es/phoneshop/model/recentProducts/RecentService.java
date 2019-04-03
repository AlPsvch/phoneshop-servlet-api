package com.es.phoneshop.model.recentProducts;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

public interface RecentService {

    public RecentViews getRecentViews(HttpServletRequest request);
    public void add(RecentViews recentViews, long productId);
}
