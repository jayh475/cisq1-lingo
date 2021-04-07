package nl.hu.cisq1.lingo.trainer.domain;


import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

import nl.hu.cisq1.lingo.trainer.domain.exception.CustomException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;


class FeedbackTest {


    @Test
    @DisplayName("word is guessed if all letters are correct")
    void wordIsGuessed() {
//        p: arrange
//        q: act
        String attempt = "WOORD";
        List<Mark> marks = List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT);
        Feedback feedback = new Feedback(attempt, marks);

//        R: assert
        assertTrue(feedback.isWordGuessed());

    }


    @Test
    @DisplayName("word is not guessed if not all letters are correct")
    void wordIsNotGuessed() {
//        p: arrange
//        q: act
        String attempt = "WOORD";
        List<Mark> marks = List.of(CORRECT, CORRECT, CORRECT, CORRECT, ABSENT);
        Feedback feedback = new Feedback(attempt, marks);

//        R: assert
        assertFalse(feedback.isWordGuessed());

    }


    @MethodSource("provideHintExamples")
    @DisplayName("provide hint examples")
    @ParameterizedTest
    void test(String attempt, String previousHint, String wordToGuess, List<Mark> marks, String hint) {
        Feedback feedback = new Feedback(attempt, marks);
        assertEquals(hint, feedback.giveHint(previousHint, wordToGuess));

    }

    static Stream<Arguments> provideHintExamples() {
//        q: act
        String woord = "WOORD";

//        R: assert
        return Stream.of(
                Arguments.of("WOONT", "W....", woord, List.of(CORRECT, CORRECT, CORRECT, ABSENT, ABSENT), "WOO.."),
                Arguments.of("WOERD", "WO...", woord, List.of(CORRECT, ABSENT, ABSENT, CORRECT, CORRECT), "WO.RD"),
                Arguments.of("WOORD", "WO.RD", woord, List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT), "WOORD")


        );

    }

    @MethodSource("provideMarkExamples")
    @DisplayName("give right marks")
    @ParameterizedTest
    void giveMarks(String attempt, String wordToGuess, List<Mark> marks) {
        Feedback feedback = new Feedback(attempt, marks);

        assertEquals(Feedback.of(attempt, wordToGuess).getMarks(), feedback.getMarks());

    }

    static Stream<Arguments> provideMarkExamples() {

        return Stream.of(
                Arguments.of("WAADT", "WOORD", List.of(CORRECT, ABSENT, ABSENT, PRESENT, ABSENT)),
                Arguments.of("WEERD", "WOORD", List.of(CORRECT, ABSENT, ABSENT, CORRECT, CORRECT)),
                Arguments.of("PATAT", "PAARD", List.of(CORRECT, CORRECT, ABSENT, PRESENT, ABSENT)),
                Arguments.of("ATTAA", "PAARD", List.of(PRESENT, ABSENT, ABSENT, PRESENT, ABSENT)),
                Arguments.of("AARAT", "WATER", List.of(ABSENT, CORRECT, PRESENT, ABSENT, PRESENT)),
                Arguments.of("AAATT", "PAARD", List.of(ABSENT, CORRECT, CORRECT, ABSENT, ABSENT))

        );
    }


    @Test
    @DisplayName("throw exception if length marks is not equal to length attempt")
    void exceptionTest() {
        List<Mark> marks = List.of(ABSENT, ABSENT);
        assertThrows(CustomException.class, () -> new Feedback("WOORD", marks));
    }

    @Test
    @DisplayName("throw exception if attempt is not equal to wordToGuess")
    void wordToGuessNotLengthAttempt(){
        String attempt = "WOORD";
        String wordToGuess = "langer";
     assertThrows(CustomException.class, () -> Feedback.of(attempt,wordToGuess));
    }


}