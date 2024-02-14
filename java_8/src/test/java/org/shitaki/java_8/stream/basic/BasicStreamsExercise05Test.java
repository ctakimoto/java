package org.shitaki.java_8.stream.basic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.shitaki.java_8.stream.basic.TestData.*;

class BasicStreamsExercise05Test {

    private static final List<Product> TEST_PRODUCTS = List.of(PENCILS, APPLES, PLATES, SPAGHETTI, NOTEBOOK, BALLPOINT_PENS, DETERGENT);

    private BasicStreamsExercise05 exercise = new BasicStreamsExercise05();

    @Test
    @DisplayName("Are all office products cheap?")
    void areAllOfficeProductsCheap() {
        assertThat(exercise.areAllOfficeProductsCheap(new ArrayList<>(TEST_PRODUCTS), new BigDecimal("8.00")))
                .describedAs("Exercise 5: Your solution does not return the correct result, all office products are less than $ 8.00.")
                .isTrue();

        assertThat(exercise.areAllOfficeProductsCheap(new ArrayList<>(TEST_PRODUCTS), new BigDecimal("6.00")))
                .describedAs("Exercise 5: Your solution does not return the correct result, not all office products are less than $ 6.00.")
                .isFalse();
    }
}
