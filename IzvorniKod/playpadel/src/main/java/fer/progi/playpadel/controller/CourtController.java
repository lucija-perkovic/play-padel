package fer.progi.playpadel.controller;

import fer.progi.playpadel.service.CourtService;
import fer.progi.playpadel.service.command.CreateBookingCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fer.progi.playpadel.service.command.CreateCourtCommand;

@RestController
@RequestMapping("/court")
public class CourtController {
    private final CourtService courtService;

    @Autowired
    public CourtController(CourtService courtService) {
        this.courtService = courtService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Object> createCourt(@RequestBody CreateCourtCommand command, @PathVariable(name = "userId") Long userId) {
        try {
            courtService.createCourt(command, userId);
            return ResponseEntity.ok(null); // 200
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(null); // 400
        }
    }
    @DeleteMapping("/{courtId}")
    public ResponseEntity<Object> deleteCourt(@PathVariable(name = "courtId") Long courtId) {
        try {
            courtService.deleteCourt(courtId);
            return ResponseEntity.ok(null); // 200
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(null); // 400
        }
    }

    @PutMapping("/booking/{courtId}")
    public ResponseEntity<Object> addBooking(@RequestBody CreateBookingCommand command, @PathVariable(name = "courtId") Long courtId) {
        try {
            courtService.createBooking(command, courtId);
            return ResponseEntity.ok(null); // 200
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(null); // 400
        }
    }

    @DeleteMapping("/booking/{bookingId}")
    public ResponseEntity<Object> deleteBooking(@PathVariable(name = "bookingId") Long bookingId) {
        try {
            courtService.deleteBooking(bookingId);
            return ResponseEntity.ok(null); // 200
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(null); // 400
        }
    }
    @PutMapping("/{courtId}")
    public ResponseEntity<Object> updateCourt(@RequestBody CreateCourtCommand command, @PathVariable(name = "courtId") Long courtId) {
        try {
            courtService.updateCourt(command, courtId);
            return ResponseEntity.ok(null); // 200
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(null); // 400
        }
    }

}
