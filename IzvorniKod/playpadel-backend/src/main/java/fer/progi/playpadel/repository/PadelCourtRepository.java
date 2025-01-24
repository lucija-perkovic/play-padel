package fer.progi.playpadel.repository;

import fer.progi.playpadel.model.PadelCourt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PadelCourtRepository extends JpaRepository<PadelCourt, Long> {
}
