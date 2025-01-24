package fer.progi.playpadel.service.impl;

import fer.progi.playpadel.enumeration.UserType;
import fer.progi.playpadel.exception.InvalidContactNumberException;
import fer.progi.playpadel.exception.InvalidLoginException;
import fer.progi.playpadel.exception.UserAlreadyRegisteredException;
import fer.progi.playpadel.model.PlayPadelUser;
import fer.progi.playpadel.repository.PlayPadelRepository;
import fer.progi.playpadel.service.UserService;
import fer.progi.playpadel.service.command.UserLoginCommand;
import fer.progi.playpadel.service.command.UserRegisterCommand;
import fer.progi.playpadel.service.dto.LoginDto;
import fer.progi.playpadel.service.dto.UserDto;
import fer.progi.playpadel.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final PlayPadelRepository playPadelRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private static final String PHONE_NUMBER_REGEX = "^\\d{10}$";
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);

    @Autowired
    public UserServiceImpl(PlayPadelRepository playPadelRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.playPadelRepository = playPadelRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginDto login(UserLoginCommand command) {
        if (command.getUsername() == null || command.getPassword() == null) {
            throw new InvalidLoginException();
        }
        final Optional<PlayPadelUser> playPadelUser = playPadelRepository.findPlayPadelUserByUsername(command.getUsername());
        if (playPadelUser.isEmpty()) {
            LOGGER.info("Invalid login credentials for username {} and password {}", command.getUsername(), command.getPassword());
        } else {
            if (passwordEncoder.matches(command.getPassword(), playPadelUser.get().getPassword())) {
                String token = jwtUtil.generateToken(playPadelUser.get().getUsername());
                LOGGER.info("Successfully logged in for username {} and password {}", command.getUsername(), command.getPassword());
                final PlayPadelUser user = playPadelUser.get();
                return new LoginDto(token, new UserDto(user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getUserType(),
                        user.getContactNumber(),
                        user.getAddress(),
                        user.getPadelHallName()));
            }

        }
        throw new InvalidLoginException();
    }


    @Override
    public void register(UserRegisterCommand command) {
        if (command.getUsername() == null || command.getPassword() == null || command.getUserType() == null) {
            throw new InvalidLoginException();
        }
        final Optional<PlayPadelUser> existingPlayPadelUser = playPadelRepository.findPlayPadelUserByUsername(command.getUsername());
        if (existingPlayPadelUser.isPresent()) {
            LOGGER.info("User is already registered");
            throw new UserAlreadyRegisteredException();
        } else {
            if (command.getContactNumber() != null) {
                if (!isValidPhoneNumber(command.getContactNumber())) {
                    LOGGER.info("Invalid contact number {}", command.getContactNumber());
                    throw new InvalidContactNumberException();
                }
            }
            LOGGER.info("Saving user {}", command.getUsername());
            final PlayPadelUser playPadelUser = new PlayPadelUser(
                    command.getUsername(),
                    passwordEncoder.encode(command.getPassword()),
                    command.getFirstName(),
                    command.getLastName(),
                    command.getUserType(),
                    command.getContactNumber(),
                    command.getAddress(),
                    command.getPadelHallName()
            );
            playPadelRepository.save(playPadelUser);
        }
    }

    @Override
    public void delete(Long id) {
        playPadelRepository.deleteById(id);
    }

    @Override
    public List<UserDto> getAllUsers() {
        final Optional<List<PlayPadelUser>> playPadelUserList = playPadelRepository.findByUserTypeNot(UserType.ADMIN);
        if (playPadelUserList.isPresent()) {
            List<UserDto> playPadelUsers;
            playPadelUsers = playPadelUserList.get().stream().map(playPadelUser -> new UserDto(
                    playPadelUser.getId(),
                    playPadelUser.getFirstName(),
                    playPadelUser.getLastName(),
                    playPadelUser.getUserType(),
                    playPadelUser.getContactNumber(),
                    playPadelUser.getAddress(),
                    playPadelUser.getPadelHallName()
            )).toList();
            return playPadelUsers;
        } else {
            return new ArrayList<>();
        }

    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        return PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches();
    }

}
