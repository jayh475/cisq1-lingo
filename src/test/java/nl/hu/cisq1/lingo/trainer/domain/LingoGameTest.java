package nl.hu.cisq1.lingo.trainer.domain;


import nl.hu.cisq1.lingo.trainer.domain.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LingoGameTest {

    //    slagende tests start new Round
    @Test
    @DisplayName("Start new round ")
    void newRound() {
        String wordToGuess = "WOORD";
        LingoGame lingoGame = new LingoGame();
        assertDoesNotThrow(() -> lingoGame.startNewRound(wordToGuess));
    }

    @Test
    @DisplayName("Round could be started, because last round was finished")
    void roundNotfinished() {
        String wordToGuess = "WOORD";
        LingoGame lingoGame = new LingoGame();
        lingoGame.getRoundList().add(new Round(wordToGuess));
        lingoGame.getRoundList().get(0).guess("WOORD");
        assertDoesNotThrow(() -> lingoGame.startNewRound(wordToGuess));
    }

//    falende test voor start new Round

    @Test
    @DisplayName("Exception is thrown because roundlist is not empty")
    void newRoundException() {
        String wordToGuess = "WOORD";
        LingoGame lingoGame = new LingoGame();
        lingoGame.getRoundList().add(new Round(wordToGuess));
        assertThrows(CustomException.class, () -> lingoGame.startNewRound(wordToGuess));
    }

    //    slagende tests voor guess
    @Test
    @DisplayName("Guess")
    void guess() {
        LingoGame lingoGame = new LingoGame();
        lingoGame.startNewRound("WOORD");
        lingoGame.guess("HOORD");
        assertEquals("HOORD", lingoGame.getRoundList().get(0).getFeedbackList().get(0).getAttempt());
    }

    //falende tests for Guess
    @Test
    @DisplayName("Can't guess because there is no round")
    void noRoundGuess() {

        LingoGame lingoGame = new LingoGame();
        assertThrows(CustomException.class, () -> lingoGame.guess("WOORD"));
    }

    //    slagende tests voor show progress
    @Test
    @DisplayName("Show progress")
    void showProgress() {
        LingoGame lingoGame = new LingoGame();
        lingoGame.startNewRound("WOORD");
        System.out.println(lingoGame.showProgress().toString());
    }

//    falende tests voor show progress
//    @Test
//    @DisplayName("progress can't be null")
//    void s


    //slagende tests voor get last round
    @Test
    @DisplayName("Get last round")
    void getLastRound() {
        LingoGame lingoGame = new LingoGame();
        lingoGame.getRoundList().add(new Round("WOORD"));
        assertEquals(lingoGame.getRoundList().get(0), lingoGame.getLastRound());
    }

    @Test
    @DisplayName(" returned round ook  als er meer dan 1 round in de lijst zit")
    void moreThanOneRound() {
        LingoGame lingoGame = new LingoGame();
        for (int i = 0; i < 2; i++) {
            lingoGame.startNewRound("WOORD");
            lingoGame.guess("WOORD");
        }
        assertEquals(lingoGame.getRoundList().get(1), lingoGame.getLastRound());


    }


}