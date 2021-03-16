package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;
import lombok.*;

import javax.persistence.*;


@Setter
@Getter
@ToString

@NoArgsConstructor
@Table(name = "progress")
public class Progress {

    private List<Feedback> feedbackList;
    private int score;
   private List<String> currentHint;
    private int roundNumber;


    public Progress(List<Feedback> feedbackList, int score, List<String> currentHint) {
    }



}
