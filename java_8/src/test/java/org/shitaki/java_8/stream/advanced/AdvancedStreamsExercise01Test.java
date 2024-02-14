package org.shitaki.java_8.stream.advanced;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AdvancedStreamsExercise01Test {

    private AdvancedStreamsExercise01 exercise = new AdvancedStreamsExercise01();

    @Test
    @DisplayName("Get sorted product names")
    void getSortedProductNames() {
        assertThat(exercise.getSortedProductNames(TestData.getProducts()))
                .describedAs("Exercise 2: Your solution does not return the correct result.")
                .containsExactly("Apples", "Detergent", "Forks", "Knives", "Pencils", "Plates", "Spaghetti");
    }
}
