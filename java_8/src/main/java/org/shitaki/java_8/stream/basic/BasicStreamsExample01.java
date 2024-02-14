/*
 * This code is part of the course "Working with Streams and Lambda Expressions in Java (Java SE 11 Developer Certification 1Z0-819)" for Pluralsight.
 *
 * Copyright (C) 2021 by Jesper de Jong (jesper@jdj-it.com).
 */
package org.shitaki.java_8.stream.basic;

import java.util.List;
import java.util.stream.Stream;

public class BasicStreamsExample01 {

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        // Streams are lazy - without a terminal operation, no work is done
        // When you call intermediate operations on a stream, you're only building the pipeline;
        // no elements are flowing through it yet
        Stream<Product> stream = products.stream().map(product -> {
            System.out.println(product);
            return product;
        });

        // When you call a terminal operation, the stream will do the work
        stream.forEach(product -> {});
    }
}
