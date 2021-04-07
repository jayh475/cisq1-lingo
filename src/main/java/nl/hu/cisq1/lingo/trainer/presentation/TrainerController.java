package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.security.data.UserProfile;
import nl.hu.cisq1.lingo.trainer.application.LingoGameService;
import nl.hu.cisq1.lingo.trainer.domain.LingoGame;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.trainer.presentation.Dto.AttemptDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lingoGame")
public class TrainerController {
    private final LingoGameService lingoGameService;

    public TrainerController(LingoGameService lingoGameService) {
        this.lingoGameService = lingoGameService;
    }

    @GetMapping("/{id}")
    public LingoGame getGame(@PathVariable(name = "id") int id, Authentication authentication) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();
        return  lingoGameService.getGame(id,profile.getUsername());
    }

    @PostMapping("/start")
    public Progress startGame(Authentication authentication) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();
        return this.lingoGameService.startGame(profile.getUsername());
    }

    @PostMapping("{id}/newRound")
    public Progress startRound(@PathVariable(name="id") Integer gameId,Authentication authentication){
        UserProfile profile = (UserProfile) authentication.getPrincipal();
        return this.lingoGameService.startNewRound(gameId,profile.getUsername());
    }


    @PostMapping("/{id}/guess")
    public Progress guess(@PathVariable(name = "id") Integer gameId, @RequestBody AttemptDto attemptDto,Authentication authentication) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();
        return lingoGameService.guess(gameId, attemptDto.attempt,profile.getUsername());
    }


}
