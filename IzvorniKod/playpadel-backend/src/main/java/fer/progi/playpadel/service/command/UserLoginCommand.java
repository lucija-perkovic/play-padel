package fer.progi.playpadel.service.command;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserLoginCommand {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;

    public UserLoginCommand() {
    }

    public UserLoginCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
