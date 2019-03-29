package com.es.phoneshop.model.product;

import com.es.phoneshop.exceptions.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Mock
    private Currency usd;
    @Mock
    private String imageURL;

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts(null, null, null).isEmpty());
    }

    @Test
    public void testSaveAndDelete() {
        Product product = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, imageURL);

        productDao.save(product);
        assertEquals(1, productDao.findProducts(null, null, null).size());
        productDao.delete(1L);
        assertEquals(0, productDao.findProducts(null, null, null).size());
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductNotFound() {
        productDao.getProduct(1L);
    }

    @Test
    public void testFindProductsWithoutQuery() {
        Product product1 = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 0, imageURL);
        Product product2 = new Product(2L, "sgs", "Samsung Galaxy S", null, usd, 100, imageURL);
        Product product3 = new Product(3L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, imageURL);
        Product product4 = new Product(4L, "iph6", "Apple Iphone 6", new BigDecimal(100), usd, 100, imageURL);

        productDao.save(product1);
        productDao.save(product2);
        productDao.save(product3);

        assertEquals(1, productDao.findProducts(null, null, null).size());

        productDao.save(product4);
        assertEquals(2, productDao.findProducts("galaxy apple", null, null).size());

        productDao.delete(1L);
        productDao.delete(2L);
        productDao.delete(3L);
        productDao.delete(4L);

    }

    @Test
    public void testFindProductsWithQuery() {
        Product product1 = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 5, imageURL);
        Product product2 = new Product(2L, "iph6", "Apple Iphone 6", new BigDecimal(100), usd, 100, imageURL);
        Product product3 = new Product(3L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 0, imageURL);

        productDao.save(product1);
        productDao.save(product2);
        productDao.save(product3);

        assertEquals(1, productDao.findProducts("samsung", null, null).size());
        assertEquals(1, productDao.findProducts("galaxy samsung", null, null).size());
        assertEquals(2, productDao.findProducts("samsung apple", null, null).size());

        productDao.delete(1L);
        productDao.delete(2L);
        productDao.delete(3L);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDeleteNotFound() {
        productDao.delete(1L);
    }
}
