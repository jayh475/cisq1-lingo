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
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {






    @MethodSource("provideMarkExamples")
    @DisplayName("give right marks")
    @ParameterizedTest
    void giveMarks(String attempt,String wordToGuess, List<Mark> marks){
        Round round = new Round(wordToGuess);

        assertEquals(round.giveMarks(attempt) , marks);

    }


        static Stream<Arguments> provideMarkExamples() {


        return Stream.of(
                Arguments.of("WAADT","WOORD",List.of(CORRECT, ABSENT, ABSENT,PRESENT ,ABSENT)),
                Arguments.of("WOORDEN","WOORD",List.of(INVALID,INVALID,INVALID,INVALID,INVALID)),
                Arguments.of("HET","WOORD",List.of(INVALID,INVALID,INVALID))
//                Arguments.of(feedback),
//                Arguments.of(feedback),
//                Arguments.of(feedback)


        );
    }






//
//
//    @Test
//    @DisplayName("succeeded within the five attemps")
//    void




//    @Test
//    @DisplayName("")
//    void
//

}