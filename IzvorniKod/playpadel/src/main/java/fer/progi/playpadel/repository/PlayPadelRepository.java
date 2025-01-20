package fer.progi.playpadel.repository;

import fer.progi.playpadel.enumeration.UserType;
import fer.progi.playpadel.model.PlayPadelUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayPadelRepository extends JpaRepository<PlayPadelUser, Long> {
    Optional<PlayPadelUser> findPlayPadelUserByUsernameAndPassword(String username, String password);
    Optional<List<PlayPadelUser>> findByUserTypeNot(UserType userType);
}
