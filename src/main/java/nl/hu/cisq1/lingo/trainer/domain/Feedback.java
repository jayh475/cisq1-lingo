package nl.hu.cisq1.lingo.trainer.domain;



import nl.hu.cisq1.lingo.trainer.domain.exception.CustomException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Feedback {
    private final String attempt;
    private final List<Mark> marks;
    private  List<String> hint = new ArrayList<>();

    public Feedback(String attempt, List<Mark> marks) {
        if(attempt.length() != marks.size())
            throw new CustomException("length attempt does not match mark size"); // TODO als een woord te lang is dan komt deze error en geen invalid overal wat nu ?

        this.attempt = attempt;
        this.marks = marks;


    }



    public boolean isWordGuessed(){
        return marks.stream().allMatch(mark -> mark == Mark.CORRECT);


//        marks.stream().filter(mark -> mark == Mark.CORRECT).count();

    }

    public boolean isGuessValid(){
        return marks.stream().noneMatch(mark -> mark == Mark.INVALID);
    }

    public boolean isGuessInvalid(){
        return marks.stream().allMatch(mark -> mark == Mark.INVALID);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(attempt, feedback.attempt) &&
                Objects.equals(marks, feedback.marks);
    }


    public List<String> giveHint(List<String> previousHint, String wordToGuess){
        String[] listOfLetters = wordToGuess.toUpperCase().split("");
        List<String> hints = new ArrayList<>();


        for(int i = 0; i < listOfLetters.length; i++ ){
            if(marks.get(i) == Mark.CORRECT){
                hints.add(listOfLetters[i]);

            }
            else if(marks.get(i) != Mark.CORRECT){
                    hints.add(previousHint.get(i));

                }
            else {
                hints.add(".");
            }
            }
        this.hint = hints;
        return hints;
        }




    public String toString() {
        return "Feedback{" +
                "attempt='" + attempt + '\'' +
                ", marks=" + marks +
                '}';
    }







}
