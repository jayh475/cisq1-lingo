package nl.hu.cisq1.lingo.trainer.domain;


import lombok.Getter;
import lombok.Setter;
import nl.hu.cisq1.lingo.trainer.domain.exception.CustomException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Feedback {
    private String attempt;
    private final List<Mark> marks;
    private List<String> hint = new ArrayList<>();

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


    public List<String> giveHint(List<String> previousHint, String wordToGuess) {
        String[] listOfLetters = wordToGuess.toUpperCase().split("");
        List<String> hints = new ArrayList<>();


        for (int i = 0; i < listOfLetters.length; i++) {
            if (marks.get(i) == Mark.CORRECT) {
                hints.add(listOfLetters[i]);

            } else if (marks.get(i) != Mark.CORRECT) {
                hints.add(previousHint.get(i));

            } else {
                hints.add(".");
            }
        }
        this.hint = hints;
        return hints;
    }


    public List<String> getHint() {
        return hint;
    }
}
