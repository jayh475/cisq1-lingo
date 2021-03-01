package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.CustomException;

import java.util.List;

public class Round {
    private String wordToGuess;
    private List<Feedback> attempts;

    public Round(String wordToGuess, List<Feedback> feedback) {
        this.wordToGuess = wordToGuess;
        if(attempts.size() > 5){
            throw new CustomException("Attemps can't be more than 5");
        }else {
            this.attempts = feedback;
        }
    }

    public List<Mark> giveMark(){
        return null;


    }







}
