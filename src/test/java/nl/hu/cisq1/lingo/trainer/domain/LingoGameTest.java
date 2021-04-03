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
    @DisplayName("Show progress without attempt")
    void showProgress() {
        LingoGame lingoGame = new LingoGame("WOORD");
        List<Feedback> feedbackList = new ArrayList<>();
        Progress progress = new Progress(0, "W....", 1, GameStatus.PLAYING, feedbackList, null);
        assertEquals(lingoGame.showProgress(), progress);
    }








    @Test
    @DisplayName("provide length after highest length has been reached")
    void firstWordLength(){
        LingoGame lingoGame = new LingoGame("WOORDEN");

        assertEquals(5,lingoGame.provideNextWordLength());

    }

//nextword geslaagd
    @Test
    @DisplayName("provide next word length when round finishes")
    void nextWordLength(){
        LingoGame lingoGame = new LingoGame("WOORD");
        lingoGame.guess("WOORD");
        lingoGame.startNewRound("douch");
        assertEquals(6,lingoGame.provideNextWordLength());

    }

//    score
    @Test
    @DisplayName("score after right guess first attempt")
    void rightScore(){
        LingoGame lingoGame = new LingoGame("WOORD");
        lingoGame.guess("WOORD");
        assertEquals(25,lingoGame.getScore());
    }






}