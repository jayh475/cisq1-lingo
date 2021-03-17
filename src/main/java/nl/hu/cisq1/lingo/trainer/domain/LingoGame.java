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


@Entity
@Table(name = "lingo_game")
public class LingoGame {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private int score;

    @OneToMany
    private final List<Round> roundList = new ArrayList<>();


    private GameStatus gameStatus;


    public LingoGame() {
        gameStatus = GameStatus.WAITING_FOR_ROUND;
    }


    public void startNewRound(String wordToGuess) {
        if (checkIfRoundCanStart()) {
            roundList.add(new Round(wordToGuess));
            gameStatus = GameStatus.PLAYING;
        } else {
            throw new CustomException("can't start new round, finish last round first");
        }
    }


    public void guess(String attempt) {
        if (gameStatus == GameStatus.PLAYING) {
            Round round = getLastRound();
            round.guess(attempt);
            if (round.isWon()) {
                calculateScore(round);
                gameStatus = GameStatus.WAITING_FOR_ROUND;
            }

        } else {
            throw new CustomException("can not guess");

        }


    }

    public void calculateScore(Round round) {
        score = 5 * (5 - round.getFeedbackList().size()) + 5;


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
        return (roundList.stream().allMatch(Round::checkIfRoundFinished) && gameStatus == GameStatus.WAITING_FOR_ROUND) || roundList.isEmpty();
    }


}
