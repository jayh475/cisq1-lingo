package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.CustomException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static nl.hu.cisq1.lingo.trainer.domain.Mark.ABSENT;

public class Round {
    private String wordToGuess;
    private List<Feedback> attempts = new ArrayList<>();


    public Round(String wordToGuess) {
        this.wordToGuess = wordToGuess;
    }

    public List<Mark> giveMarks(String attempt) {
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
        return marks;


    }

    public void startRound(){
        if(attempts.isEmpty()){

        }

    }


}
