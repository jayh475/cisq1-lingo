package nl.hu.cisq1.lingo.trainer.domain;



import java.util.List;

public class Feedback {
    private final  String attempt;
    private final List<Mark> marks;

    public Feedback(String attempt, List<Mark> marks) {
        this.attempt = attempt;
        this.marks = marks;
    }

    public boolean isWordGuessed(){
        return marks.stream().allMatch(mark -> mark == Mark.CORRECT);
//        for(Mark mark : marks){
//            if (mark != mark.CORRECT){
//                return false;
//            }
//        }
//        return true;

//        marks.stream().filter(mark -> mark == Mark.CORRECT).count();

    }

    public boolean isGuessValid(){
        return marks.stream().noneMatch(mark -> mark == Mark.INVALID);
    }

    public boolean isGuessInvalid(){
        return marks.stream().allMatch(mark -> mark == Mark.INVALID);
    }



}
