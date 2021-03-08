package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.Setter;
import nl.hu.cisq1.lingo.trainer.domain.exception.CustomException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static nl.hu.cisq1.lingo.trainer.domain.Mark.ABSENT;


public class Round {
    private String wordToGuess;
    private List<Feedback> feedbackList = new ArrayList<>();


    public Round(String wordToGuess) {
        this.wordToGuess = wordToGuess;

    }


    public List<Mark> generateMarks(String attempt) {
        List<Mark> marks = new ArrayList<>();
        String[] wordToGuessList = wordToGuess.split("");
        String[] lettersOfAttempt = attempt.split("");

        if (wordToGuess.length() != attempt.length()) {
            for (int i = 0; i < wordToGuess.length(); i++) {
                marks.add(INVALID);
            }
        } else {


            for (int i = 0; i < wordToGuess.length(); i++) {
                if (wordToGuessList[i].equals(lettersOfAttempt[i])) {
                    marks.add(CORRECT);
                } else if (Arrays.asList(wordToGuessList).contains(lettersOfAttempt[i])) {
                    marks.add(PRESENT);
                } else {
                    marks.add(ABSENT);
                }
            }
        }
        feedbackList.add(new Feedback(attempt, marks));
        return marks;
    }


    public String initializeRound() {
        String[] wordToGuessList = this.wordToGuess.split("");
        List<String> hints = new ArrayList<>();
        if (feedbackList.isEmpty()) {

            for (int i = 0; i < wordToGuessList.length; i++) {
                if (i == 0) {
                    hints.add(wordToGuessList[i]);
                } else {
                    hints.add(".");
                }

            }
        }
        return String.join("", hints);
    }

    public List<Feedback> getFeedbackHistory() {
        return feedbackList;
    }


    public void guess(String attempt){
        if(checkIfRoundFinished()){
            throw new CustomException("cannot guess because round is finished");
        }else{
            generateMarks(attempt);

        }


    }


    public boolean checkIfRoundFinished() {
        return feedbackList.size() >= 5 || feedbackList.stream().anyMatch(Feedback :: isWordGuessed);
    }


}






