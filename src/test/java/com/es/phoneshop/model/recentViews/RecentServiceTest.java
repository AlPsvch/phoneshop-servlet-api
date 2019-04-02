package com.es.phoneshop.model.recentViews;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.recentProducts.HttpSessionRecentService;
import com.es.phoneshop.model.recentProducts.RecentService;
import com.es.phoneshop.model.recentProducts.RecentViews;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RecentServiceTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;

    private RecentService recentService = HttpSessionRecentService.getInstance();
    private ProductDao productDao = ArrayListProductDao.getInstance();
    private RecentViews recentViews = new RecentViews();

    @Before
    public void setup() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(recentViews);
        Product product = new Product(1L, null, null, new BigDecimal(100), null, 5, null);
        Product product1 = new Product(2L, null, null, new BigDecimal(100), null, 5, null);
        Product product2 = new Product(3L, null, null, new BigDecimal(100), null, 5, null);
        Product product3 = new Product(4L, null, null, new BigDecimal(100), null, 5, null);

        productDao.save(product);
        productDao.save(product1);
        productDao.save(product2);
        productDao.save(product3);
    }

    @After
    public void complete() {
        productDao.delete(1L);
        productDao.delete(2L);
        productDao.delete(3L);
        productDao.delete(4L);
    }

    @Test
    public void testGetRecentViews() {
        recentService.getRecentViews(request);
        verify(session, never()).setAttribute(anyString(), any());
    }

    @Test
    public void testAdd() {
        assertEquals(0, recentService.getRecentViews(request).getRecentlyViewed().size());

        recentService.add(recentViews, 1L);
        recentService.add(recentViews, 2L);
        recentService.add(recentViews, 1L);

        assertEquals(2, recentService.getRecentViews(request).getRecentlyViewed().size());

        recentService.add(recentViews, 3L);
        recentService.add(recentViews, 4L);

        assertEquals(3, recentService.getRecentViews(request).getRecentlyViewed().size());
    }
}
