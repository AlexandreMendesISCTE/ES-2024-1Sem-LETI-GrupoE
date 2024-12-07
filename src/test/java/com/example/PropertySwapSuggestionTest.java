package com.example;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;

public class PropertySwapSuggestionTest {

    @Test
    public void testExercise_6() {
        // Test if the Exercise_6 method runs without throwing any exceptions
        assertDoesNotThrow(() -> {
            PropertySwapSuggestion.Exercise_6("Arco da Calheta");
        });
    }

    @Test
    public void testExercise_6WithNullFreguesia() {
        // Test if the Exercise_6 method runs without throwing any exceptions when freguesia is null
        assertDoesNotThrow(() -> {
            PropertySwapSuggestion.Exercise_6(null);
        });
    }

    @Test
    public void testMain() {
        // Test if the main method runs without throwing any exceptions
        assertDoesNotThrow(() -> {
            PropertySwapSuggestion.main(new String[]{});
        });
    }
}