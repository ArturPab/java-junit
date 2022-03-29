package pl.pabjan.javatestseducation.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.pabjan.javatestseducation.model.TolkienCharacter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.pabjan.javatestseducation.model.Race.HOBBIT;

public class DataModelAssertThrowsTest {

    @Test
    @DisplayName("Ensure that access to the fellowship throws exception outside the valid range")
    void exceptionTesting() {
        DataService dataService = new DataService();
        Throwable exception = assertThrows(IndexOutOfBoundsException.class, () -> dataService.getFellowship().get(20));
        assertEquals("Index 20 out of bounds for length 9", exception.getMessage());
    }

    @Test
    public void ensureThatAgeMustBeLargerThanZeroViaSetter() {
        TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, HOBBIT);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> frodo.setAge(-1));
        assertEquals("Age is not allowed to be smaller than zero", exception.getMessage());

    }

    @Test
    public void testThatAgeMustBeLargerThanZeroViaConstructor() {
        TolkienCharacter frodo = new TolkienCharacter("Frodo", -1, HOBBIT);
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->  frodo.setAge(frodo.age));
        assertEquals("Age is not allowed to be smaller than zero", exception.getMessage());
    }

}