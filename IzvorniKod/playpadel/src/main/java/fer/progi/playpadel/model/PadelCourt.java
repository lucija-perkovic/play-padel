package fer.progi.playpadel.model;

import fer.progi.playpadel.enumeration.CourtType;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "padel_court")
public class PadelCourt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "location")
    private String location;
    @Column(name = "court_type")
    @Enumerated(EnumType.STRING)
    private CourtType courtType;
    @Column(name = "opening_time")
    private Timestamp openingTime;
    @Column(name = "closing_time")
    private Timestamp closingTime;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "padel_court_booking_id", foreignKey = @ForeignKey(name = "fk_padel_court_booking_id"))
    private List<PadelCourtBooking> padelCourtBookingList = new ArrayList<>();

    public PadelCourt() {
    }

    public PadelCourt(String location, CourtType courtType, Timestamp openingTime, Timestamp closingTime) {
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

    public List<PadelCourtBooking> getPadelCourtBookingList() {
        return padelCourtBookingList;
    }

    public void setPadelCourtBookingList(List<PadelCourtBooking> padelCourtBookingList) {
        this.padelCourtBookingList = padelCourtBookingList;
    }
}
