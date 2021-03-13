package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.Setter;
import nl.hu.cisq1.lingo.trainer.domain.exception.CustomException;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

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
        TreeMap<Integer,String> treeMap= new TreeMap<>();
        int count = 1;

        if (attempt.length() != wordToGuess.length()) {
            throw new CustomException("attempt does not match size wordToGuess");
        } else {
            for (int i = 0; i < wordToGuessList.length; i++) {
                String letterInAttempt = lettersOfAttempt[i];
                String letter = wordToGuessList[i];

                if (letter.equals(lettersOfAttempt[i] )) {
                    marks.add(CORRECT); // eerst de goeie uit de lijst halen en dan checken of presenten er zijn.

                } else if (Arrays.asList(wordToGuessList).contains(lettersOfAttempt[i])  ) {
                    treeMap.put(count++,lettersOfAttempt[i]);
                    System.out.println(treeMap);
                    System.out.println(wordToGuess.chars().filter(ch -> ch == letterInAttempt.charAt(0)).count());


                    if(treeMap.lastKey()  <=  wordToGuess.chars().filter(ch -> ch == letterInAttempt.charAt(0)).count() ) {
                        System.out.println("ok");
                        marks.add(PRESENT);
                    }else{
                        marks.add(ABSENT);
                    }


                } else {
                    marks.add(ABSENT);
                }
            }

        }
        System.out.println(marks);
        feedbackList.add(new Feedback(attempt, marks));
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







