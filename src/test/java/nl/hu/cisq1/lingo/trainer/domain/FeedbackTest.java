package nl.hu.cisq1.lingo.trainer.domain;


import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;


class FeedbackTest {




    @Test
    @DisplayName("word is guessed if all letters are correct")
    void wordIsGuessed(){
//        p: arrange
//        q: act
        String  attempt = "WOORD";
        List<Mark> marks = List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT);
        Feedback feedback = new Feedback(attempt,marks);

//        R: assert
        assertTrue(feedback.isWordGuessed());

    }

    @Test
    @DisplayName("word is not guessed if not all letters are correct")
    void wordIsNotGuessed(){
//        p: arrange
//        q: act
        String  attempt = "WOORD";
        List<Mark> marks = List.of(CORRECT, CORRECT, CORRECT, CORRECT, ABSENT);
        Feedback feedback = new Feedback(attempt,marks);

//        R: assert
        assertFalse(feedback.isWordGuessed());

    }

    @Test
    @DisplayName("guess is valid if no letter is invalid")
    void GuessIsvalid(){
//        p: arrange
//        q: act
        String  attempt = "WOORD";
        List<Mark> marks = List.of(CORRECT, CORRECT, CORRECT, CORRECT, ABSENT);
        Feedback feedback = new Feedback(attempt,marks);

//        R: assert
        assertTrue(feedback.isGuessValid());

    }

    @Test
    @DisplayName("guess is invalid if all letter is invalid")
    void GuessIsInvalid(){
//        p: arrange
//        q: act
        String  attempt = "WOORD";
        List<Mark> marks = List.of(INVALID,INVALID,INVALID,INVALID);
        Feedback feedback = new Feedback(attempt,marks);

//        R: assert
        assertTrue(feedback.isGuessInvalid());

    }


}