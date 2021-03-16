package nl.hu.cisq1.lingo.trainer.domain;


import nl.hu.cisq1.lingo.trainer.domain.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;


import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {


    @MethodSource("provideMarkExamples")
    @DisplayName("give right marks")
    @ParameterizedTest
    void giveMarks(String attempt, String wordToGuess, List<Mark> marks) {
        Round round = new Round(wordToGuess);

        assertEquals(marks, round.generateMarks(attempt));

    }


    static Stream<Arguments> provideMarkExamples() {

        return Stream.of(
                Arguments.of("WAADT", "WOORD", List.of(CORRECT, ABSENT, ABSENT, PRESENT, ABSENT)),
                Arguments.of("WEERD", "WOORD", List.of(CORRECT, ABSENT, ABSENT, CORRECT, CORRECT)),
                Arguments.of("PATAT", "PAARD", List.of(CORRECT, CORRECT, ABSENT, PRESENT, ABSENT)),
                Arguments.of("ATTAA","PAARD",List.of(PRESENT,ABSENT,ABSENT,PRESENT,ABSENT)),
                Arguments.of("AARAT","WATER",List.of(ABSENT,CORRECT,PRESENT,ABSENT,PRESENT)), //eerste letter moet niet als present gemarkeerd worden.
                Arguments.of("AAATT","PAARD",List.of(ABSENT,CORRECT,CORRECT,ABSENT,ABSENT))



        );
    }


    @Test
    @DisplayName("geeft  invalid aantal keer word ToGuees  als lengte van wordToGuess niet hetzelfde is als attempt ")
    void WordTOGuessIsNotAttemptLength() {
        Round round = new Round("WOORD");
        assertThrows(CustomException.class, () -> round.generateMarks("JA"));

    }

    @Test
    @DisplayName("Woord is goed en er wordt geen exception gegooid ")
    void WordTOGuessIsAttemptLength() {
        Round round = new Round("WOORD");
        assertDoesNotThrow(() -> round.generateMarks("WOORD"));

    }


    @Test
    @DisplayName(" return true if round finishes at five attempts ")
    void roundfinished() {
        Feedback feedback = new Feedback("WAADT", List.of(CORRECT, ABSENT, ABSENT, PRESENT, ABSENT));
        Round round = new Round("WOORD");
        for (int i = 0; i < 5; i++) {
            round.getFeedbackList().add(feedback);
        }
        assertTrue(round.checkIfRoundFinished());
    }

    @Test
    @DisplayName("add feedback less than 5 attempts")
    void guess() {
        Round round = new Round("WOORD");
        round.guess("WAARD");
        assertFalse(round.getFeedbackList().isEmpty());

    }

    @Test
    @DisplayName("exception is thrown because attempts 5 or more")
    void exceedsFiveAttempts() {
        Round round = new Round("WOORD");

        assertThrows(CustomException.class, () ->
        {
            for (int i = 0; i <= 5; i++) {
                round.guess("WAARD");
            }
        });
    }

    @Test
    @DisplayName("checks if initializingFirstHint gives a letter ")
    void InitializingFirstHint() {
        Round round = new Round("WOORD");
        assertEquals("W...." , round.initializeFirstHint());


    }


    @Test
    @DisplayName("checks the current length of the word")
    void currentWordLength() {
        Round round = new Round("WOORD");

        assertEquals(5, round.getCurrentWorthLength());
    }


    @Test
    @DisplayName("Give hints")
    void giveHints() {
        Round round = new Round("WOORD");
        round.guess("WAARD");
        assertEquals("W..RD", round.giveHint());
    }

    @Test
    @DisplayName("check if first letter is given With the function giveHint")
    void giveFirstLetter() {
        Round round = new Round("WOORD");
        assertEquals("W....", round.giveHint());
    }

    @Test
    @DisplayName(" check if feedbacklist is empty when giving initializing first letter ")
    void CheckIfFeedbackListEmpty() {
        Round round = new Round("WOORD");
        assertTrue(round.getFeedbackList().isEmpty());
    }


}