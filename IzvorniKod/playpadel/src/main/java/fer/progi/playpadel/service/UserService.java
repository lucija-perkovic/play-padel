package fer.progi.playpadel.service;

import fer.progi.playpadel.service.command.UserLoginCommand;
import fer.progi.playpadel.service.command.UserRegisterCommand;
import fer.progi.playpadel.service.dto.LoginDto;
import fer.progi.playpadel.service.dto.UserDto;

import java.util.List;

public interface UserService {
    LoginDto login(UserLoginCommand command);

    void register(UserRegisterCommand command);

    void delete(Long id);

    List<UserDto> getAllUsers();
}
