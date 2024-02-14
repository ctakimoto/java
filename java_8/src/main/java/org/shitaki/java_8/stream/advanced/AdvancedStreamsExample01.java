/*
 * This code is part of the course "Working with Streams and Lambda Expressions in Java (Java SE 11 Developer Certification 1Z0-819)" for Pluralsight.
 *
 * Copyright (C) 2021 by Jesper de Jong (jesper@jdj-it.com).
 */
package org.shitaki.java_8.stream.advanced;

import java.math.BigInteger;
import java.util.UUID;
import java.util.stream.Stream;

public class AdvancedStreamsExample01 {

    public static void main(String[] args) {
        // generate() takes a Supplier and creates an infinite stream from values returned by the supplier
        Stream<UUID> uuids = Stream.generate(UUID::randomUUID);
        uuids.limit(10).forEach(System.out::println);

        // The first version of iterate() takes a seed value and an unary operator and generates an infinite stream of values
        // by first returning the seed, and then applying the operator to each previous element, so the stream
        // will contain: seed, f(seed), f(f(seed)), etc.
        Stream<BigInteger> powersOfTwo = Stream.iterate(BigInteger.ONE, n -> n.multiply(BigInteger.TWO));
        powersOfTwo.limit(20).forEach(System.out::println);

        // The second version of iterate() takes an extra parameter, which is a predicate that indicates if there is a next element
        // When the predicate returns false, the stream ends
        // The three parameters are just like the three parts of a for-loop
        Stream<Character> alphabet = Stream.iterate('A', letter -> letter <= 'Z', letter -> (char) (letter + 1));
        alphabet.forEach(System.out::print);

        // You can construct a stream by creating a builder and adding elements to the builder
        Stream.Builder<String> builder = Stream.builder();
        builder.add("one");
        builder.add("two");
        builder.add("three");
        Stream<String> stream = builder.build();
        stream.forEach(System.out::println);
    }
}
