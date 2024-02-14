package org.shitaki.java_8.stream.basic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.shitaki.java_8.stream.basic.TestData.*;

class BasicStreamsExercise04Test {

    private static final List<Product> TEST_PRODUCTS_1 = List.of(PENCILS, DISH_BRUSH, PLATES, SCOURER, SPAGHETTI, FORKS, DETERGENT);
    private static final List<Product> TEST_PRODUCTS_2 = List.of(PENCILS, PLATES, SPAGHETTI, FORKS);

    private BasicStreamsExercise04 exercise = new BasicStreamsExercise04();

    @Test
    @DisplayName("Find cheapest cleaning product")
    void findCheapestCleaning() {
        assertThat(exercise.findCheapestCleaning(new ArrayList<>(TEST_PRODUCTS_1)))
                .describedAs("Exercise 4: Your solution did not find the cheapest cleaning product.")
                .contains(SCOURER);

        assertThat(exercise.findCheapestCleaning(new ArrayList<>(TEST_PRODUCTS_2)))
                .describedAs("Exercise 4: Your solution found something, but an empty result was expected.")
                .isNotPresent();
    }
}
