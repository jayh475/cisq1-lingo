package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.hu.cisq1.lingo.trainer.domain.exception.CustomException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "lingo_game")
public class LingoGame {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private int score;


    @OneToMany(mappedBy = "lingo_game")
    private final List<Round> roundList = new ArrayList<>();




    public void startNewRound(String wordToGuess) {
        if (checkIfRoundCanStart()) {
            roundList.add(new Round(wordToGuess));
        } else {
            throw new CustomException("can't start new round, finish last round first");
        }
    }


    public void guess(String attempt) {
        Round round = getLastRound();
        round.guess(attempt);
    }

    public Progress showProgress() {
        Round round = getLastRound();
        return new Progress(round.getFeedbackList(), score, round.giveHint());
    }


    public Round getLastRound() {
        Round round;
        if (roundList.size() == 1) {
            round = roundList.get(0);
        } else if (!roundList.isEmpty()) {
            round = roundList.get(roundList.size() - 1);
        } else {
            throw new CustomException("There is no round");
        }
        return round;
    }

    private boolean checkIfRoundCanStart() {
        return roundList.stream().anyMatch(Round::checkIfRoundFinished) || roundList.isEmpty();
    }



}
