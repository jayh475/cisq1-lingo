package nl.hu.cisq1.lingo.trainer.domain;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {






    @MethodSource("provideMarkExamples")
    @DisplayName("give right marks")
    @ParameterizedTest
    void giveMarks(String attempt,String wordToGuess, List<Mark> marks){
        Round round = new Round(wordToGuess);

        assertEquals(round.generateMarks(attempt) , marks);

    }


        static Stream<Arguments> provideMarkExamples() {

        return Stream.of(
                Arguments.of("WAADT","WOORD",List.of(CORRECT, ABSENT, ABSENT,PRESENT ,ABSENT)),
                Arguments.of("WOORDEN","WOORD",List.of(INVALID,INVALID,INVALID,INVALID,INVALID)),
                Arguments.of("HET","WOORD",List.of(INVALID,INVALID,INVALID,INVALID,INVALID))



        );
    }


    @Test
    @DisplayName(" return true if round finishes at five attempts ")
    void roundfinished(){
        Feedback feedback = new Feedback("WAADT",List.of(CORRECT, ABSENT, ABSENT,PRESENT ,ABSENT));
        Round round = new Round("WOORD");
        for(int i =0; i < 5; i++) {
            round.getFeedbackHistory().add(feedback);
        }
        assertTrue(round.checkIfRoundFinished());
    }

    @Test
    @DisplayName("add feedback less than 5 attempts")
    void guess(){
        Round round = new Round("WOORD");
        round.guess("WAARD");

      for(Feedback o : round.getFeedbackHistory()){
          System.out.println(o);
      }
        assertFalse( round.getFeedbackHistory().isEmpty());

    }

//    @Test
//    @DisplayName("exception is thrown because attempts 5 or more")






//    @Test
//    @DisplayName("initialize round ")
//    void initializeRound(){
//        Round round = new Round("WOORD");
//        assertEquals("W....",round.initializeRound());
//
//    }


}