package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.hu.cisq1.lingo.trainer.domain.exception.CustomException;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;

import java.util.*;

@Getter
//@Setter
@NoArgsConstructor
@Entity
@Table(name = "round")
public class Round {

    private transient static final int MAX_ATTEMPTS = 5;
    @Id
    @GeneratedValue
    private int id;
    private String wordToGuess;


    @OneToMany
    @Cascade(CascadeType.ALL)
    private final List<Feedback> feedbackList = new ArrayList<>();


    public Round(String wordToGuess) {
        this.wordToGuess = wordToGuess;
    }


    public String initializeFirstHint() {
        String[] wordToGuessList = this.wordToGuess.split("");
        List<String> hints = new ArrayList<>();

        for (int i = 0; i < wordToGuessList.length; i++) {
            if (i == 0) {

                hints.add(wordToGuessList[i]);
            } else {
                hints.add(".");
            }
        }
        return String.join("", hints);
    }


    public void guess(String attempt) {
        if (lostGame()) {
            throw new CustomException("cannot guess because round is finished");
        } else {
            feedbackList.add(Feedback.of(attempt, wordToGuess));
            giveHint();
        }
    }

    public int calculateScore() {
        return MAX_ATTEMPTS * (MAX_ATTEMPTS - getAttemptCount()) + MAX_ATTEMPTS;
    }


    private int getAttemptCount() {
        return feedbackList.size();
    }


    public String giveHint() {
        if (feedbackList.isEmpty()) {
            return initializeFirstHint();
        } else if (getAttemptCount() == 1) {
            return feedbackList.get(0).giveHint(initializeFirstHint(), wordToGuess);
        } else {
            Feedback feedback = feedbackList.get(getAttemptCount() - 1);
            String previousHint = feedbackList.get(getAttemptCount() - 2).getHint();
            return feedback.giveHint(previousHint, wordToGuess);
        }
    }


    public boolean lostGame() {
        return getAttemptCount() >= MAX_ATTEMPTS;
    }

    public boolean isWon() {
        return feedbackList.stream().anyMatch(Feedback::isWordGuessed);
    }


    public Integer getCurrentWorthLength() {
        return wordToGuess.length();
    }


}







