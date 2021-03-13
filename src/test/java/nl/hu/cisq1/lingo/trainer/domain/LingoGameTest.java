package nl.hu.cisq1.lingo.trainer.domain;


import nl.hu.cisq1.lingo.trainer.domain.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LingoGameTest {

    @Test
    @DisplayName("Start new round")
    void newRound() {
        String wordToGuess = "WOORD";
        LingoGame lingoGame = new LingoGame();
        assertDoesNotThrow(() -> lingoGame.startNewRound(wordToGuess));
    }

    @Test
    @DisplayName("Exception is thrown because roundlist is not empty")
    void newRoundException() {
        String wordToGuess = "WOORD";
        LingoGame lingoGame = new LingoGame();
        lingoGame.getRoundList().add(new Round(wordToGuess));
        assertThrows(CustomException.class, () -> lingoGame.startNewRound(wordToGuess));
    }

    //TODO mockito gebruiken
//    @Test
//    @DisplayName("Exception is thrown because round is not finished"){
//        String wordToGuess = "WOORD";
//        LingoGame lingoGame = new LingoGame();
//        assertThrows(CustomException.class, () -> lingoGame.startNewRound(wordToGuess));
//
//
//    }

//    @Test
//    @DisplayName("geeft raadpoging door aan huidige spelronde")
//     void guess(){
//
//        assertEquals();
//
//    }



}