package fer.progi.playpadel.service.dto;

import java.sql.Timestamp;

public class BookingDto {
    private Long id;
    private Timestamp start;
    private Timestamp end;
    private Long bookingUserId;

    public BookingDto(Long id, Timestamp start, Timestamp end, Long bookingUserId) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.bookingUserId = bookingUserId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public Long getBookingUserId() {
        return bookingUserId;
    }

    public void setBookingUserId(Long bookingUserId) {
        this.bookingUserId = bookingUserId;
    }
}
