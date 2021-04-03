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


//schrijf met@SpringBootTest een service integration test voor ten minste de volgende use case van onze applicatie:het raden van woorden.In het echt zou je van elke use case ten minste de happy path willen dekken.
//        Let op dat je ook hier weer gebruik kunt maken van@ParameterizedTest.Deze tests testen onafhankelijk van de presentatie-infrastructuur,zoals HTTP.
//        Om in onze continuous integration pipeline een in-memory database te gebruiken kan je@Import(CiTestConfiguration.class) toevoegen boven de testklasse.
//        Er worden voor het testen in de continuous integration pipeline standaard drie woorden ingeladen in de woordendatabase(zie:WordTestDataFixtures).
//        Om zelf testdata(fixtures)te importeren kan je een nieuwe commandline runner toevoegen of kan je gebruik maken van een@BeforeAll lifecycle method.


@SpringBootTest
@Transactional
@Import(CiTestConfiguration.class)
public class TrainerServiceIntegrationTest {
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

    @Test
    @DisplayName("Guessing a word")
    void GuessingWord(){

    }







}




