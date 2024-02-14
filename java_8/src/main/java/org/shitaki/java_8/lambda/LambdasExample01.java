/*
 * This code is part of the course "Working with Streams and Lambda Expressions in Java (Java SE 11 Developer Certification 1Z0-819)" for Pluralsight.
 *
 * Copyright (C) 2021 by Jesper de Jong (jesper@jdj-it.com).
 */
package org.shitaki.java_8.lambda;

import java.util.Comparator;
import java.util.List;

public class LambdasExample01 {

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        // Interface Comparator implemented with an anonymous class.
        products.sort(new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getPrice().compareTo(p2.getPrice());
            }
        });

        // Interface Comparator implemented with a lambda expression.
        products.sort((p1, p2) -> p1.getPrice().compareTo(p2.getPrice()));

        // The same with a more verbose syntax:
        // - You can optionally specify the type of the parameters
        // - The body can be a block between { and } or a single expression
        products.sort((Product p1, Product p2) -> {
            return p1.getPrice().compareTo(p2.getPrice());
        });

        for (Product product : products) {
            System.out.println(product);
        }
    }
}
