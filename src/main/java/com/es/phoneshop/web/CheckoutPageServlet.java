package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.order.*;
import com.es.phoneshop.util.OrderUtility;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class CheckoutPageServlet extends HttpServlet {
    protected static final String CART = "cart";
    protected static final String ORDER = "order";
    protected static final String DELIVERY = "deliveryModes";
    protected static final String PAYMENT = "paymentMethods";

    private CartService cartService;
    private OrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        cartService = HttpSessionCartService.getInstance();
        orderService = OrderServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);

        if(cart.getCartItems().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart?emptyMessage=Cart is empty");
            return;
        }

        request.setAttribute(ORDER, orderService.createOrder(cart));
        request.setAttribute(DELIVERY, DeliveryMode.getDeliveryModes());
        request.setAttribute(PAYMENT, PaymentMethod.getPaymentMethods());
        request.getRequestDispatcher("/WEB-INF/pages/order.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        Order order = orderService.createOrder(cart);
        ContactDetails contactDetails;

        Optional<ContactDetails> contactDetailsOptional = OrderUtility.getContactDetails(request);
        if (!contactDetailsOptional.isPresent()) {
            doGet(request, response);
            return;
        } else {
            contactDetails = contactDetailsOptional.get();
        }

        String deliveryModeString = request.getParameter("deliveryMode");
        DeliveryMode deliveryMode = DeliveryMode.getDeliveryMode(deliveryModeString);

        String paymentMethodString = request.getParameter("paymentMethod");
        PaymentMethod paymentMethod = PaymentMethod.getPaymentMethod(paymentMethodString);

        orderService.placeOrder(order, contactDetails, deliveryMode, paymentMethod);
        cartService.clearCart(cart);

        response.sendRedirect(request.getContextPath() + "/order/overview/" + order.getSecureId());
    }
}
