/*
 * This code is part of the course "Working with Streams and Lambda Expressions in Java (Java SE 11 Developer Certification 1Z0-819)" for Pluralsight.
 *
 * Copyright (C) 2021 by Jesper de Jong (jesper@jdj-it.com).
 */
package org.shitaki.java_8.stream.advanced;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdvancedStreamsExample05 {

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        // Group products by category. The classifier function that you pass to groupingBy() determines how to split the products into groups.
        Map<Category, List<Product>> productsByCategory = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory));

        // Suppose that we want to get a map of product names per category (instead of products).
        // Mapping products to product names with map(...) won't work, because the result of the map(...) operation is a stream of strings.
        // The information about the categories will have been thrown away, so you can't group on categories anymore in the collector.
//        Map<Category, List<String>> productNamesByCategory = products.stream()
//                .map(Product::getName)
//                .collect(Collectors.groupingBy(...));

        // Create a Map of product names grouped by category.
        // We use a downstream collector, created by Collectors.mapping(...), to map grouped products to product names.
        // Note that Collectors.mapping(...) takes a mapping function and another downstream collector to determine how to collect its result.
        Map<Category, List<String>> productNamesByCategory = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.mapping(Product::getName, Collectors.toList())));

        // Print the product names for each category.
        productNamesByCategory.forEach((category, names) -> {
            System.out.println(category);
            names.forEach(name -> System.out.println("- " + name));
        });

        // Challenge: This line calculates the total price of products per category, just like in the previous example (AdvancedStreamsExample04),
        // but by using groupingBy() and multiple downstream collectors. Can you explain exactly how this works?
        // Use the API documentation of class Collectors to learn about the factory methods.
        Map<Category, BigDecimal> totalPerCategory = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.mapping(Product::getPrice, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
    }
}
