/*
 * This code is part of the course "Working with Streams and Lambda Expressions in Java (Java SE 11 Developer Certification 1Z0-819)" for Pluralsight.
 *
 * Copyright (C) 2021 by Jesper de Jong (jesper@jdj-it.com).
 */
package org.shitaki.java_8.lambda;

import java.math.BigDecimal;
import java.util.List;

public class LambdasExample03 {

    interface ProductFilter {
        boolean accept(Product product);
    }

    // Print the products that are accepted by the filter.
    static void printProducts(List<Product> products, ProductFilter filter) {
        for (Product product : products) {
            if (filter.accept(product)) {
                System.out.println(product);
            }
        }
    }

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        BigDecimal priceLimit = new BigDecimal("5.00");

        // This lambda expression captures the local variable priceLimit.
        // The variable must be effectively final; if it is not, an error will appear in the lambda expression.
        ProductFilter filter = product -> product.getPrice().compareTo(priceLimit) < 0;

        // Reassigning the variable, even after the lambda expression, makes it not effectively final.
        // priceLimit = new BigDecimal("6.00");

        printProducts(products, filter);
    }
}
