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

    public Progress(int score, String currentHint, int roundNumber) {
        this.score = score;
        this.currentHint = currentHint;
        this.roundNumber = roundNumber;
    }


}



