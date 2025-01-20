package fer.progi.playpadel.service.command;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.Date;

public class CreateBookingCommand {
    @JsonProperty("bookingUserId")
    private Long bookingUserId;
    @JsonProperty("startBookingTime")
    private Timestamp startBookingTime;
    @JsonProperty("endBookingTime")
    private Timestamp endBookingTime;

    public Long getBookingUserId() {
        return bookingUserId;
    }

    public void setBookingUserId(Long bookingUserId) {
        this.bookingUserId = bookingUserId;
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
}
