package fer.progi.playpadel.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fer.progi.playpadel.enumeration.UserType;

public class UserDto {
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

    public UserDto(String firstName, String lastName, UserType userType, String contactNumber, String address, String padelHallName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.contactNumber = contactNumber;
        this.address = address;
        this.padelHallName = padelHallName;
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
