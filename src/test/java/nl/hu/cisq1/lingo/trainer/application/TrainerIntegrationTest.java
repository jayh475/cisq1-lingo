package nl.hu.cisq1.lingo.trainer.application;


import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.LingoGame;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import javax.transaction.Transactional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
@Transactional
@Import(CiTestConfiguration.class)
public class TrainerIntegrationTest {
    @Autowired
    private LingoGameService lingoGameService;
    @Autowired
    private SpringGameRepository gameRepository;


    @Test
    @DisplayName("game is not empty ")
    void startGame() {
        String wordToGuess = "jarig";
        LingoGame lingoGame = new LingoGame(wordToGuess);
        gameRepository.save(lingoGame);
        assertNotNull(this.lingoGameService.startGame());
    }




}




