package com.es.phoneshop.model.cart;

import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class HttpSessionCartService implements CartService {
    private static final String SESSION_CART_KEY = "sessionCart";
    private static CartService instance;

    public static synchronized CartService getInstance() {
        if (instance == null) {
            instance = new HttpSessionCartService();
        }
        return instance;
    }

    @Override
    public synchronized Cart getCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(SESSION_CART_KEY);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(SESSION_CART_KEY, cart);
        }
        return cart;
    }

    @Override
    public synchronized void add(Cart cart, long productId, int quantity) throws OutOfStockException {
        Product product = ArrayListProductDao.getInstance().getProduct(productId);

        if (quantity > product.getStock() || quantity <= 0) {
            throw new OutOfStockException("Not enough stock or incorrect stock input. Product stock is " + product.getStock());
        }

        Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                .filter(cartItem -> Long.valueOf(productId).equals(cartItem.getProduct().getId()))
                .findAny();

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();

            if ((cartItem.getQuantity() + quantity) > product.getStock()) {
                throw new OutOfStockException("Not enough stock. Product stock is " + product.getStock());
            }

            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            CartItem cartItem = new CartItem(product, quantity);
            cart.getCartItems().add(cartItem);
        }
    }
}
