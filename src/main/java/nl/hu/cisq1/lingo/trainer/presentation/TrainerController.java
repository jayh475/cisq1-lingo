package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.LingoGameService;
import nl.hu.cisq1.lingo.trainer.domain.LingoGame;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.trainer.presentation.Dto.AttemptDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lingoGame")
public class TrainerController {
    private final LingoGameService lingoGameService;

    public TrainerController(LingoGameService lingoGameService) {
        this.lingoGameService = lingoGameService;
    }

    @GetMapping("/{id}")
    public LingoGame getGame(@PathVariable(name = "id") int id) {
        return  lingoGameService.getGame(id);
    }

    @PostMapping("/start")
    public Progress startGame() {
        return this.lingoGameService.startGame();
    }

    @PostMapping("{id}/newRound")
    public Progress startRound(@PathVariable(name="id") Integer gameId){
        return this.lingoGameService.startNewRound(gameId);
    }


    @PostMapping("/{id}/guess")
    public Progress guess(@PathVariable(name = "id") Integer gameId, @RequestBody AttemptDto attemptDto) {
        return lingoGameService.guess(gameId, attemptDto.attempt);
    }

}
