package nl.hu.cisq1.lingo.trainer.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import nl.hu.cisq1.lingo.trainer.domain.exception.CustomException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static nl.hu.cisq1.lingo.trainer.domain.Mark.ABSENT;

@Getter
@NoArgsConstructor

@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue
    private int id;
    private String hint;
    private String attempt;

    @Enumerated
    @ElementCollection(targetClass = Mark.class)
    private List<Mark> marks;


    public static Feedback of(String attempt, String wordToGuess) {
        List<Mark> marks = new ArrayList<>();
        String[] wordToGuessList = wordToGuess.toLowerCase().split("");
        String[] lettersOfAttempt = attempt.toLowerCase().split("");
        List<String> nonGuessedLetters = new ArrayList<>();

        if (attempt.length() != wordToGuess.length()) {
            throw new CustomException("attempt does not match size wordToGuess");
        }

        for (int i = 0; i < wordToGuessList.length; i++) {
//            return marks.stream().allMatch(mark -> mark == Mark.CORRECT);
//            Arrays.stream(wordToGuessList).noneMatch(c -> c.equals(nonGuessedLetters[i])   );

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
                }
            } else {
                marks.add(ABSENT);
            }
        }
        return new Feedback(attempt, marks);
    }


    public Feedback(String attempt, List<Mark> marks) {
        if (attempt.length() != marks.size()) {
            throw new CustomException("length attempt does not match mark size");
        }
        this.attempt = attempt;
        this.marks = marks;


    }

    public boolean isWordGuessed() {
        return marks.stream().allMatch(mark -> mark == Mark.CORRECT);

    }


    public String giveHint(String previousHint, String wordToGuess) {
        String[] listOfLetters = wordToGuess.toUpperCase().split("");
        List<String> hints = new ArrayList<>();
        String[] previousHintList = previousHint.toUpperCase().split("");

        for (int i = 0; i < listOfLetters.length; i++) {
            if (marks.get(i) == Mark.CORRECT) {
                hints.add(listOfLetters[i]);

            } else {
                hints.add(previousHintList[i]);
            }
        }
        this.hint = String.join("", hints);
        return hint;

    }


    public String getHint() {
        return hint;
    }




}
