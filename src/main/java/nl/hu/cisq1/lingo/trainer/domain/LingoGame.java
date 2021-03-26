package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.hu.cisq1.lingo.trainer.domain.exception.CustomException;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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

    private int score;

    @OneToMany

    @Cascade(CascadeType.ALL)
    private final List<Round> roundList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;


    public LingoGame(String wordToGuess) {
        roundList.add(new Round(wordToGuess));
        gameStatus = GameStatus.PLAYING;
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
                score += round.calculateScore();
                gameStatus = GameStatus.WAITING_FOR_ROUND;
            }

        } else {
            throw new CustomException("can not guess");

        }


    }


    public Progress showProgress() {
        return new Progress(gameStatus, score, giveHint(), roundList.size(), getLastRound().getFeedbackList());
    }


    public int provideNextWordLength() {
        if (getLastRound().getCurrentWorthLength() != 7) {
            return getLastRound().getCurrentWorthLength() + 1;
        } else {
            return 5;
        }
    }

    public String giveHint() {
        return getLastRound().giveHint();
    }


    public Round getLastRound() {
        if (roundList.size() == 1) {
            return roundList.get(0);
        } else {
            return roundList.get(roundList.size() - 1);
        }
    }

    private boolean checkIfRoundCanStart() {
        return (roundList.stream().allMatch(Round::checkIfRoundFinished) && gameStatus == GameStatus.WAITING_FOR_ROUND);
    }


}
