/*
 * This code is part of the course "Working with Streams and Lambda Expressions in Java (Java SE 11 Developer Certification 1Z0-819)" for Pluralsight.
 *
 * Copyright (C) 2021 by Jesper de Jong (jesper@jdj-it.com).
 */
package org.shitaki.java_8.stream.advanced;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.DoubleStream;

public class AdvancedStreamsExample07 {

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        // DoubleStream is a stream that contains primitive double values.
        // Compared to Stream<Double>, this avoids boxing and unboxing primitive double values into and out of Double wrapper objects.
        DoubleStream prices = products.stream()
                .mapToDouble(product -> product.getPrice().doubleValue());

        // There are some methods that are not available on regular streams, such as sum().
        double total = prices.sum();
        System.out.printf("The sum of all product prices is $ %.2f%n", total);

        // The method summaryStatistics() provides statistics about the elements in the stream.
        // See the API documentation of the specialized streams for more methods.
        DoubleSummaryStatistics statistics = products.stream()
                .mapToDouble(product -> product.getPrice().doubleValue())
                .summaryStatistics();

        System.out.printf("Count  : %d%n", statistics.getCount());
        System.out.printf("Sum    : $ %.2f%n", statistics.getSum());
        System.out.printf("Minimum: $ %.2f%n", statistics.getMin());
        System.out.printf("Maximum: $ %.2f%n", statistics.getMax());
        System.out.printf("Average: $ %.2f%n", statistics.getAverage());
    }
}
