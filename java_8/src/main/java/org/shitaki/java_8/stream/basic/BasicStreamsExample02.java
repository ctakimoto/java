/*
 * This code is part of the course "Working with Streams and Lambda Expressions in Java (Java SE 11 Developer Certification 1Z0-819)" for Pluralsight.
 *
 * Copyright (C) 2021 by Jesper de Jong (jesper@jdj-it.com).
 */
package org.shitaki.java_8.stream.basic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BasicStreamsExample02 {

    public static void main(String[] args) {
        List<Product> products = ExampleData.getProducts();

        // Get a stream from a collection
        Stream<Product> stream1 = products.stream();

        // Get a stream from an array
        String[] array = new String[]{"one", "two", "three"};
        Stream<String> stream2 = Arrays.stream(array);

        // Create a Stream from elements directly
        Stream<String> stream3 = Stream.of("one", "two", "three");

        // Create a Stream with zero or one elements with ofNullable()
        Stream<String> stream4 = Stream.ofNullable("four");

        // Create an empty Stream with Stream.empty()
        Stream<?> stream5 = Stream.empty();

        // Returns an infinite stream of random numbers between 1 (inclusive) and 7 (exclusive)
        IntStream dice = new Random().ints(1, 7);

        // There are more methods in the Java standard library that create streams, for example BufferedReader.lines()
        try (BufferedReader in = new BufferedReader(new FileReader("README.md", StandardCharsets.UTF_8))) {
            in.lines().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
