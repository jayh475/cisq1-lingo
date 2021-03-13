package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.Setter;
import nl.hu.cisq1.lingo.trainer.domain.exception.CustomException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static nl.hu.cisq1.lingo.trainer.domain.Mark.ABSENT;

@Getter
@Setter
public class Round {
    private final String wordToGuess;
    private final List<Feedback> feedbackList = new ArrayList<>();


    public Round(String wordToGuess) {
        this.wordToGuess = wordToGuess;
    }


    public List<Mark> generateMarks(String attempt) {
        List<Mark> marks = new ArrayList<>();
        String[] wordToGuessList = wordToGuess.split("");
        String[] lettersOfAttempt = attempt.split("");


        List<String> lettersThatArePresent = new ArrayList<>();


        if (wordToGuess.length() != attempt.length()) {
            throw new CustomException("wordToGuess length does not match attempt length");

        } else {

            for (int i = 0; i < wordToGuess.length(); i++) {
                if (wordToGuessList[i].equals(lettersOfAttempt[i])) {
                    marks.add(CORRECT);
                    lettersThatArePresent.add(lettersOfAttempt[i]);

                } else if (Arrays.asList(wordToGuessList).contains(lettersOfAttempt[i])) {
                    marks.add(PRESENT);

                } else {
                    marks.add(ABSENT);
                }
            }
        }
        feedbackList.add(new Feedback(attempt, marks));
        giveHint();
        return marks;
    }


    public List<String> initializeFirstHint() {
        String[] wordToGuessList = this.wordToGuess.split("");
        List<String> hints = new ArrayList<>();

        for (int i = 0; i < wordToGuessList.length; i++) {
            if (i == 0) {

                hints.add(wordToGuessList[i]);
            } else {
                hints.add(".");
            }

        }
        return hints;
    }





    public void guess(String attempt) {
        if (checkIfRoundFinished()) {
            throw new CustomException("cannot guess because round is finished");
        } else {
            generateMarks(attempt);

        }


    }

    public List<String> giveHint() {
        if (feedbackList.isEmpty()) {
            return initializeFirstHint();
        } else if (feedbackList.size() == 1) {
            return feedbackList.get(0).giveHint(initializeFirstHint(), wordToGuess);

        } else {
            return feedbackList.get(feedbackList.size() - 1).giveHint(feedbackList.get(feedbackList.size() - 2).getHint(), wordToGuess);

        }
    }


    public boolean checkIfRoundFinished() {
        return feedbackList.size() >= 5 || feedbackList.stream().anyMatch(Feedback::isWordGuessed);
    }


    public Integer getCurrentWorthLength() {
        return wordToGuess.length();
    }


}







