package com.es.phoneshop.web;

import com.es.phoneshop.model.order.ContactDetails;
import com.es.phoneshop.util.OrderUtility;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutPageServletTest {
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


    private CheckoutPageServlet servlet = new CheckoutPageServlet();
    private String string = "a";
    private String phone = "+123456789012";


    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.init(servletConfig);

        servlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(request.getParameter("firstName")).thenReturn(string);
        when(request.getParameter("lastName")).thenReturn(string);
        when(request.getParameter("phone")).thenReturn(phone);
        when(request.getParameter("address")).thenReturn(string);
        when(request.getParameter("deliveryMode")).thenReturn("Courier");
        when(request.getParameter("paymentMethod")).thenReturn("Money");


        servlet.init(servletConfig);

        servlet.doPost(request, response);

        verify(response).sendRedirect(anyString());
    }
}
