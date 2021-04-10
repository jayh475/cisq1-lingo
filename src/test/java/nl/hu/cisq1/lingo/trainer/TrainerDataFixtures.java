package nl.hu.cisq1.lingo.trainer;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.LingoGame;
import org.springframework.boot.CommandLineRunner;

public class TrainerDataFixtures implements CommandLineRunner {
    private final SpringGameRepository springGameRepository;

    public TrainerDataFixtures(SpringGameRepository springGameRepository) {
        this.springGameRepository = springGameRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        springGameRepository.save(new LingoGame("woord","jayh475"));
    }
}
