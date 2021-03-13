package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.hu.cisq1.lingo.trainer.domain.exception.CustomException;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
public class LingoGame {
    private int score;
    private final List<Round> roundList = new ArrayList<>();


    public void startNewRound(String wordToGuess) {
        if (roundList.stream().anyMatch(Round::checkIfRoundFinished) || roundList.isEmpty()) {
            roundList.add(new Round(wordToGuess));
        } else {
            throw new CustomException("can't start new round, finish last round first");
        }

    }

    public Round getLastRound(){
        Round round;
        if (roundList.size() == 1) {
            round = roundList.get(0);
        } else {
            round = roundList.get(roundList.size() - 1);
        }
        return round;
    }


    public void guess(String attempt) {
        Round round = getLastRound();
        round.guess(attempt);
    }

    public Progress  showProgress(){
        Round round = getLastRound();
        return new Progress(round.getFeedbackList(),score,round.giveHint());

    }







}
