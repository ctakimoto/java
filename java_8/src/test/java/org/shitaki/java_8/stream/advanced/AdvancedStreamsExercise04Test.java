package org.shitaki.java_8.stream.advanced;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class AdvancedStreamsExercise04Test {

    private AdvancedStreamsExercise04 exercise = new AdvancedStreamsExercise04();

    @Test
    @DisplayName("Get food and non-food product names")
    void getFoodAndNonFoodProductNames() {
        Assertions.assertThat(exercise.getFoodAndNonFoodProductNames(TestData.getProducts()))
                .describedAs("Exercise 5: Your solution does not return the correct result.")
                .containsExactlyInAnyOrderEntriesOf(Map.of(
                        true, List.of("Apples", "Spaghetti"),
                        false, List.of("Pencils", "Plates", "Knives", "Forks", "Detergent")));
    }
}
