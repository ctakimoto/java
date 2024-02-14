/*
 * This code is part of the course "Working with Streams and Lambda Expressions in Java (Java SE 11 Developer Certification 1Z0-819)" for Pluralsight.
 *
 * Copyright (C) 2021 by Jesper de Jong (jesper@jdj-it.com).
 */
package org.shitaki.java_8.functionalinterface;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionalInterfacesExample03 {

    // Go through a list of products, and return the first product for which the predicate returns true.
    static Optional<Product> findProduct(List<Product> products, Predicate<Product> predicate) {
        for (Product product : products) {
            if (predicate.test(product)) {
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        String name = "Spaghetti";

        Function<Product, BigDecimal> productToPrice = Product::getPrice;
        Function<BigDecimal, String> priceToMessage = price -> String.format("The price of %s is $ %.2f%n", name, price);

        // Compose a new function out of the two functions above by using andThen(...)
        Function<Product, String> productToMessage = productToPrice.andThen(priceToMessage);

        // Alternative: use compose(...), which is the same as andThen(...) except that the order of the functions is reversed
//        Function<Product, String> productToMessage = priceToMessage.compose(productToPrice);

        findProduct(products, product -> product.getName().equals(name))
                .map(productToMessage)
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.printf("%s is not available%n", name));
    }
}
