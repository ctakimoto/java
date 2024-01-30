package org.shitaki.java_14.record;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PassengerTest {
    @Test
    public void records_same_attribute_are_equals() {
        // given
        Passenger passenger1 = new Passenger("Celso", 2);
        Passenger passenger2 = new Passenger("Celso", 2);

        // then
        Assertions.assertEquals(passenger1, passenger2);
    }
}
