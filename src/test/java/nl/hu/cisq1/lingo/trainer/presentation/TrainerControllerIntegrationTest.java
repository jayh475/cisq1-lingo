package nl.hu.cisq1.lingo.trainer.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.SecurityTestConfiguration;
import nl.hu.cisq1.lingo.security.data.SpringUserRepository;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.presentation.Dto.AttemptDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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


import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest

@Import({CiTestConfiguration.class})

@AutoConfigureMockMvc
class TrainerControllerIntegrationTest {
    private String jwtToken;
    private MockHttpServletResponse response;

    @Autowired
    SpringUserRepository springUserRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws Exception {
        this.jwtToken = SecurityTestConfiguration.getJwtToken(springUserRepository);
        RequestBuilder newGameLingoGameRequest = MockMvcRequestBuilders
                .post("/lingoGame/start").header("Authorization", jwtToken);
        response = mockMvc.perform(newGameLingoGameRequest).andReturn().getResponse();
    }

    @AfterEach
    void tearDown() {
        this.jwtToken = null;
        response = null;
        mockMvc = null;
        springUserRepository = null;
    }


    @Test
    @DisplayName("first guess at start of the game")
    void guess() throws Exception {

        Integer gameId = JsonPath.read(response.getContentAsString(), "$.gameId");
        AttemptDto guess = new AttemptDto();
        guess.attempt = "knoop";

        String guessBody = new ObjectMapper().writeValueAsString(guess);
        RequestBuilder guessRequest = MockMvcRequestBuilders
                .post("/lingoGame/" + gameId + "/guess")
                .contentType(MediaType.APPLICATION_JSON).header("Authorization", jwtToken)
                .content(guessBody);

        mockMvc.perform(guessRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameStatus", is(GameStatus.PLAYING.toString())))
                .andExpect(jsonPath("$.gameId", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.feedbacks", hasSize(1)));
    }


    @Test
    @DisplayName("Get game")
    void getGame() throws Exception {
        Integer gameId = JsonPath.read(response.getContentAsString(), "$.gameId");
        RequestBuilder guessRequest = MockMvcRequestBuilders
                .get("/lingoGame/" + gameId)
                .contentType(MediaType.APPLICATION_JSON).header("Authorization", jwtToken);

        mockMvc.perform(guessRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameStatus", is(GameStatus.PLAYING.toString())))
                .andExpect(jsonPath("$.id", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.player", is("jayh475")));


    }


    @Test
    @DisplayName(" can not start new round")
    void canNotStartround() throws Exception {
        Integer gameId = JsonPath.read(response.getContentAsString(), "$.gameId");

        RequestBuilder roundRequest = MockMvcRequestBuilders
                .post("/lingoGame/" + gameId + "/newRound")
                .contentType(MediaType.APPLICATION_JSON).header("Authorization", jwtToken);

        mockMvc.perform(roundRequest)
                .andExpect(status().isBadRequest());



    }





}

