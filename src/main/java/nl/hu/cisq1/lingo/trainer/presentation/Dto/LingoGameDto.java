package nl.hu.cisq1.lingo.trainer.presentation.Dto;

import lombok.Getter;
import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;


import java.util.List;

@Getter
public class LingoGameDto {
    private final GameStatus gameStatus;
    private final int score;
    private final String currentHint;
    private final int roundNumber;
    private final int gameId;
    private  List<Feedback> feedbacks;


    public LingoGameDto(GameStatus gameStatus, int score, String currentHint, int roundNumber, int gameId, List<Feedback> feedbacks) {
        this.gameStatus = gameStatus;
        this.score = score;
        this.currentHint = currentHint;
        this.roundNumber = roundNumber;
        this.feedbacks = feedbacks;
        this.gameId = gameId;
    }


}
