package nl.hu.cisq1.lingo.trainer.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.SecurityConfiguration;
import nl.hu.cisq1.lingo.security.application.UserService;
import nl.hu.cisq1.lingo.security.data.SpringUserRepository;
import nl.hu.cisq1.lingo.security.data.UserProfile;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.presentation.Dto.AttemptDto;
import org.apache.tomcat.util.http.parser.Authorization;
import org.h2.engine.User;
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

import org.springframework.security.core.Authentication;
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

    @Autowired
    SpringUserRepository springUserRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        this.jwtToken = SecurityConfiguration.getJwtToken(springUserRepository);

    }


    @Test
    @DisplayName("first guess at start of the game")
    void guess() throws Exception {

//        TODO USERPROFILE mock
        RequestBuilder newGameLingoGameRequest = MockMvcRequestBuilders
                .post("/lingoGame/start").header("Authorization", jwtToken);
        MockHttpServletResponse response = mockMvc.perform(newGameLingoGameRequest).andReturn().getResponse();
        Integer gameId = JsonPath.read(response.getContentAsString(), "$.gameId");
        AttemptDto guess = new AttemptDto();
        guess.attempt = "knoop";

        String guessBody = new ObjectMapper().writeValueAsString(guess);
        RequestBuilder guessRequest = MockMvcRequestBuilders
                .post("/lingoGame/" + gameId + "/guess")
                .contentType(MediaType.APPLICATION_JSON).header("Authorization" ,jwtToken)

                .content(guessBody);
        mockMvc.perform(guessRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameStatus", is(GameStatus.PLAYING.toString())))
                .andExpect(jsonPath("$.gameId", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.feedbacks", hasSize(1)));
    }








}

