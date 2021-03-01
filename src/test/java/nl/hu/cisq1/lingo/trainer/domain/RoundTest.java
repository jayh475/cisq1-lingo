package nl.hu.cisq1.lingo.trainer.domain;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.ABSENT;
import static nl.hu.cisq1.lingo.trainer.domain.Mark.CORRECT;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {


//    static Stream<Arguments> provideAttemptsExample() {
//        Feedback feedback = new Feedback(List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT));
//
//        return Stream.of(
//                Arguments.of(feedback),
//                Arguments.of(feedback),
//                Arguments.of(feedback),
//                Arguments.of(feedback),
//                Arguments.of(feedback),
//                Arguments.of(feedback)
//
//
//        );
//    }

//    @MethodSource("provideAttemptsExample")
//    @ParameterizedTest




        static Stream<Arguments> provideMarkExamples() {
        Feedback feedback = new Feedback(List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT));

        return Stream.of(
                Arguments.of(feedback),
                Arguments.of(feedback),
                Arguments.of(feedback),
                Arguments.of(feedback),
                Arguments.of(feedback),
                Arguments.of(feedback)


        );
    }

        @MethodSource("provideAttemptsExample")
    @DisplayName("give right marks")
    @ParameterizedTest
    void giveMarks(){
//        Round round = new Round();
//        assertEquals(round.iets(attempt, word) , marks);

    }



//
//
//    @Test
//    @DisplayName("succeeded within the five attemps")
//    void




//    @Test
//    @DisplayName("Player score can't be negative")
//    void
//

}