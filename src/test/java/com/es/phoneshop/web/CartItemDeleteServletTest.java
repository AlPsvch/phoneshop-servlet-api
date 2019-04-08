package com.es.phoneshop.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartItemDeleteServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private HttpSession session;

    private CartItemDeleteServlet servlet = new CartItemDeleteServlet();

    @Before
    public void setup() {
        when(request.getRequestURI()).thenReturn("/phoneshop-servlet-api/cart/deleteCartItem/1");
        when(request.getServletPath()).thenReturn("/cart/deleteCartItem");
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        servlet.init(servletConfig);

        servlet.doPost(request, response);

        verify(response).sendRedirect(anyString());
    }
}
