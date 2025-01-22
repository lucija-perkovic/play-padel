package fer.progi.playpadel.security;

import fer.progi.playpadel.exception.InvalidLoginException;
import fer.progi.playpadel.model.PlayPadelUser;
import fer.progi.playpadel.repository.PlayPadelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PlayPadelRepository playPadelRepository;

    @Autowired
    public CustomUserDetailsService(PlayPadelRepository playPadelRepository) {
        this.playPadelRepository = playPadelRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        PlayPadelUser user = playPadelRepository.findPlayPadelUserByUsername(username)
                .orElseThrow(InvalidLoginException::new);

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getUserType().name())  // Customize based on your roles/permissions
                .build();
    }
}