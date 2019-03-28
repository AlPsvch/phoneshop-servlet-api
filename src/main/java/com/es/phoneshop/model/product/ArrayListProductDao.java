package com.es.phoneshop.model.product;

import com.es.phoneshop.exceptions.ProductNotFoundException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListProductDao implements ProductDao {
    private static ArrayListProductDao instance;
    private Map<String, Comparator<Product>> comparatorMap = new HashMap<>();

    private ArrayListProductDao() {
        comparatorMap.put("description", Comparator.comparing(Product::getDescription));
        comparatorMap.put("price", Comparator.comparing(Product::getPrice));
    }

    public static synchronized ArrayListProductDao getInstance() {
        if (instance == null) {
            instance = new ArrayListProductDao();
        }
        return instance;
    }

    private List<Product> products = new ArrayList<>();

    @Override
    public synchronized Product getProduct(Long id) {
        return findActualProducts()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product with code " + id + " not found."));
    }

    @Override
    public synchronized List<Product> findProducts(String query, String order, String sort) {
        List<Product> actualProducts = findActualProducts().collect(Collectors.toList());

        if (query == null || query.trim().isEmpty()) {
            return sortProducts(actualProducts, order, sort);
        }

        List<String> words = Arrays.asList(query.trim().toLowerCase().split(" "));

        Map<Product, Integer> productIntegerMap = actualProducts.stream()
                .collect(Collectors.toMap(Function.identity(), product -> numberOfCoincidences(product, words)));

        List<Product> productsByQuery = actualProducts.stream()
                .filter(product -> productIntegerMap.get(product) > 0)
                .sorted(Comparator.<Product>comparingInt(productIntegerMap::get).reversed())
                .collect(Collectors.toList());

        return sortProducts(productsByQuery, order, sort);
    }

    @Override
    public synchronized void save(Product product) {
        if (products.stream()
                .anyMatch(p -> p.getId().equals(product.getId()))) {
            throw new IllegalArgumentException("Product with such ID already exists");
        } else {
            products.add(product);
        }
    }

    @Override
    public synchronized void delete(Long id) {
        products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .map(p -> products.remove(p))
                .orElseThrow(() -> new ProductNotFoundException("Product with code " + id + " not found."));
    }

    private Stream<Product> findActualProducts() {
        return products.stream()
                .filter(p -> p.getPrice() != null && p.getStock() > 0);
    }

    private int numberOfCoincidences(Product product, List<String> words) {
        return (int) words.stream()
                .filter(product.getDescription().toLowerCase()::contains)
                .count();
    }

    private List<Product> sortProducts(List<Product> unsortedProducts, String order, String sort) {
        if (sort == null || sort.isEmpty())
            return unsortedProducts;

        return unsortedProducts.stream()
                .sorted(getComparator(order, sort))
                .collect(Collectors.toList());
    }

    private Comparator<Product> getComparator(String order, String sort) {
        if (!comparatorMap.containsKey(sort)) {
            throw new IllegalArgumentException("There is no comparator with key " + sort);
        }
        Comparator<Product> comparator = comparatorMap.get(sort);
        return "desc".equals(order) ? comparator.reversed() : comparator;
    }
}