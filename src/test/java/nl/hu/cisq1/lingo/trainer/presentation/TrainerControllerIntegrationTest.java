package nl.hu.cisq1.lingo.trainer.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import nl.hu.cisq1.lingo.CiTestConfiguration;


import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.presentation.Dto.AttemptDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




@SpringBootTest
@Import(CiTestConfiguration.class)
@AutoConfigureMockMvc
class TrainerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("first guess at start of the game")
    void guess() throws Exception {
        RequestBuilder newGameLingoGameRequest = MockMvcRequestBuilders
                .post("/lingoGame/start");
        MockHttpServletResponse response = mockMvc.perform(newGameLingoGameRequest).andReturn().getResponse();
        Integer gameId = JsonPath.read(response.getContentAsString(), "$.gameId");
        AttemptDto guess = new AttemptDto();
        guess.attempt = "knoop";

        String guessBody = new ObjectMapper().writeValueAsString(guess);
        RequestBuilder guessRequest = MockMvcRequestBuilders
                .post("/lingoGame/" + gameId + "/guess")
                .contentType(MediaType.APPLICATION_JSON)

                .content(guessBody);
        mockMvc.perform(guessRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameStatus", is(GameStatus.PLAYING.toString())))
                .andExpect(jsonPath("$.gameId", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.feedbacks", hasSize(1)));
    }



}