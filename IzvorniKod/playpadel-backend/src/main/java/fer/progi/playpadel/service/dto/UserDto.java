package fer.progi.playpadel.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fer.progi.playpadel.enumeration.UserType;

public class UserDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("userType")
    private UserType userType;
    @JsonProperty("contactNumber")
    private String contactNumber;
    @JsonProperty("address")
    private String address;
    @JsonProperty("padelHallName")
    private String padelHallName;

    public UserDto(Long id, String firstName, String lastName, UserType userType, String contactNumber, String address, String padelHallName) {
        this.id = id;
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
}
