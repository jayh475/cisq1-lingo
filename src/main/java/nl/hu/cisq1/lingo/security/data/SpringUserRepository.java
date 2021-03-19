package nl.hu.cisq1.lingo.security.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringUserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
