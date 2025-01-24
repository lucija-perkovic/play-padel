package fer.progi.playpadel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import fer.progi.playpadel.enumeration.UserType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "play_padel_user")
public class PlayPadelUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "play_padel_username")
    private String username;
    @Column(name ="play_padel_password")
    private String password;
    @Column(name ="first_name")
    private String firstName;
    @Column(name ="last_name")
    private String lastName;
    @Column(name ="user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column(name ="contact_number")
    private String contactNumber;
    @Column(name ="address")
    private String address;
    @Column(name ="padel_hall_name")
    private String padelHallName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "padel_court_id", foreignKey = @ForeignKey(name = "fk_padel_court_id"))
    private List<PadelCourt> padelCourtList = new ArrayList<>();

    public PlayPadelUser() {
    }

    public PlayPadelUser(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public PlayPadelUser(String username, String password, String firstName, String lastName, UserType userType, String contactNumber, String address, String padelHallName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.contactNumber = contactNumber;
        this.address = address;
        this.padelHallName = padelHallName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPadelHallName() {
        return padelHallName;
    }

    public void setPadelHallName(String padelHallName) {
        this.padelHallName = padelHallName;
    }

    public List<PadelCourt> getPadelCourtList() {
        return padelCourtList;
    }

    public void setPadelCourtList(List<PadelCourt> padelCourtList) {
        this.padelCourtList = padelCourtList;
    }
}
