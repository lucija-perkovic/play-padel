package fer.progi.playpadel.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "padel_court_booking")
public class PadelCourtBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "start_booking_time")
    private Timestamp startBookingTime;
    @Column(name = "end_booking_time")
    private Timestamp endBookingTime;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "booking_user_id")
    private PlayPadelUser bookingUser;

    public PadelCourtBooking() {
    }

    public PadelCourtBooking(Timestamp startBookingTime, Timestamp endBookingTime, PlayPadelUser bookingUser) {
        this.startBookingTime = startBookingTime;
        this.endBookingTime = endBookingTime;
        this.bookingUser = bookingUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getStartBookingTime() {
        return startBookingTime;
    }

    public void setStartBookingTime(Timestamp startBookingTime) {
        this.startBookingTime = startBookingTime;
    }

    public Timestamp getEndBookingTime() {
        return endBookingTime;
    }

    public void setEndBookingTime(Timestamp endBookingTime) {
        this.endBookingTime = endBookingTime;
    }

    public PlayPadelUser getBookingUser() {
        return bookingUser;
    }

    public void setBookingUser(PlayPadelUser bookingUser) {
        this.bookingUser = bookingUser;
    }
}
