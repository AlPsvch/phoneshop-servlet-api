package com.es.phoneshop.model.product;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListProductDao implements ProductDao {
    private List<Product> products = new ArrayList<>();

    @Override
    public synchronized Product getProduct(Long id) throws IllegalArgumentException {
        return findActualProducts()
                .filter((p) -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no product with such ID."));
    }

    @Override
    public synchronized List<Product> findProducts(String query) {
        if (query != null && !query.trim().isEmpty()) {
            String[] splitQuery = query.toLowerCase().split(" ");

            return findProductsByQuery(splitQuery);
        } else {
            return findActualProducts().collect(Collectors.toList());
        }
    }

    private List<Product> findProductsByQuery(String... query) {
        Map<Product, Integer> foundProductsMap = new HashMap<>();

        findActualProducts()
                .forEach((p) -> {
                            int number = numberOfCoincidences(p.getDescription(), query);

                            if (number > 0) {
                                foundProductsMap.put(p, number);
                            }
                        }
                );

        List<Product> productList = new ArrayList<>();

        foundProductsMap.entrySet().stream()
                .sorted(Map.Entry.<Product, Integer>comparingByValue().reversed())
                .forEach((p) -> productList.add(p.getKey()));

        return productList;
    }

    private int numberOfCoincidences(String description, String... query) {
        int number = 0;
        for (String q : query) {
            if (description.toLowerCase().contains(q.toLowerCase()))
                ++number;
        }

        return number;
    }

    @Override
    public synchronized void save(Product product) throws IllegalArgumentException {
        if (products.stream()
                .anyMatch((p) -> p.getId().equals(product.getId()))) {
            throw new IllegalArgumentException("Product with such ID already exists");
        } else {
            products.add(product);
        }
    }

    @Override
    public synchronized void delete(Long id) throws IllegalArgumentException {
        products.stream()
                .filter((p) -> p.getId().equals(id))
                .findFirst()
                .map((p) -> products.remove(p))
                .orElseThrow(() -> new IllegalArgumentException("There is no product with such ID."));
    }

    private Stream<Product> findActualProducts() {
        return products.stream()
                .filter((p) -> p.getPrice() != null && p.getStock() > 0);
    }
}
