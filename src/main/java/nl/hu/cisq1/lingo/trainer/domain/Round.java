package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.hu.cisq1.lingo.trainer.domain.exception.CustomException;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;

import java.util.*;


import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static nl.hu.cisq1.lingo.trainer.domain.Mark.ABSENT;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "round")
public class Round {

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


    public List<Mark> generateMarks(String attempt) {
        List<Mark> marks = new ArrayList<>();
        String[] wordToGuessList = wordToGuess.toLowerCase().split("");
        String[] lettersOfAttempt = attempt.toLowerCase().split("");
        List<String> nonGuessedLetters = new ArrayList<>();

        if (attempt.length() != wordToGuess.length()) {
            throw new CustomException("attempt does not match size wordToGuess");
        }



        else {
            for (int i = 0; i < wordToGuessList.length; i++) {
                if (!lettersOfAttempt[i].equals(wordToGuessList[i])) {
                    nonGuessedLetters.add(wordToGuessList[i]);
                }

            }

            for (int i = 0; i < wordToGuessList.length; i++) {
                String letterInAttempt = lettersOfAttempt[i];
                String letter = wordToGuessList[i];

                if (letter.equals(lettersOfAttempt[i])) {
                    marks.add(CORRECT);

                } else if (nonGuessedLetters.contains(lettersOfAttempt[i])) {
                    if (nonGuessedLetters.stream().filter(ch -> ch.equals(String.valueOf(letterInAttempt.charAt(0)))).count() <= wordToGuess.toLowerCase().chars().filter(ch -> ch == letterInAttempt.charAt(0)).count()) {
                        marks.add(PRESENT);
                        nonGuessedLetters.remove(letterInAttempt);
                    } else {
                        marks.add(ABSENT);
                    }
                } else {
                    marks.add(ABSENT);
                }
            }
        }
        feedbackList.add(new Feedback(attempt, marks));

        giveHint();
        return marks;
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
        if (checkIfRoundFinished()) {
            throw new CustomException("cannot guess because round is finished");
        } else {
            generateMarks(attempt);
        }
    }

    public int calculateScore() {
        return 5 * (5 - getAttemptCount()) + 5;

    }

    private int getAttemptCount() {
        return feedbackList.size();
    }


    public String giveHint() {
        if (feedbackList.isEmpty()) {
            return initializeFirstHint();
        } else if (getAttemptCount() == 1) {
            Feedback feedback = feedbackList.get(0);
            feedback.setHint(feedback.giveHint(initializeFirstHint(), wordToGuess));
            return feedback.getHint();

        } else {
            Feedback feedback = feedbackList.get(getAttemptCount()-1);
            feedback.setHint(feedback.giveHint(feedbackList.get(getAttemptCount() - 2).getHint(), wordToGuess));

            return feedback.getHint();

        }
    }


    public boolean checkIfRoundFinished() {
        return getAttemptCount() >= 5 || feedbackList.stream().anyMatch(Feedback::isWordGuessed);
    }

    public boolean isWon() {
        return feedbackList.stream().anyMatch(Feedback::isWordGuessed);
    }


    public Integer getCurrentWorthLength() {
        return wordToGuess.length();
    }


}







