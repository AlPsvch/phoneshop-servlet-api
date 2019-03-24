package com.es.phoneshop.model.product;

import com.es.phoneshop.exceptions.ProductNotFoundException;
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
    public void testFindProducts() {
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
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDeleteNotFound(){
        productDao.delete(1L);
    }
}
