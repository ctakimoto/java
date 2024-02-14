/*
 * This code is part of the course "Working with Streams and Lambda Expressions in Java (Java SE 11 Developer Certification 1Z0-819)" for Pluralsight.
 *
 * Copyright (C) 2021 by Jesper de Jong (jesper@jdj-it.com).
 */
package org.shitaki.java_8.stream.advanced;

import java.util.ArrayList;
import java.util.List;

public class AdvancedStreamsExample03 {

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        // You can reduce stream elements into an ArrayList with reduce(), but this is inefficient because reduce()
        // is designed for the result container to be immutable; so you need to create intermediate lists and
        // copy elements between them.
        List<String> productNames1 = products.stream().reduce(
                new ArrayList<>(),
                (list, product) -> {
                    ArrayList<String> newList = new ArrayList<>(list);
                    newList.add(product.getName());
                    return newList;
                },
                (list1, list2) -> {
                    ArrayList<String> newList = new ArrayList<>(list1);
                    newList.addAll(list2);
                    return newList;
                });

        // Collection is mutable reduction. This is much more efficient if you have a mutable result container
        // such as an ArrayList, avoiding most of the copying that needs to be done when using reduce().
        List<String> productNames2 = products.stream().collect(
                ArrayList::new,
                (list, product) -> list.add(product.getName()),
                ArrayList::addAll);
    }
}
