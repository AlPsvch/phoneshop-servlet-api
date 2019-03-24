package com.es.phoneshop.model.product;

import com.es.phoneshop.exceptions.ProductNotFoundException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListProductDao implements ProductDao {
    private static ArrayListProductDao instance;

    public static synchronized ArrayListProductDao getInstance(){
        if(instance == null){
            instance = new ArrayListProductDao();
        }
        return instance;
    }

    private List<Product> products = new ArrayList<>();

    @Override
    public synchronized Product getProduct(Long id) throws ProductNotFoundException {
        return findActualProducts()
                .filter((p) -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product with code " + id + " not found."));
    }

    @Override
    public synchronized List<Product> findProducts(String query, String order, String sort) {
        List<Product> finalProducts = findUnsortedProducts(query);

        if(sort!= null && !sort.isEmpty()){
            return sortProducts(finalProducts, order, sort);
        }

        return finalProducts;
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
    public synchronized void delete(Long id) throws ProductNotFoundException {
        products.stream()
                .filter((p) -> p.getId().equals(id))
                .findFirst()
                .map((p) -> products.remove(p))
                .orElseThrow(() -> new ProductNotFoundException("Product with code " + id + " not found."));
    }

    private Stream<Product> findActualProducts() {
        return products.stream()
                .filter((p) -> p.getPrice() != null && p.getStock() > 0);
    }

    private List<Product> findUnsortedProducts(String query) {
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

    private List<Product> sortProducts(List<Product> unsortedProducts, String order, String sort){
        List<Product> sortedProducts = unsortedProducts;
        if ("description".equalsIgnoreCase(sort)) {
            sortedProducts = sortedProducts
                    .stream()
                    .sorted("asc".equalsIgnoreCase(order) ?
                            Comparator.comparing(Product::getDescription) :
                            Comparator.comparing(Product::getDescription).reversed())
                    .collect(Collectors.toList());
        } else if ("price".equalsIgnoreCase(sort)) {
            sortedProducts = sortedProducts
                    .stream()
                    .sorted("asc".equalsIgnoreCase(order) ?
                            Comparator.comparing(Product::getPrice) :
                            Comparator.comparing(Product::getPrice).reversed())
                    .collect(Collectors.toList());
        }
        return sortedProducts;
    }
}