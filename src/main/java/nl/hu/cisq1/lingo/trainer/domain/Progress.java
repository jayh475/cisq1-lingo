package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class Progress {
    private int score;
   private List<String> hint;
    private int roundNumber;




}
