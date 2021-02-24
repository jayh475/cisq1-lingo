package nl.hu.cisq1.lingo.trainer.domain;


import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;


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
    @DisplayName("guess is invalid if all letter are invalid")
    void GuessIsInvalid(){
//        p: arrange
//        q: act
        String  attempt = "WOORD";
        List<Mark> marks = List.of(INVALID,INVALID,INVALID,INVALID);
        Feedback feedback = new Feedback(attempt,marks);

//        R: assert
        assertTrue(feedback.isGuessInvalid());

    }



    static Stream<Arguments> provideHintExamples() {
//        q: act
        String woord = "WOORD";

//        R: assert
       return  Stream.of(
               Arguments.of("W....","WORDT", woord, List.of(CORRECT,CORRECT,PRESENT,PRESENT,ABSENT)),
               Arguments.of("WO....","WAARD", woord, List.of(CORRECT,ABSENT,ABSENT,CORRECT,CORRECT)),
               Arguments.of("WO.RD","WOORD", woord, List.of(CORRECT,CORRECT,CORRECT,CORRECT,CORRECT))

       );

    }


    @MethodSource("provideHintExamples")
    @DisplayName("provide hint examples")
    @ParameterizedTest
    void test(String previousHint,String attempt, String word, List<Mark> marks ){

        assertEquals(attempt, word);





    }






}