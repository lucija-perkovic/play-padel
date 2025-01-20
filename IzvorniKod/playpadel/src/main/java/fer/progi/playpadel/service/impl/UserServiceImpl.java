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
import fer.progi.playpadel.service.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final PlayPadelRepository playPadelRepository;

    private static final String PHONE_NUMBER_REGEX = "^\\d{10}$"; // Adjust the pattern as needed
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);

    @Autowired
    public UserServiceImpl(PlayPadelRepository playPadelRepository) {
        this.playPadelRepository = playPadelRepository;
    }

    @Override
    public void login(UserLoginCommand command) {
        final Optional<PlayPadelUser> playPadelUser = playPadelRepository.findPlayPadelUserByUsernameAndPassword(command.getUsername(), command.getPassword());
        if (playPadelUser.isEmpty()) {
            LOGGER.info("Invalid login credentials for username {} and password {}", command.getUsername(), command.getPassword());
            throw new InvalidLoginException();
        } else {
            LOGGER.info("Successfully logged in for username {} and password {}", command.getUsername(), command.getPassword());
        }
    }

    @Override
    public void register(UserRegisterCommand command) {
        final Optional<PlayPadelUser> existingPlayPadelUser = playPadelRepository.findPlayPadelUserByUsernameAndPassword(command.getUsername(), command.getPassword());
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
                    command.getPassword(),
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
