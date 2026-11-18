package org.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestAutomatonTest {
    @ParameterizedTest
    @CsvSource({
        "'', S",
        "abc, S",
        "abcT, 1",
        "abcTE, 2",
        "abcTES, 3",
        "TEST, F",
        "abcTESTabc, F",
        "TESTTEST, F",
        "TESTTES, F",
        "Tx, S",
        "TEx, S",
        "TESx, S",
        "'TE ST', 1",
        "test, S",
        "абвTEST🙂, F",
        "TT, 1",
        "TET, 1",
        "TTEST, F",
        "TETEST, F"
    })
    void returnsFinalState(String text, String expected) {
        assertEquals(expected, TestAutomaton.recognize(text));
    }
}
