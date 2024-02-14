/*
 * This code is part of the course "Working with Streams and Lambda Expressions in Java (Java SE 11 Developer Certification 1Z0-819)" for Pluralsight.
 *
 * Copyright (C) 2021 by Jesper de Jong (jesper@jdj-it.com).
 */
package org.shitaki.java_8.stream.basic;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class BasicStreamsExample04 {

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        // findFirst() will return an Optional with the first element in the stream, or an empty Optional
        // This is often used together with filter() to search for an element on arbitrary criteria
        Optional<Product> opt = products.stream()
                .filter(product -> product.getCategory() == Category.OFFICE)
                .findFirst();
        opt.ifPresent(System.out::println);

        // If you only want to check if the stream contains an element that matches your search criteria,
        // then you can use anyMatch(), which will return a boolean result
        // Unlike findFirst() and findAny(), you don't have to filter first - anyMatch() takes a Predicate
        boolean foundOfficeProduct = products.stream()
                .anyMatch(product -> product.getCategory() == Category.OFFICE);
        System.out.println("Does the list contain at least one office product? " + foundOfficeProduct);

        // To check if all elements of the stream match specific criteria, use allMatch()
        BigDecimal priceLimit = new BigDecimal("10.00");
        boolean allProductsAreCheap = products.stream()
                .allMatch(product -> product.getPrice().compareTo(priceLimit) < 0);
        System.out.println("Are all products cheap? " + allProductsAreCheap);

        // noneMatch() returns the opposite of anyMatch()
        boolean allProductsAreExpensive = products.stream()
                .noneMatch(product -> product.getPrice().compareTo(priceLimit) < 0);
        System.out.println("Are all products expensive? " + allProductsAreExpensive);
    }
}
