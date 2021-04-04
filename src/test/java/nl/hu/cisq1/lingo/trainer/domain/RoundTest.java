package nl.hu.cisq1.lingo.trainer.domain;


import nl.hu.cisq1.lingo.trainer.domain.exception.CustomException;
import org.junit.jupiter.api.*;


import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;


import java.util.List;

class RoundTest {


    @Test
    @DisplayName(" return true if round finishes at five attempts ")
    void roundfinished() {
        Feedback feedback = new Feedback("WAADT", List.of(CORRECT, ABSENT, ABSENT, PRESENT, ABSENT));
        Round round = new Round("WOORD");
        for (int i = 0; i < 5; i++) {
            round.getFeedbackList().add(feedback);
        }
        assertTrue(round.lostGame());
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
        assertEquals("W....", round.initializeFirstHint());


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
    @DisplayName("feedbacklist not empty and it's not the first feedback check")
    void secFeedbackHint(){
        Round round = new Round("WOORD");
        round.guess("WAARD");
        round.guess("WOERD");
        assertEquals("WO.RD",round.giveHint());
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