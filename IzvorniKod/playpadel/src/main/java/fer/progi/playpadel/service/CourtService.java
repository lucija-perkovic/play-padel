package fer.progi.playpadel.service;

import fer.progi.playpadel.service.command.CreateBookingCommand;
import fer.progi.playpadel.service.command.CreateCourtCommand;

public interface CourtService {
    void createCourt(CreateCourtCommand command, Long userId);
    void createBooking(CreateBookingCommand command, Long courtId);

    void deleteBooking(Long bookingId);

    void deleteCourt(Long courtId);

    void updateCourt(CreateCourtCommand command, Long courtId);
}
