package com.es.phoneshop.util;

import javax.servlet.http.HttpServletRequest;

public class ProductUtility {
    public static Long getProductId(HttpServletRequest request) {
        String uri = request.getRequestURI();
        int index = uri.indexOf(request.getServletPath());
        String productId = uri.substring(index + request.getServletPath().length() + 1);

        return Long.valueOf(productId);
    }
}
