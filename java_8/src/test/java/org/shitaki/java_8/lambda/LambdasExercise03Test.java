/*
 * This code is part of the course "Working with Streams and Lambda Expressions in Java (Java SE 11 Developer Certification 1Z0-819)" for Pluralsight.
 *
 * Copyright (C) 2021 by Jesper de Jong (jesper@jdj-it.com).
 */
package org.shitaki.java_8.lambda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.shitaki.java_8.lambda.Category.*;

class LambdasExercise03Test {

    private static final List<Product> TEST_PRODUCTS = Arrays.asList(
            new Product(OFFICE, "Pencils", new BigDecimal("5.79")),
            new Product(FOOD, "Apples", new BigDecimal("1.29")),
            new Product(UTENSILS, "Plates", new BigDecimal("12.95")),
            new Product(FOOD, "Spaghetti", new BigDecimal("2.79")),
            new Product(FOOD, "Coffee", new BigDecimal("7.49")),
            new Product(CLEANING, "Detergent", new BigDecimal("3.79")));

    private LambdasExercise03 exercise = new LambdasExercise03();

    @Test
    @DisplayName("Exercise 3: Use a shopping cart")
    void useShoppingCart() {
        LambdasExercise03.ShoppingCartFactory factory = exercise.getShoppingCartFactory();

        assertThat(factory)
                .describedAs("Exercise 3: Your getShoppingCartFactory() does not return a shopping cart factory.")
                .isInstanceOf(LambdasExercise03.ShoppingCartFactory.class);

        LambdasExercise03.ShoppingCart shoppingCart = factory.newShoppingCart();

        assertThat(shoppingCart)
                .describedAs("Exercise 3: Your ShoppingCartFactory does not create a shopping cart.")
                .isInstanceOf(LambdasExercise03.ShoppingCart.class);

        for (Product product : TEST_PRODUCTS) {
            shoppingCart.add(product);
        }

        assertThat(shoppingCart.getTotalAmount())
                .describedAs("Exercise 3: Your shopping cart does not return the correct total amount.")
                .isEqualTo(new BigDecimal("34.10"));
    }
}
