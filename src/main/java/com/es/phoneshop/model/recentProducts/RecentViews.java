package com.es.phoneshop.model.recentProducts;

import com.es.phoneshop.model.product.Product;

import java.util.Deque;
import java.util.LinkedList;

public class RecentViews {
    private Deque<Product> recentlyViewed;

    public RecentViews() {
        recentlyViewed = new LinkedList<>();
    }

    public Deque<Product> getRecentlyViewed() {
        return recentlyViewed;
    }
}
