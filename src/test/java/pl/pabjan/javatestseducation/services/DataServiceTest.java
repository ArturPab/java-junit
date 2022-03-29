package pl.pabjan.javatestseducation.services;

import org.junit.jupiter.api.*;
import pl.pabjan.javatestseducation.model.Movie;
import pl.pabjan.javatestseducation.model.Ring;
import pl.pabjan.javatestseducation.model.TolkienCharacter;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static pl.pabjan.javatestseducation.model.Race.*;

class DataServiceTest {
    DataService dataService;

    @BeforeEach
    void setUp() {
        dataService = new DataService();
    }

    @Test
    void ensureThatInitializationOfTolkeinCharactorsWorks() {
        TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, HOBBIT);

        assertEquals(33, frodo.age, "check that age is 33");
        assertEquals("Frodo", frodo.getName(), "check that name is Frodo");
        assertNotEquals("Frodon", frodo.getName(), "check that name isn't Frodon");
    }

    @Test
    void ensureThatEqualsWorksForCharaters() {
        Object jake = new TolkienCharacter("Jake", 43, HOBBIT);
        Object sameJake = jake;
        Object jakeClone = new TolkienCharacter("Jake", 12, HOBBIT);

        assertEquals(jake, sameJake);
        assertNotEquals(jake, jakeClone);
    }

    @Test
    void checkInheritance() {
        TolkienCharacter tolkienCharacter = dataService.getFellowship().get(0);
        assertNotSame(tolkienCharacter.getClass(), Movie.class);
    }

    @Test
    void ensureFellowShipCharacterAccessByNameReturnsNullForUnknownCharacter() {
        assertThrows(NoSuchElementException.class, () -> dataService.getFellowshipCharacter("Lars"));
    }

    @Test
    void ensureFellowShipCharacterAccessByNameWorksGivenCorrectNameIsGiven() {
        // TODO imlement a check that dataService.getFellowshipCharacter returns a fellow for an
        // existing felllow, e.g. "Frodo"
        TolkienCharacter tolkienCharacter = dataService.getFellowshipCharacter("Frodo");
        assertNotNull(tolkienCharacter);
    }


    @Test
    void ensureThatFrodoAndGandalfArePartOfTheFellowship() {

        List<TolkienCharacter> fellowship = dataService.getFellowship();

        assertAll("check that Frodo and Gandalf are part of the fellowship",
                () -> assertNotNull(dataService.getFellowshipCharacter("Frodo")),
                () -> assertNotNull(dataService.getFellowshipCharacter("Gandalf"))
        );
    }

    @Test
    void ensureThatOneRingBearerIsPartOfTheFellowship() {
        List<TolkienCharacter> fellowship = dataService.getFellowship();
        Map<Ring, TolkienCharacter> ringBearers = dataService.getRingBearers();
        assertNotNull(fellowship.stream().filter(ringBearers::containsValue).findFirst());
    }

    @Tag("slow")
    @DisplayName("Minimal stress testing: run this test 1000 times to ")
    @RepeatedTest(1000)
    void ensureThatWeCanRetrieveFellowshipMultipleTimes() {
        dataService = new DataService();
        assertNotNull(dataService.getFellowship());
    }

    @Test
    void ensureOrdering() {
        List<TolkienCharacter> fellowship = dataService.getFellowship();
        assertAll("ensure that the order of the fellowship is: frodo, sam, merry,pippin, gandalf,legolas,gimli,aragorn,boromir",
                () -> assertEquals("Frodo", fellowship.get(0).getName()),
                () -> assertEquals("Sam", fellowship.get(1).getName()),
                () -> assertEquals("Merry", fellowship.get(2).getName()),
                () -> assertEquals("Pippin", fellowship.get(3).getName()),
                () -> assertEquals("Gandalf", fellowship.get(4).getName()),
                () -> assertEquals("Legolas", fellowship.get(5).getName()),
                () -> assertEquals("Gimli", fellowship.get(6).getName()),
                () -> assertEquals("Aragorn", fellowship.get(7).getName()),
                () -> assertEquals("Boromir", fellowship.get(8).getName())
        );
    }

    @Test
    void ensureAge() {
        List<TolkienCharacter> fellowship = dataService.getFellowship();

        List<TolkienCharacter> hobbitsAndMen = fellowship.stream().filter(tolkienCharacter -> tolkienCharacter.getRace() == HOBBIT || tolkienCharacter.getRace() == MAN).collect(Collectors.toList());

        hobbitsAndMen.forEach(character -> assertTrue(character.age < 100));

        List<TolkienCharacter> elfsAndDwarfsAndMaia = fellowship.stream().filter(tolkienCharacter -> tolkienCharacter.getRace() == ELF || tolkienCharacter.getRace() == DWARF || tolkienCharacter.getRace() == MAIA).collect(Collectors.toList());

        elfsAndDwarfsAndMaia.forEach(character -> assertTrue(character.age>100));
    }

    @Test
    void ensureThatFellowsStayASmallGroup() {

        List<TolkienCharacter> fellowship = dataService.getFellowship();
        assertThrows(IndexOutOfBoundsException.class, () -> dataService.getFellowship().get(20));
    }

    @Test
    void ensureThatTestDoesNotRunLongerThanThreeSeconds() {
        assertTimeout(Duration.ofSeconds(3), () -> dataService.update());
    }
}