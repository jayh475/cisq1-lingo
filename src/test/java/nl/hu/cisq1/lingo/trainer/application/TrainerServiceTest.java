package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.security.application.UserService;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.LingoGame;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TrainerServiceTest {
    private SpringGameRepository springGameRepository;
    private LingoGameService lingoGameService;
    private String username;

    @BeforeEach
    public void setUp() {
        username = "Jayh475";
        WordService wordService = mock(WordService.class);
        springGameRepository = mock(SpringGameRepository.class);

        lingoGameService = new LingoGameService(springGameRepository, wordService);
        when(wordService.provideRandomWord(6)).thenReturn("appels");
        when(wordService.provideRandomWord(5)).thenReturn("peren");

    }

    @AfterEach
    void tearDown() {
        System.out.println("teardown");

    }

    @Test
    @DisplayName("Starting a new game and get the right progress")
    void startNewGame() {
        Progress progress = lingoGameService.startGame(username);
        assertEquals(new Progress(0, "p....", 1, GameStatus.PLAYING, new ArrayList<>(), null), progress);
    }

    @Test
    @DisplayName("start new round  is possible after finishing first round")
    void startNewRound() {
        LingoGame game = new LingoGame("appel",username);
        game.guess("appel");
        when(springGameRepository.findLingoGameById(anyInt())).thenReturn(java.util.Optional.of(game));
        Progress progress = lingoGameService.startNewRound(0,username);
        assertEquals(2,progress.getRoundNumber());
        assertEquals("a.....",progress.getCurrentHint());
    }




}