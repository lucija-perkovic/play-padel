//package fer.progi.playpadel;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import fer.progi.playpadel.enumeration.CourtType;
//import fer.progi.playpadel.enumeration.UserType;
//import fer.progi.playpadel.model.PadelCourt;
//import fer.progi.playpadel.model.PadelCourtBooking;
//import fer.progi.playpadel.model.PlayPadelUser;
//import fer.progi.playpadel.repository.PadelCourtBookingRepository;
//import fer.progi.playpadel.repository.PadelCourtRepository;
//import fer.progi.playpadel.repository.PlayPadelRepository;
//import fer.progi.playpadel.service.command.CreateBookingCommand;
//import fer.progi.playpadel.service.command.CreateCourtCommand;
//import fer.progi.playpadel.service.command.UserRegisterCommand;
//import fer.progi.playpadel.service.impl.CourtServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class PlayPadelServiceTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private UserRegisterCommand validCommand;
//
//    @Mock
//    private PadelCourtRepository padelCourtRepository;
//
//    @Mock
//    private PadelCourtBookingRepository padelCourtBookingRepository;
//
//    @Mock
//    private CourtServiceImpl courtService;
//
//    @Mock
//    private PlayPadelRepository playPadelRepository;
//
//    @BeforeEach
//    public void setup() {
//        validCommand = new UserRegisterCommand();
//        validCommand.setUsername("user123");
//        validCommand.setPassword("Password123");
//        validCommand.setUserType(UserType.PLAYER);
//    }
//
//    @Test
//    public void shouldRegisterUserSuccessfully() throws Exception {
//        String jsonRequest = objectMapper.writeValueAsString(validCommand);
//
//        ResultActions result = mockMvc.perform(post("/user/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonRequest));
//
//        result.andExpect(status().isOk());
//    }
//
//    @Test
//    public void shouldReturnBadRequestForMissingUsername() throws Exception {
//        validCommand.setUsername(null);
//
//        String jsonRequest = objectMapper.writeValueAsString(validCommand);
//
//        ResultActions result = mockMvc.perform(post("/user/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonRequest));
//
//        result.andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void shouldReturnBadRequestForInvalidUserType() throws Exception {
//        validCommand.setUserType(null);
//
//        String jsonRequest = objectMapper.writeValueAsString(validCommand);
//
//        ResultActions result = mockMvc.perform(post("/user/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonRequest));
//
//        result.andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void shouldReturnBadRequestForInvalidPhoneNumber() throws Exception {
//        validCommand.setContactNumber("12345");
//
//        String jsonRequest = objectMapper.writeValueAsString(validCommand);
//
//        ResultActions result = mockMvc.perform(post("/user/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonRequest));
//
//        result.andExpect(status().isBadRequest());
//    }
//
//
//    @Test
//    public void shouldCreateCourtSuccessfully() throws Exception {
//        CreateCourtCommand validCommand = new CreateCourtCommand(
//                "Location 1",
//                CourtType.INDOOR,
//                Timestamp.valueOf("2025-01-01 08:00:00"),
//                Timestamp.valueOf("2025-01-01 22:00:00")
//        );
//
//        String jsonRequest = objectMapper.writeValueAsString(validCommand);
//
//        ResultActions result = mockMvc.perform(post("/court/{userId}", 1L)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonRequest));
//
//        result.andExpect(status().isOk());
//    }
//
//    @Test
//    public void shouldDeleteCourtSuccessfully() throws Exception {
//        Long courtId = 1L;
//
//        doNothing().when(padelCourtRepository).deleteById(courtId);
//
//        ResultActions result = mockMvc.perform(delete("/court/{courtId}", courtId));
//
//        result.andExpect(status().isOk());
//    }
//
//    @Test
//    public void shouldDeleteBookingSuccessfully() throws Exception {
//        Long bookingId = 1L;
//
//        doNothing().when(padelCourtBookingRepository).deleteById(bookingId);
//
//        ResultActions result = mockMvc.perform(delete("/court/booking/{bookingId}", bookingId));
//
//        result.andExpect(status().isOk());
//    }
//
//    @Test
//    public void shouldUpdateCourtSuccessfully() throws Exception {
//        CreateCourtCommand validUpdateCommand = new CreateCourtCommand(
//                "New Location",
//                CourtType.OUTDOOR,
//                Timestamp.valueOf("2025-01-01 08:00:00"),
//                Timestamp.valueOf("2025-01-01 20:00:00")
//        );
//
//        String jsonRequest = objectMapper.writeValueAsString(validUpdateCommand);
//
//        ResultActions result = mockMvc.perform(put("/court/{courtId}", 1L)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonRequest));
//
//        result.andExpect(status().isOk());
//    }
//
//    @Test
//    public void shouldReturnBadRequestWhenBookingTimesOverlap() throws Exception {
//        CreateBookingCommand command = new CreateBookingCommand(
//                1L,  // bookingUserId
//                Timestamp.valueOf("2025-01-01 10:30:00"),
//                Timestamp.valueOf("2025-01-01 11:30:00")
//        );
//        PadelCourt padelCourt = new PadelCourt("Location", CourtType.OUTDOOR, Timestamp.valueOf("2025-01-01 08:00:00"), Timestamp.valueOf("2025-01-01 22:00:00"));
//        List<PadelCourtBooking> existingBookings = new ArrayList<>();
//        PadelCourtBooking existingBooking = new PadelCourtBooking(
//                Timestamp.valueOf("2025-01-01 10:00:00"),
//                Timestamp.valueOf("2025-01-01 11:00:00"),
//                new PlayPadelUser("username", "password", UserType.PLAYER)
//        );
//        existingBookings.add(existingBooking);
//        padelCourt.setPadelCourtBookingList(existingBookings);
//
//        when(padelCourtRepository.findById(1L)).thenReturn(Optional.of(padelCourt));
//        when(playPadelRepository.findById(1L)).thenReturn(Optional.of(new PlayPadelUser("username", "password", UserType.PLAYER)));
//
//        when(courtService.isBookingSlotAvailable(anyList(), any(Timestamp.class), any(Timestamp.class))).thenReturn(false);
//
//        String jsonRequest = objectMapper.writeValueAsString(command);
//
//        ResultActions result = mockMvc.perform(put("/court/booking/{courtId}", 1L)  // Correct URL format
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonRequest));
//
//        result.andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void shouldReturnOkWhenBookingTimesDoNotOverlap() throws Exception {
//        CreateBookingCommand command = new CreateBookingCommand(
//                1L,
//                Timestamp.valueOf("2025-01-01 11:30:00"),
//                Timestamp.valueOf("2025-01-01 12:30:00")
//        );
//
//        PadelCourt padelCourt = new PadelCourt("Location", CourtType.OUTDOOR, Timestamp.valueOf("2025-01-01 08:00:00"), Timestamp.valueOf("2025-01-01 22:00:00"));
//        List<PadelCourtBooking> existingBookings = new ArrayList<>();
//        padelCourt.setPadelCourtBookingList(existingBookings);
//
//        when(padelCourtRepository.findById(1L)).thenReturn(Optional.of(padelCourt));
//        when(playPadelRepository.findById(1L)).thenReturn(Optional.of(new PlayPadelUser("username", "password", UserType.PLAYER)));
//
//        when(courtService.isBookingSlotAvailable(anyList(), any(Timestamp.class), any(Timestamp.class))).thenReturn(true);
//
//        String jsonRequest = objectMapper.writeValueAsString(command);
//
//        ResultActions result = mockMvc.perform(put("/court/booking/{courtId}", 1L)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonRequest));
//
//        result.andExpect(status().isOk());
//    }
//}
