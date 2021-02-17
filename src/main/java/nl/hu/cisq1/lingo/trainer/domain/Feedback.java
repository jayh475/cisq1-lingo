package nl.hu.cisq1.lingo.trainer.domain;



import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Feedback {
    private final  String attempt;
    private final List<Mark> marks;
    private  List<String> hint;

    public Feedback(String attempt, List<Mark> marks) {
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


    @Override
    public int hashCode() {
        return Objects.hash(attempt, marks);
    }

    public List<Character> giveHint(Character previousHint, String wordToGuess, List<Mark> marks){

        return List.of();

    }


    @Override
    public String toString() {
        return "Feedback{" +
                "attempt='" + attempt + '\'' +
                ", marks=" + marks +
                '}';
    }


}
