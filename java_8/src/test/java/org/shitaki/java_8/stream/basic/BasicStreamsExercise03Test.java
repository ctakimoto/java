package org.shitaki.java_8.stream.basic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class BasicStreamsExercise03Test {

    private BasicStreamsExercise03 exercise = new BasicStreamsExercise03();

    @Test
    @DisplayName("Find product cheaper than the price limit")
    void findProductCheaperThan() {
        Optional<Product> result = exercise.findProductCheaperThan(TestData.getProducts(), new BigDecimal("4.00"));
        Assertions.assertThat(result)
                .describedAs("Exercise 3: Your solution did not find any products cheaper than $ 4.00.")
                .isPresent();
        assertThat(result.get().getPrice())
                .describedAs("Exercise 3: Your solution returned a product that is not less than $ 4.00.")
                .isLessThan(new BigDecimal("4.00"));

        assertThat(exercise.findProductCheaperThan(TestData.getProducts(), new BigDecimal("1.00")))
                .describedAs("Exercise 3: There are no products cheaper than $ 1.00, expected an empty result.")
                .isNotPresent();
    }
}
