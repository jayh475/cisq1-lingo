package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;

import lombok.*;

import javax.persistence.*;


@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode


public class Progress {

    private int score;
    private String currentHint;
    private int roundNumber;
    private GameStatus gameStatus;
    private List<Feedback> feedbackList;

    public Progress(GameStatus gameStatus,int score, String currentHint, int roundNumber,List<Feedback> feedbacks) {
        this.gameStatus = gameStatus;
        this.score = score;
        this.currentHint = currentHint;
        this.roundNumber = roundNumber;
        this.feedbackList = feedbacks;
    }


}



