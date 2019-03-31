package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private HttpSession session;

    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();
    private ArrayListProductDao productDao = ArrayListProductDao.getInstance();

    @Before
    public void setup() {
        Currency usd = Currency.getInstance("USD");
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 5, ""));
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getRequestURI()).thenReturn("/phoneshop-servlet-api/products/1");
        when(request.getServletPath()).thenReturn("/products");
        when(request.getSession()).thenReturn(session);
    }

    @After
    public void complete() {
        productDao.delete(1L);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.init(servletConfig);

        servlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        String quantity = "1";
        when(request.getParameter(ProductDetailsPageServlet.QUANTITY)).thenReturn(quantity);

        servlet.init(servletConfig);

        servlet.doPost(request, response);

        verify(response).sendRedirect(anyString());
    }
}
