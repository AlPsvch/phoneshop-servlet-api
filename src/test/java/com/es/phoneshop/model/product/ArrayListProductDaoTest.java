package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Mock
    private Currency usd;
    @Mock
    private String imageURL;

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts(null).isEmpty());
    }

    @Test
    public void testSaveAndDelete() {
        Product product = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, imageURL);

        productDao.save(product);
        assertEquals(1, productDao.findProducts(null).size());
        productDao.delete(1L);
        assertEquals(0, productDao.findProducts(null).size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetProduct() {
        productDao.getProduct(1L);
    }

    @Test
    public void testFindProducts() {
        Product product1 = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 0, imageURL);
        Product product2 = new Product(2L, "sgs", "Samsung Galaxy S", null, usd, 100, imageURL);
        Product product3 = new Product(3L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, imageURL);

        productDao.save(product1);
        productDao.save(product2);
        productDao.save(product3);

        assertEquals(1, productDao.findProducts("galaxy").size());
    }
}
