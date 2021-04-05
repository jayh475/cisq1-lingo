package nl.hu.cisq1.lingo.trainer.application;


import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.LingoGame;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@Transactional
@Import(CiTestConfiguration.class)
public class TrainerServiceIntegrationTest {
    private int id;
    @Autowired
    private LingoGameService lingoGameService;
    @Autowired
    private SpringGameRepository gameRepository;

    private String username ;



    @BeforeEach
    public void setUp() {
        username = "jayh475";
        Progress progress = lingoGameService.startGame(username);
         id = progress.getGameId();

    }


    @Test
    @DisplayName("Guessing a word not correct")
    void GuessingWord(){
       Progress progress = lingoGameService.guess(id,"WOORD",username);
        assertEquals(GameStatus.PLAYING,progress.getGameStatus());
        assertEquals("P....",progress.getCurrentHint());
    }


    @Test
    @DisplayName("Guessing a word correct")
    void GuessingWordCorrectly(){
        Progress progress = lingoGameService.guess(id,"PIZZA",username);
        assertEquals(GameStatus.WAITING_FOR_ROUND,progress.getGameStatus());
        assertEquals("PIZZA",progress.getCurrentHint());
    }







}




