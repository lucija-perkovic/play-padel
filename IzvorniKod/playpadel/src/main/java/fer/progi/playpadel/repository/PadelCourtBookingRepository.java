package fer.progi.playpadel.repository;

import fer.progi.playpadel.model.PadelCourtBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PadelCourtBookingRepository extends JpaRepository<PadelCourtBooking, Long> {
}
