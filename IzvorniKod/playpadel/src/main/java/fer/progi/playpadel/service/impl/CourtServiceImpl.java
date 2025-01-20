package fer.progi.playpadel.service.impl;

import fer.progi.playpadel.exception.InvalidCreateBookingException;
import fer.progi.playpadel.model.PadelCourt;
import fer.progi.playpadel.model.PadelCourtBooking;
import fer.progi.playpadel.model.PlayPadelUser;
import fer.progi.playpadel.repository.PadelCourtBookingRepository;
import fer.progi.playpadel.repository.PadelCourtRepository;
import fer.progi.playpadel.repository.PlayPadelRepository;
import fer.progi.playpadel.service.CourtService;
import fer.progi.playpadel.service.command.CreateBookingCommand;
import fer.progi.playpadel.service.command.CreateCourtCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class CourtServiceImpl implements CourtService {
    private final PadelCourtRepository padelCourtRepository;
    private final PadelCourtBookingRepository padelCourtBookingRepository;
    private final PlayPadelRepository playPadelRepository;

    @Autowired
    public CourtServiceImpl(PadelCourtRepository padelCourtRepository, PadelCourtBookingRepository padelCourtBookingRepository, PlayPadelRepository playPadelRepository) {
        this.padelCourtRepository = padelCourtRepository;
        this.padelCourtBookingRepository = padelCourtBookingRepository;
        this.playPadelRepository = playPadelRepository;
    }

    @Override
    public void createCourt(CreateCourtCommand command, Long userId) {
        final Optional<PlayPadelUser> playPadelUser = playPadelRepository.findById(userId);
        if (playPadelUser.isPresent()) {
            System.out.println("I AM SAVING TO " + command.getClosingTime());
            final PadelCourt padelCourt = padelCourtRepository.save(new PadelCourt(
                    command.getLocation(),
                    command.getCourtType(),
                    command.getOpeningTime(),
                    command.getClosingTime()
            ));
            final List<PadelCourt> courtList = playPadelUser.get().getPadelCourtList();
            courtList.add(padelCourt);
            playPadelRepository.save(playPadelUser.get());
        }

    }

    @Override
    public void createBooking(CreateBookingCommand command, Long courtId) {
        final Optional<PadelCourt> optionalPadelCourt = padelCourtRepository.findById(courtId);
        final Optional<PlayPadelUser> padelUser = playPadelRepository.findById(command.getBookingUserId());
        if (optionalPadelCourt.isPresent() && padelUser.isPresent()) {
            final PadelCourt padelCourt = optionalPadelCourt.get();
            final List<PadelCourtBooking> padelCourtBookingList = padelCourt.getPadelCourtBookingList();
            final boolean isAvailable = isBookingSlotAvailable(padelCourtBookingList, command.getStartBookingTime(), command.getEndBookingTime());
            if (isAvailable) {
                final PadelCourtBooking padelCourtBooking =
                        padelCourtBookingRepository.save(new PadelCourtBooking(
                                command.getStartBookingTime(),
                                command.getEndBookingTime(),
                                padelUser.get()
                        ));
                padelCourtBookingList.add(padelCourtBooking);
                padelCourt.setPadelCourtBookingList(padelCourtBookingList);
                padelCourtRepository.save(padelCourt);
            } else {
                throw new InvalidCreateBookingException();
            }
        } else {
            throw new InvalidCreateBookingException();
        }

    }

    @Override
    public void deleteBooking(Long bookingId) {
        padelCourtBookingRepository.deleteById(bookingId);
    }

    @Override
    public void deleteCourt(Long courtId) {
        padelCourtRepository.deleteById(courtId);
    }

    @Override
    public void updateCourt(CreateCourtCommand command, Long courtId) {
        final Optional<PadelCourt> padelCourt = padelCourtRepository.findById(courtId);
        if(padelCourt.isPresent()){
            final PadelCourt court = padelCourt.get();
            if(command.getLocation() != null){
                court.setLocation(command.getLocation());
            }
            if(command.getClosingTime() != null){
                court.setClosingTime(command.getClosingTime());
            }
            if(command.getOpeningTime() != null){
                court.setOpeningTime(command.getOpeningTime());
            }
            if(command.getCourtType() != null){
                court.setCourtType(command.getCourtType());
            }
            padelCourtRepository.save(court);
        }
    }

    private boolean isBookingSlotAvailable(List<PadelCourtBooking> padelCourtBookingList, Timestamp commandStart, Timestamp commandEnd) {
        // Extract time from the command start and end
        LocalTime commandStartTime = commandStart.toLocalDateTime().toLocalTime();
        LocalTime commandEndTime = commandEnd.toLocalDateTime().toLocalTime();

        for (PadelCourtBooking booking : padelCourtBookingList) {
            Timestamp startBookingTime = booking.getStartBookingTime();
            Timestamp endBookingTime = booking.getEndBookingTime();

            // Extract time from the booking start and end times
            LocalTime bookingStartTime = startBookingTime.toLocalDateTime().toLocalTime();
            LocalTime bookingEndTime = endBookingTime.toLocalDateTime().toLocalTime();

            // Check if there is an overlap (start or end time is between the command times)
            if (!(commandEndTime.isBefore(bookingStartTime) || commandStartTime.isAfter(bookingEndTime))) {
                // Overlap found
                return false;
            }
        }
        // No overlap found
        return true;
    }
}
