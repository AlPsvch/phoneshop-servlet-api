package com.es.phoneshop.model.cart;

import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;

    private CartService cartService = HttpSessionCartService.getInstance();
    private ProductDao productDao = ArrayListProductDao.getInstance();
    private Cart cart = new Cart();

    @Before
    public void setup() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(cart);
        Product product = new Product(1L, null, null, new BigDecimal(100), null, 5, null);
        productDao.save(product);
    }

    @After
    public void complete() {
        productDao.delete(1L);
    }

    @Test
    public void testGetCart() {
        cartService.getCart(request);
        verify(session, never()).setAttribute(anyString(), any());
    }

    @Test
    public void testAdd() throws OutOfStockException {
        assertEquals(0, cartService.getCart(request).getCartItems().size());
        cartService.add(cart, 1L, 1);
        assertEquals(1, cartService.getCart(request).getCartItems().size());
        cartService.add(cart, 1L, 1);
        assertEquals(1, cartService.getCart(request).getCartItems().size());
    }

    @Test(expected = OutOfStockException.class)
    public void testAddWithOverflow() throws OutOfStockException {
        cartService.add(cart, 1L, 10);
    }

    @Test(expected = OutOfStockException.class)
    public void testAddWithNegativeQuantity() throws OutOfStockException {
        cartService.add(cart, 1L, -5);
    }
}
