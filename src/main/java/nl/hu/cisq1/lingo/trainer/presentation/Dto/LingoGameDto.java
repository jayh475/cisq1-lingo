package nl.hu.cisq1.lingo.trainer.presentation.Dto;

import lombok.Getter;

@Getter
public class LingoGameDto {
    private final int score;
    private final String currentHint;
    private final int roundNumber;
    private final int id;


    public LingoGameDto( int score, String currentHint, int roundNumber, int id) {
        this.score = score;
        this.currentHint = currentHint;
        this.roundNumber = roundNumber;

        this.id = id;
    }


}
