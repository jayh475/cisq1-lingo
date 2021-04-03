package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.LingoGame;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TrainerServiceTest {

    @Test
    @DisplayName("Starting a new game and get the right progress")
    void startNewGame() {
        WordService wordService = mock(WordService.class);
        SpringGameRepository springGameRepository = mock(SpringGameRepository.class);
        LingoGameService lingoGameService = new LingoGameService(springGameRepository, wordService);
        when(wordService.provideRandomWord(anyInt())).thenReturn("peren");

        Progress progress = lingoGameService.startGame();

        assertEquals(new Progress(0, "p....", 1, GameStatus.PLAYING, new ArrayList<>(), null), progress);

    }

    @Test
    @DisplayName("start new round  is possible")
    void startNewRound() {
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(6)).thenReturn("appels");
        LingoGame game = new LingoGame("appel");
        game.guess("appel");

        SpringGameRepository gameRepository = mock(SpringGameRepository.class);
        when(gameRepository.findLingoGameById(anyInt())).thenReturn(game);

        LingoGameService lingoGameService = new LingoGameService(gameRepository, wordService);

        Progress progress = lingoGameService.startNewRound(0);

        assertEquals(progress.getRoundNumber(), 2);
        assertEquals(progress.getCurrentHint(), "a.....");
    }




}