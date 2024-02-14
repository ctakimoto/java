package org.shitaki.java_8.functionalinterface;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.shitaki.java_8.functionalinterface.Category.*;
import static org.shitaki.java_8.functionalinterface.TestData.*;

class FunctionalInterfacesExercise02Test {

    private FunctionalInterfacesExercise02 exercise = new FunctionalInterfacesExercise02();

    @Test
    @DisplayName("Search by category")
    void searchByCategory() {
        Predicate<Product> predicate = exercise.createSearchPredicate(new FunctionalInterfacesExercise02.SearchCriteria(FOOD, null, null, null));
        assertThat(TestData.getProducts().stream().filter(predicate).collect(Collectors.toList()))
                .describedAs("Exercise 2: When searching for FOOD products, only those should be returned.")
                .containsExactly(APPLES, SPAGHETTI, COFFEE);
    }

    @Test
    @DisplayName("Search by name")
    void searchByName() {
        Predicate<Product> predicate = exercise.createSearchPredicate(new FunctionalInterfacesExercise02.SearchCriteria(null, ".*t.*", null, null));
        assertThat(TestData.getProducts().stream().filter(predicate).collect(Collectors.toList()))
                .describedAs("Exercise 2: When searching for products with a 't' in the name, only those should be returned.")
                .containsExactly(PLATES, SPAGHETTI, DETERGENT);
    }

    @Test
    @DisplayName("Search by category and name")
    void searchByCategoryAndName() {
        Predicate<Product> predicate = exercise.createSearchPredicate(new FunctionalInterfacesExercise02.SearchCriteria(FOOD, ".*t.*", null, null));
        assertThat(TestData.getProducts().stream().filter(predicate).collect(Collectors.toList()))
                .describedAs("Exercise 2: When searching for FOOD products with a 't' in the name, only those should be returned.")
                .containsExactly(SPAGHETTI);
    }

    @Test
    @DisplayName("Search by minimum price")
    void searchByMinimumPrice() {
        Predicate<Product> predicate = exercise.createSearchPredicate(new FunctionalInterfacesExercise02.SearchCriteria(null, null, new BigDecimal("3.79"), null));
        assertThat(TestData.getProducts().stream().filter(predicate).collect(Collectors.toList()))
                .describedAs("Exercise 2: When searching for products costing $ 3.79 or more, only those should be returned.")
                .containsExactly(PENCILS, PLATES, COFFEE, DETERGENT);
    }

    @Test
    @DisplayName("Search by maximum price")
    void searchByMaximumPrice() {
        Predicate<Product> predicate = exercise.createSearchPredicate(new FunctionalInterfacesExercise02.SearchCriteria(null, null, null, new BigDecimal("5.79")));
        assertThat(TestData.getProducts().stream().filter(predicate).collect(Collectors.toList()))
                .describedAs("Exercise 2: When searching for products costing $ 5.79 or less, only those should be returned.")
                .containsExactly(PENCILS, APPLES, SPAGHETTI, DETERGENT);
    }

    @Test
    @DisplayName("Search by price between minimum and maximum")
    void searchByPriceBetween() {
        Predicate<Product> predicate = exercise.createSearchPredicate(new FunctionalInterfacesExercise02.SearchCriteria(null, null, new BigDecimal("3.00"), new BigDecimal("8.00")));
        assertThat(TestData.getProducts().stream().filter(predicate).collect(Collectors.toList()))
                .describedAs("Exercise 2: When searching for products costing between $ 3.00 and $ 8.00, only those should be returned.")
                .containsExactly(PENCILS, COFFEE, DETERGENT);
    }

    @Test
    @DisplayName("Search by category, name and price between")
    void searchByAllCriteria() {
        Predicate<Product> predicate = exercise.createSearchPredicate(new FunctionalInterfacesExercise02.SearchCriteria(OFFICE, ".*n.*", new BigDecimal("3.00"), new BigDecimal("8.00")));
        assertThat(TestData.getProducts().stream().filter(predicate).collect(Collectors.toList()))
                .describedAs("Exercise 2: When searching for a combination of criteria, only the expected result should be returned.")
                .containsExactly(PENCILS);
    }

    @Test
    @DisplayName("Search expecting an empty result")
    void emptyResult() {
        Predicate<Product> predicate = exercise.createSearchPredicate(new FunctionalInterfacesExercise02.SearchCriteria(OFFICE, "Apples", null, null));
        assertThat(TestData.getProducts().stream().filter(predicate).collect(Collectors.toList()))
                .describedAs("Exercise 2: When searching for an OFFICE product named Apples, an empty result is expected.")
                .isEmpty();
    }

    @Test
    @DisplayName("Empty search criteria")
    void emptySearchCriteria() {
        Predicate<Product> predicate = exercise.createSearchPredicate(new FunctionalInterfacesExercise02.SearchCriteria(null, null, null, null));
        assertThat(TestData.getProducts().stream().filter(predicate).collect(Collectors.toList()))
                .describedAs("Exercise 2: When all search criteria are empty, the predicate should accept all products.")
                .containsExactly(PENCILS, APPLES, PLATES, SPAGHETTI, COFFEE, DETERGENT);
    }
}
