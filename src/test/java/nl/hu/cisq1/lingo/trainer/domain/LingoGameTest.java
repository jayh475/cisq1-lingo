package nl.hu.cisq1.lingo.trainer.domain;


import nl.hu.cisq1.lingo.trainer.domain.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LingoGameTest {


    @Test
    @DisplayName("start new game and get first hint without attempt")
    void newLingoGame() {
        String wordToGuess = "WOORD";
        LingoGame lingoGame = new LingoGame(wordToGuess);
        assertEquals("W....", lingoGame.giveHint());

    }

    //    slagende tests start new Round

    @Test
    @DisplayName("Round could  be started, because last round was finished and status was waiting for round")
    void roundNotfinished() {
        String wordToGuess = "WOORD";
        LingoGame lingoGame = new LingoGame(wordToGuess);
        lingoGame.guess(wordToGuess);
        assertDoesNotThrow(() -> lingoGame.startNewRound(wordToGuess));
    }

    //    falende test voor start new Round
    @Test
    @DisplayName("Exception is thrown because round was not finished")
    void newRoundException() {
        String wordToGuess = "WOORD";
        LingoGame lingoGame = new LingoGame(wordToGuess);
        assertThrows(CustomException.class, () -> lingoGame.startNewRound(wordToGuess));
    }

    //    slagende tests voor guess
    @Test
    @DisplayName("the guess was asserted in the in the round ")
    void guessInRound() {
        LingoGame lingoGame = new LingoGame("WOORD");
        lingoGame.guess("HOORD");
        assertEquals("HOORD", lingoGame.getRoundList().get(0).getFeedbackList().get(0).getAttempt());
    }

    @Test
    @DisplayName("give hint when guess")
    void giveHint() {

    }


    //falende tests for Guess
    @Test
    @DisplayName("Can't guess because there is no round")
    void noRoundGuess() {
        LingoGame lingoGame = new LingoGame();
        assertThrows(CustomException.class, () -> lingoGame.guess("WOORD"));
    }

    @Test
    @DisplayName("Can't guess because feedback list contains 5 attempts")
    void exceededAttempts() {
        LingoGame lingoGame = new LingoGame("WOORD");
        lingoGame.guess("Jarig");
        lingoGame.guess("waard");
        lingoGame.guess("weert");
        lingoGame.guess("appen");
        lingoGame.guess("atten");
        assertThrows(CustomException.class, () -> lingoGame.guess("atten"));
    }


//        slagende tests voor show progress
    @Test
    @DisplayName("Show progress")
    void showProgress() {
        LingoGame lingoGame = new LingoGame("WOORD");
        List<Feedback> feedbackList = List.of();
        Progress progress = new Progress(GameStatus.PLAYING,0, "W....", 1, ArrayList<Feedback>);
        assertEquals(lingoGame.showProgress(), progress);
    }

    @Test
    @DisplayName("Show progress after a guess")
    void progressAfterGuess() {
        LingoGame lingoGame = new LingoGame("WOORD");

        lingoGame.guess("WOERD");
        Progress progress = new Progress(0, "WO.RD", 1);
        assertEquals(lingoGame.showProgress(), progress);
    }


//    falende tests voor show progress


}