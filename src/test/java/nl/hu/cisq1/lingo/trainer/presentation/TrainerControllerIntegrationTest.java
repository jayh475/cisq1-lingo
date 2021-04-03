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


//Stap 2: Maak een controller integration testIn een @SpringBootTest kunnen we ook HTTP simuleren, zodat we vanaf de controller kunnen testen alsof we HTTP gebruiken. Hiervoor gebruiken we MockMvc. Zie als voorbeeld de controller test van de words component. MockMvc moet klaargezet worden voor onze test, annoteer de testklasse met @AutoConfigureMockMvc.
//        MockMvc kan je als dependency laten injecteren via property injection:@Autowiredprivate MockMvc mockMvc;Vervolgens kan je de MockMvcRequestBuilder gebruiken om een request op te bouwen.
//        Deze request kan je aan meegeven aan de perform-methode: (mockMvc.perform(request)). Daar kan je vervolgens rechtstreeks matchen op eigenschappen om assertions op uit te voeren:mockMvc.
//        perform(request)    .andExpect(status().isOk())De status() is hier een static import van MockMvcResultMatchers. Matchers richten de focus op een bepaalde eigenschap van je response. Daar kan je vervolgens een assertion op doen. Te importeren met:import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//        Natuurlijk willen we meer dan alleen de statuscode verifiëren. Daar zijn weer andere result matchers voor,
//        bijvoorbeeld:1content(): match op de gehele response bodyjsonPath(): match op een bepaalde path binnen de response body JSON oZie voor een voorbeeld: https://mkyong.com/spring-boot/spring-test-how-to-test-a-json-array-in-jsonpath/ cookie(): match op een specifieke response cookieheader(): match op een specfieke response header
//

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

//         JSON genereren van AttemptDto object en deze als een string returnen
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