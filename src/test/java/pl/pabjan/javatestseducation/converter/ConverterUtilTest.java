package pl.pabjan.javatestseducation.converter;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConverterUtilTest {

    int[][] celsiusFahrenheitMapping = new int[][] { { 10, 50 }, { 40, 104 }, { 0, 32 } };
    static int[][] celsiusFahrenheitMapping() {
        return new int[][] { { 10, 50 }, { 40, 104 }, { 0, 32 } };
    }

    @TestFactory
    Stream<DynamicTest> ensureThatCelsiumConvertsToFahrenheit() {
        return Arrays.stream(celsiusFahrenheitMapping).map(entry -> {
            // access celcius and fahrenheit from entry
            int celsius = entry[0];
            int fahrenheit = entry[1];
            return DynamicTest.dynamicTest(celsius + "C = " + fahrenheit + "F", () -> {
                assertEquals(fahrenheit, ConverterUtil.convertCelsiusToFahrenheit(celsius));
            });
        });

    }

    @TestFactory
    Stream<DynamicTest> ensureThatFahrenheitToCelsiumConverts() {
        return Arrays.stream(celsiusFahrenheitMapping).map(entry -> {
            int celsius = entry[0];
            int fahrenheit = entry[1];
            return DynamicTest.dynamicTest(fahrenheit + "F" + " = " + celsius + "C", () -> {
                assertEquals(celsius, ConverterUtil.convertFahrenheitToCelsius(fahrenheit));
            });
        });
    }

    @ParameterizedTest(name = "{index} called with: {0}")
    @MethodSource(value = "celsiusFahrenheitMapping")
    void testWithParameter(int[] data) {
        int celsius = data[0];
        int fahrenheit = data[1];
        assertEquals(celsius, ConverterUtil.convertFahrenheitToCelsius(fahrenheit));
    }


}