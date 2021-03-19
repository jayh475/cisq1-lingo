package nl.hu.cisq1.lingo.trainer.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.hu.cisq1.lingo.trainer.domain.exception.CustomException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
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

            } else if (marks.get(i) != Mark.CORRECT) {
                hints.add(previousHintList[i]);

            } else {
                hints.add(".");
            }
        }

        this.hint = String.join("", hints);
        return hint;

    }


    public String getHint() {
        return hint;
    }
}
