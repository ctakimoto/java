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

public class AdvancedStreamsExample04 {

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        // Using Collectors.toMap() to compute the total price of products per category.
        Map<Category, BigDecimal> totalPerCategory = products.stream()
                .collect(Collectors.toMap(
                        Product::getCategory,   // Key mapper function
                        Product::getPrice,      // Value mapper function
                        BigDecimal::add));      // Merge function
        System.out.println(totalPerCategory);
    }
}
