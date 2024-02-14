/*
 * This code is part of the course "Working with Streams and Lambda Expressions in Java (Java SE 11 Developer Certification 1Z0-819)" for Pluralsight.
 *
 * Copyright (C) 2021 by Jesper de Jong (jesper@jdj-it.com).
 */
package org.shitaki.java_8.stream.advanced;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class AdvancedStreamsExample02 {

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        // The first version of reduce() takes a BinaryOperator to accumulate elements into a final result
        // It returns an Optional; if the stream is empty, the result is an empty Optional
        Optional<BigDecimal> opt = products.stream()
                .map(Product::getPrice)
                .reduce((result, element) -> result.add(element)); // Can also be written with a method reference: BigDecimal::add

        opt.ifPresentOrElse(
                total -> System.out.printf("The total value of all products is: $ %.2f%n", total),
                () -> System.out.println("There are no products"));

        // The second version of reduce() takes a starting value and a BinaryOperator
        // It returns a value instead of an Optional; if the stream is empty, the result will be the starting value
        BigDecimal total = products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.printf("The total value of all products is: $ %.2f%n", total);

        // The third version of reduce() is the most general
        // The type of its result value may be different than the type of the elements in the stream
        // The third parameter is a combiner function to combine intermediate results; this is useful for example for a parallel
        // stream, where different threads compute intermediate results that have to be combined into a final result
        BigDecimal total2 = products.stream()
                .reduce(BigDecimal.ZERO, (result, product) -> result.add(product.getPrice()), BigDecimal::add);
        System.out.printf("The total value of all products is: $ %.2f%n", total2);
    }
}
