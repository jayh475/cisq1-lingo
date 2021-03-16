package nl.hu.cisq1.lingo.trainer.data;

import nl.hu.cisq1.lingo.trainer.domain.LingoGame;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringGameRepository extends JpaRepository<LingoGame,Long> {
    Optional<LingoGame> findLingoGamesById(int id);

}
