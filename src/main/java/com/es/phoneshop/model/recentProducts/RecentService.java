package com.es.phoneshop.model.recentProducts;

import javax.servlet.http.HttpServletRequest;

public interface RecentService {

    RecentViews getRecentViews(HttpServletRequest request);

    void add(RecentViews recentViews, long productId);
}
