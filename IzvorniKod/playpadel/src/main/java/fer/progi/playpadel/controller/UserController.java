package fer.progi.playpadel.controller;

import fer.progi.playpadel.repository.PlayPadelRepository;
import fer.progi.playpadel.service.UserService;
import fer.progi.playpadel.service.command.UserLoginCommand;
import fer.progi.playpadel.service.command.UserRegisterCommand;
import fer.progi.playpadel.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginCommand command) {
        try {
            userService.login(command);
            return ResponseEntity.ok(null); // 200
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(null); // 400
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserRegisterCommand command) {
        try {
            userService.register(command);
            return ResponseEntity.ok(null); // 200
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(null); // 400
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(name = "id") Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok(null); // 200
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(null); // 400
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllUsers() {
        try {
            return ResponseEntity.ok(userService.getAllUsers()); // 200
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(null); // 400
        }
    }


}
