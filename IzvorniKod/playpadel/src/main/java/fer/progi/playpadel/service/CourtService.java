package fer.progi.playpadel.service;

import fer.progi.playpadel.service.command.CreateBookingCommand;
import fer.progi.playpadel.service.command.CreateCourtCommand;
import fer.progi.playpadel.service.dto.BookingDto;
import fer.progi.playpadel.service.dto.CourtDto;

import java.util.List;

public interface CourtService {
    CourtDto createCourt(CreateCourtCommand command, Long userId);

    void createBooking(CreateBookingCommand command, Long courtId);

    void deleteBooking(Long bookingId);

    void deleteCourt(Long courtId);

    void updateCourt(CreateCourtCommand command, Long courtId);

    List<CourtDto> getAllCourts();

    List<BookingDto> getCourtReservations(Long courtId);

    List<CourtDto>  getCourtsByUser(Long userId);
}
