package fer.progi.playpadel.service.impl;

import fer.progi.playpadel.exception.InvalidCreateBookingException;
import fer.progi.playpadel.exception.InvalidLoginException;
import fer.progi.playpadel.model.PadelCourt;
import fer.progi.playpadel.model.PadelCourtBooking;
import fer.progi.playpadel.model.PlayPadelUser;
import fer.progi.playpadel.repository.PadelCourtBookingRepository;
import fer.progi.playpadel.repository.PadelCourtRepository;
import fer.progi.playpadel.repository.PlayPadelRepository;
import fer.progi.playpadel.service.CourtService;
import fer.progi.playpadel.service.command.CreateBookingCommand;
import fer.progi.playpadel.service.command.CreateCourtCommand;
import fer.progi.playpadel.service.dto.BookingDto;
import fer.progi.playpadel.service.dto.CourtDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class CourtServiceImpl implements CourtService {
    private final PadelCourtRepository padelCourtRepository;
    private final PadelCourtBookingRepository padelCourtBookingRepository;
    private final PlayPadelRepository playPadelUserRepository;

    @Autowired
    public CourtServiceImpl(PadelCourtRepository padelCourtRepository, PadelCourtBookingRepository padelCourtBookingRepository, PlayPadelRepository playPadelUserRepository) {
        this.padelCourtRepository = padelCourtRepository;
        this.padelCourtBookingRepository = padelCourtBookingRepository;
        this.playPadelUserRepository = playPadelUserRepository;
    }

    @Override
    public CourtDto createCourt(CreateCourtCommand command, Long userId) {
        final Optional<PlayPadelUser> playPadelUser = playPadelUserRepository.findById(userId);
        if (playPadelUser.isPresent()) {
            final PadelCourt padelCourt = padelCourtRepository.save(new PadelCourt(
                    command.getLocation(),
                    command.getCourtType(),
                    command.getOpeningTime(),
                    command.getClosingTime()
            ));
            final List<PadelCourt> courtList = playPadelUser.get().getPadelCourtList();
            courtList.add(padelCourt);
            playPadelUserRepository.save(playPadelUser.get());
            return new CourtDto(padelCourt.getId(), padelCourt.getLocation(), padelCourt.getCourtType());
        }

        throw new InvalidLoginException(); // todo zamijeniti s custom cannot create court exception

    }

    @Override
    public void createBooking(CreateBookingCommand command, Long courtId) {
        final Optional<PadelCourt> optionalPadelCourt = padelCourtRepository.findById(courtId);
        final Optional<PlayPadelUser> padelUser = playPadelUserRepository.findById(command.getBookingUserId());
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
        if (padelCourt.isPresent()) {
            final PadelCourt court = padelCourt.get();
            if (command.getLocation() != null) {
                court.setLocation(command.getLocation());
            }
            if (command.getClosingTime() != null) {
                court.setClosingTime(command.getClosingTime());
            }
            if (command.getOpeningTime() != null) {
                court.setOpeningTime(command.getOpeningTime());
            }
            if (command.getCourtType() != null) {
                court.setCourtType(command.getCourtType());
            }
            padelCourtRepository.save(court);
        }
    }

    @Override
    public List<CourtDto> getAllCourts() {
        return padelCourtRepository.findAll().stream().map(padelCourt -> new CourtDto(padelCourt.getId(), padelCourt.getLocation(), padelCourt.getCourtType())).toList();
    }

    @Override
    public List<BookingDto> getCourtReservations(Long courtId) {
        final Optional<PadelCourt> padelCourt = padelCourtRepository.findById(courtId);
        if (padelCourt.isPresent()) {
            final PadelCourt court = padelCourt.get();
            final List<PadelCourtBooking> padelCourtBookingList = court.getPadelCourtBookingList();
            return padelCourtBookingList.stream().map(padelCourtBooking -> new BookingDto(padelCourtBooking.getId(), padelCourtBooking.getStartBookingTime(), padelCourtBooking.getEndBookingTime(), padelCourtBooking.getBookingUser().getId())).toList();
        }
        throw new InvalidLoginException(); // todo zamijeniti s ne postoji court exception
    }

    @Override
    public List<CourtDto> getCourtsByUser(Long userId) {
        final Optional<PlayPadelUser> optionalPlayPadelUser = playPadelUserRepository.findById(userId);
        if (optionalPlayPadelUser.isPresent()) {
            final PlayPadelUser user = optionalPlayPadelUser.get();
            return user.getPadelCourtList().stream().map(padelCourt -> new CourtDto(
                    padelCourt.getId(), padelCourt.getLocation(), padelCourt.getCourtType()
            )).toList();
        } else {
            throw new InvalidLoginException(); // todo
        }
    }

    public boolean isBookingSlotAvailable(List<PadelCourtBooking> padelCourtBookingList, Timestamp commandStart, Timestamp commandEnd) {
        LocalDateTime commandStartTime = commandStart.toLocalDateTime();
        LocalDateTime commandEndTime = commandEnd.toLocalDateTime();

        for (PadelCourtBooking booking : padelCourtBookingList) {
            LocalDateTime startBookingTime = booking.getStartBookingTime().toLocalDateTime();
            LocalDateTime endBookingTime = booking.getEndBookingTime().toLocalDateTime();


            if (!(commandEndTime.isBefore(startBookingTime) || commandStartTime.isAfter(endBookingTime))) {
                return false;
            }
        }
        return true;
    }
}
