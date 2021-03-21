package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.LingoGameService;
import nl.hu.cisq1.lingo.trainer.domain.LingoGame;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.trainer.presentation.Dto.AttemptDto;
import nl.hu.cisq1.lingo.trainer.presentation.Dto.LingoGameDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lingoGame")
public class TrainerController {
    public final String host = "http://localhost:8082";
    private final LingoGameService lingoGameService;

    public TrainerController(LingoGameService lingoGameService) {
        this.lingoGameService = lingoGameService;
    }

    @GetMapping("/{id}")
    public LingoGameDto getGame(@PathVariable(name = "id") int id){
        LingoGame lingoGame =lingoGameService.getGame(id);

        Progress progress = lingoGame.showProgress();

        return new LingoGameDto( progress.getGameStatus(),progress.getScore(), progress.getCurrentHint(), progress.getRoundNumber(), id,progress.getFeedbackList());
    }


    @CrossOrigin(origins = host)
    @PostMapping("/start")
    public LingoGameDto startGame() {
        LingoGame lingoGame = this.lingoGameService.starGame();
        Progress progress = lingoGame.showProgress();
        return new LingoGameDto(progress.getGameStatus(),progress.getScore(), progress.getCurrentHint(), progress.getRoundNumber(), lingoGame.getId(),progress.getFeedbackList());
    }

    @CrossOrigin(origins = host)
    @PostMapping("/{id}/guess")
    public LingoGameDto guess(@PathVariable(name ="id") Integer gameId, @RequestBody AttemptDto attemptDto){
        LingoGame lingoGame = lingoGameService.getGame(gameId);
        lingoGameService.guess(gameId,attemptDto.attempt);
        Progress progress = lingoGame.showProgress();

        return  new LingoGameDto(progress.getGameStatus(),progress.getScore(), progress.getCurrentHint(), progress.getRoundNumber(), gameId,progress.getFeedbackList());
    }


}
