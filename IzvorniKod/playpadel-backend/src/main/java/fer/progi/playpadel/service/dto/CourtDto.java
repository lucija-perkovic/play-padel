package fer.progi.playpadel.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fer.progi.playpadel.enumeration.CourtType;

public class CourtDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("location")
    private String location;
    @JsonProperty("courtType")
    private CourtType courtType;

    public CourtDto(Long id, String location, CourtType courtType) {
        this.id = id;
        this.location = location;
        this.courtType = courtType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
