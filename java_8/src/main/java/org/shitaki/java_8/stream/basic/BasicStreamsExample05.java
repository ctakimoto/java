/*
 * This code is part of the course "Working with Streams and Lambda Expressions in Java (Java SE 11 Developer Certification 1Z0-819)" for Pluralsight.
 *
 * Copyright (C) 2021 by Jesper de Jong (jesper@jdj-it.com).
 */
package org.shitaki.java_8.stream.basic;

import java.util.List;
import java.util.stream.Collectors;

public class BasicStreamsExample05 {

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        // This is not a good way to get the elements of a stream into a list
//        List<String> foodProductNames = new ArrayList<>();
//        products.stream()
//                .filter(product -> product.getCategory() == Category.FOOD)
//                .map(Product::getName)
//                .forEach(foodProductNames::add);

        // Using collect() you can collect the elements of a stream into a collection
        // Class Collectors contains factory methods for collectors that create different kinds of collections
        List<String> foodProductNames = products.stream()
                .filter(product -> product.getCategory() == Category.FOOD)
                .map(Product::getName)
                .collect(Collectors.toList());

        System.out.println(foodProductNames);

        // Collectors.joining(...) returns a collector to reduce stream elements into a string
        String categories = products.stream()
                .map(Product::getCategory)
                .distinct()
                .map(Category::name)
                .collect(Collectors.joining("; "));

        System.out.println(categories);
    }
}
