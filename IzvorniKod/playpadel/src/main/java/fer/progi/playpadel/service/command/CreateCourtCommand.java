package fer.progi.playpadel.service.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import fer.progi.playpadel.enumeration.CourtType;

import java.sql.Timestamp;


public class CreateCourtCommand {
    @JsonProperty("location")
    private String location;
    @JsonProperty("courtType")
    private CourtType courtType;
    @JsonProperty("openingTime")
    private Timestamp openingTime;
    @JsonProperty("closingTime")
    private Timestamp closingTime;

    public CreateCourtCommand(String location, CourtType courtType, Timestamp openingTime, Timestamp closingTime) {
        this.location = location;
        this.courtType = courtType;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public CourtType getCourtType() {
        return courtType;
    }

    public void setCourtType(CourtType courtType) {
        this.courtType = courtType;
    }

    public Timestamp getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Timestamp openingTime) {
        this.openingTime = openingTime;
    }

    public Timestamp getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Timestamp closingTime) {
        this.closingTime = closingTime;
    }
}
