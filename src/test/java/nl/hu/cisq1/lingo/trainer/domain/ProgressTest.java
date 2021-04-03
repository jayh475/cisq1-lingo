package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class ProgressTest {

    Progress progress = new Progress(0, "W....", 1, GameStatus.PLAYING, new ArrayList<>(), 1);

    @Test
    @DisplayName("get gamestatus")
    void getGameStatus() {
        assertEquals(GameStatus.PLAYING, progress.getGameStatus());
    }

    @Test
    @DisplayName("score")
    void getScore() {
        assertEquals(0, progress.getScore());
    }

    @Test
    @DisplayName("current hint")
    void getCurrentHint() {
        assertEquals("W....", progress.getCurrentHint());
    }

    @Test
    @DisplayName("get roundnumber")
    void getRoundNumber() {
        assertEquals(1, progress.getRoundNumber());
    }

    @Test
    @DisplayName("get feedbacks")
    void getFeedback() {
        assertEquals(List.of(), progress.getFeedbacks());
    }

    @Test
    @DisplayName("get gameId")
    void getGameId() {
        assertEquals(1, progress.getGameId());
    }
}