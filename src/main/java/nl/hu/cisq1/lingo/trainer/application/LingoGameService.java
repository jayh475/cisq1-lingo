package nl.hu.cisq1.lingo.trainer.application;


import nl.hu.cisq1.lingo.security.application.UserService;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.LingoGame;
import nl.hu.cisq1.lingo.trainer.domain.Progress;

import nl.hu.cisq1.lingo.trainer.domain.exception.CustomException;
import nl.hu.cisq1.lingo.trainer.domain.exception.ResourceNotFoundException;
import nl.hu.cisq1.lingo.words.application.WordService;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class LingoGameService {
    private final SpringGameRepository gameRepository;
    private final WordService wordService;
    private final UserService userService;


    public LingoGameService(SpringGameRepository gameRepository, WordService wordService, UserService userService) {
        this.gameRepository = gameRepository;
        this.wordService = wordService;
        this.userService = userService;
    }

    public Progress startGame(String username) {
        String wordToGuess = wordService.provideRandomWord(5);
        LingoGame lingoGame = new LingoGame(wordToGuess,username);
        gameRepository.save(lingoGame);
        return lingoGame.showProgress();
    }
    public LingoGame getGame(int id,String username) {
       LingoGame lingoGame = gameRepository.findLingoGameById(id).orElseThrow(() -> new ResourceNotFoundException("Game not found"));
       if(!lingoGame.getPlayer().equals(username)){
           throw new CustomException("Player does not exist on this id");

       }
        return lingoGame;

    }


    public Progress startNewRound(int id, String username) {
        LingoGame lingoGame = gameRepository.findLingoGameById(id).orElseThrow(() -> new ResourceNotFoundException("Game not found"));
        String wordToGUess = wordService.provideRandomWord(lingoGame.provideNextWordLength());
        lingoGame.startNewRound(wordToGUess);

        if(!lingoGame.getPlayer().equals(username)){
            throw new CustomException("Player does not exist on this id");
        }

        gameRepository.save(lingoGame);
        return lingoGame.showProgress();
    }


    public Progress guess(int id, String attempt, String username) {
        LingoGame lingoGame = gameRepository.findLingoGameById(id).orElseThrow(() -> new ResourceNotFoundException("Game not found"));

        if(!lingoGame.getPlayer().equals(username)){
            throw new CustomException("Player does not exist on this id");
        }

        lingoGame.guess(attempt);
        gameRepository.save(lingoGame);

        return lingoGame.showProgress();
    }


}
