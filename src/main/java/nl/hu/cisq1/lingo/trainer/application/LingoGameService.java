package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.LingoGame;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class LingoGameService {
    private final SpringGameRepository gameRepository;
    private final WordService wordService;


    public LingoGameService(SpringGameRepository gameRepository, WordService wordService) {
        this.gameRepository = gameRepository;
        this.wordService = wordService;
    }

    public LingoGame starGame() {
        LingoGame lingoGame = new LingoGame(wordService.provideRandomWord(5));
        gameRepository.save(lingoGame);

        return lingoGame;
    }


    public LingoGame getGame(int id){
        return gameRepository.findLingoGameById(id);
    }


    public void startNewRound(int id){
        LingoGame lingoGame = gameRepository.findLingoGameById(id);
    }

    public void guess(int id, String attempt) {
        LingoGame lingoGame = gameRepository.findLingoGameById(id);
        lingoGame.guess(attempt);
        gameRepository.save(lingoGame);

    }









}
