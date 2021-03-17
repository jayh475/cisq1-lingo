package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.LingoGame;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TrainerService {
    private final SpringGameRepository gameRepository;
    private final WordService wordService;


    public TrainerService(SpringGameRepository gameRepository, WordService wordService) {
        this.gameRepository = gameRepository;
        this.wordService = wordService;
    }

    public int StarGame() {
        LingoGame lingoGame = new LingoGame();
        gameRepository.save(lingoGame);
        return lingoGame.getId();

    }

    public void StartRound(){




    }















}
