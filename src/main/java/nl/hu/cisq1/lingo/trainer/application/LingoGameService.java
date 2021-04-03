package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.LingoGame;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.trainer.domain.exception.CustomException;
import nl.hu.cisq1.lingo.trainer.domain.exception.ResourceNotFoundException;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
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

    public Progress startGame() {
        String wordToGuess = wordService.provideRandomWord(5);
        LingoGame lingoGame = new LingoGame(wordToGuess);
        gameRepository.save(lingoGame);
        return lingoGame.showProgress();
    }


    public LingoGame getGame(int id) {
        LingoGame lingoGame = gameRepository.findLingoGameById(id);
        if (lingoGame == null) {
            throw new ResourceNotFoundException("person with ID["+ id +"] not found");
        }
        return lingoGame;
    }


    public Progress startNewRound(int id) {
        LingoGame lingoGame = gameRepository.findLingoGameById(id);
        String wordToGUess = wordService.provideRandomWord(lingoGame.provideNextWordLength());
        lingoGame.startNewRound(wordToGUess);
        gameRepository.save(lingoGame);
        return lingoGame.showProgress();
    }


    public Progress guess(int id, String attempt) {
        LingoGame lingoGame = gameRepository.findLingoGameById(id);
        lingoGame.guess(attempt);
        gameRepository.save(lingoGame);

        return lingoGame.showProgress();
    }


}
