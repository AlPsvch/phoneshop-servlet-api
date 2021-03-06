package com.es.phoneshop.model.cart;

import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;

public class HttpSessionCartService implements CartService {
    private static final String SESSION_CART_KEY = "sessionCart";
    private ProductDao productDao = ArrayListProductDao.getInstance();
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

        Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                .filter(cartItem -> Long.valueOf(productId).equals(cartItem.getProduct().getId()))
                .findAny();

        int totalQuantity = cartItemOptional.map(c -> c.getQuantity() + quantity).orElse(quantity);

        if (totalQuantity > product.getStock() || quantity <= 0) {
            throw new OutOfStockException("Not enough stock or incorrect stock input. Product stock is " + product.getStock());
        }

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(totalQuantity);
        } else {
            CartItem cartItem = new CartItem(product, totalQuantity);
            cart.getCartItems().add(cartItem);
        }

        recalculateTotalPrice(cart);
    }

    @Override
    public synchronized void update(Cart cart, long productId, int quantity) throws OutOfStockException {
        Product product = ArrayListProductDao.getInstance().getProduct(productId);

        if (quantity > product.getStock() || quantity <= 0) {
            throw new OutOfStockException("Not enough stock. Product stock is " + product.getStock());
        }

        CartItem cartItem = cart.getCartItems().stream()
                .filter(c -> Long.valueOf(productId).equals(c.getProduct().getId()))
                .findAny().orElseThrow(() -> new IllegalArgumentException("There is no product in cart with such ID"));

        cartItem.setQuantity(quantity);

        recalculateTotalPrice(cart);
    }

    @Override
    public synchronized void delete(Cart cart, long productId) {
        Long productIdLong = productId;
        cart.getCartItems().removeIf(cartItem -> productIdLong.equals(cartItem.getProduct().getId()));

        recalculateTotalPrice(cart);
    }

    @Override
    public void clearCart(Cart cart) {
        cart.getCartItems().
                forEach(cartItem -> productDao.getProduct(cartItem.getProduct().getId())
                        .setStock(cartItem.getProduct().getStock() - cartItem.getQuantity()));

        cart.getCartItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
    }

    private void recalculateTotalPrice(Cart cart) {
        BigDecimal newTotalPrice = cart.getCartItems().stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.valueOf(0));
        cart.setTotalPrice(newTotalPrice);
    }
}
