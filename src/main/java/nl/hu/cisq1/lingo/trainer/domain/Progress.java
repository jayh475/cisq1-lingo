package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Value;

import java.util.List;

@Value
public class Progress {
    int score;
    String currentHint;
    int roundNumber;
    GameStatus gameStatus;
    List<Feedback> feedbacks;
    Integer gameId;
}