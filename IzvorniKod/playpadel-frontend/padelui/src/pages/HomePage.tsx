import React, { useEffect, useState } from 'react';
import Navbar from '../components/Navbar';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import api from '../utils/axios';

interface Event {
  id: number;
  title: string;
  start: Date;
  end: Date;
  reserved: boolean; // true if reserved, false if available
  courtId?: number; // Court ID associated with the event
}

interface Court {
  id: number;
  location: string;
  courtType: string;
}

interface Reservation {
  id: number;
  start: string; // ISO 8601 string format
  end: string;   // ISO 8601 string format
  bookingUserId: number; // Added bookingUserId to the reservation
}

function HomePage() {
  const [events, setEvents] = useState<Event[]>([]);
  const [courts, setCourts] = useState<Court[]>([]); // State for courts
  const [reservations, setReservations] = useState<Reservation[]>([]); // State for reservations
  const [loading, setLoading] = useState<boolean>(true);
  const [selectedDate, setSelectedDate] = useState<Date>(new Date());
  const [selectedCourt, setSelectedCourt] = useState<number | null>(null); // State for selected court
  const [bookingTime, setBookingTime] = useState<{ start: Date | null; end: Date | null }>({
    start: null,
    end: null,
  });

  // Fetch events and courts from the backend API
  useEffect(() => {
    const fetchCourts = async () => {
      try {
        const response = await api.get('/court/all');
        setCourts(response.data);
        setLoading(false);
      } catch (error) {
        console.error('Error fetching courts:', error);
      }
    };

    fetchCourts();
  }, []);

  const fetchReservations = async () => {
    try {
      const response = await api.get(`/court/${selectedCourt}/reservations`);
      setReservations(response.data);
    } catch (error) {
      console.error('Error fetching reservations:', error);
    }
  };

  // Fetch reservations when court is selected
  useEffect(() => {
    if (selectedCourt !== null) {
      fetchReservations();
    }
  }, [selectedCourt]);

  // Handle booking a court
  const handleBooking = async () => {
    if (!bookingTime.start || !bookingTime.end || selectedCourt === null) {
      alert('Please select a valid time range for the booking.');
      return;
    }

    const userId = localStorage.getItem('userId');
    if (!userId) {
      alert('User is not authenticated.');
      return;
    }

    try {
      // Prepare payload
      const payload = {
        bookingUserId: userId,
        startBookingTime: new Date(bookingTime.start).getTime(),
        endBookingTime: new Date(bookingTime.end).getTime(),
      };

      // Send PUT request to book the court
      const response = await api.put(`/court/booking/${selectedCourt}`, payload);

      if (response.status === 200) {
        alert('Booking successful!');
        setReservations([]); 
        await fetchReservations();
        setSelectedDate(new Date(bookingTime.start)); // Update the calendar to the selected booking date
      } else {
        alert('Failed to book the court.');
      }
    } catch (error) {
      console.error('Error booking the court:', error);
      alert('An error occurred while booking the court.');
    }
  };

  // Delete a reservation
  const handleDeleteReservation = async (bookingId: number) => {
    const userId = localStorage.getItem('userId');
    if (!userId) {
      alert('User is not authenticated.');
      return;
    }

    try {
      const response = await api.delete(`/court/booking/${bookingId}`);
      if (response.status === 200) {
        alert('Booking deleted successfully!');
        setReservations(reservations.filter((res) => res.id !== bookingId)); // Remove the deleted booking from the state
      } else {
        alert('Failed to delete the booking.');
      }
    } catch (error) {
      console.error('Error deleting the booking:', error);
      alert('An error occurred while deleting the booking.');
    }
  };

  // Filter events based on the selected date
  const filteredEvents = events.filter(
    (event) =>
      new Date(event.start).toDateString() === selectedDate.toDateString()
  );

  // Filter reservations based on the selected date
  const filteredReservations = reservations.filter((reservation) => {
    const reservationStartDate = new Date(reservation.start);
    return reservationStartDate.toDateString() === selectedDate.toDateString();
  });

  // Function to format selectedDate to fit datetime-local format (yyyy-MM-ddThh:mm)
  const formatDateToLocalInput = (date: Date) => {
    const yyyy = date.getFullYear();
    const mm = String(date.getMonth() + 1).padStart(2, '0');
    const dd = String(date.getDate()).padStart(2, '0');
    const hh = String(date.getHours()).padStart(2, '0');
    const min = String(date.getMinutes()).padStart(2, '0');
    return `${yyyy}-${mm}-${dd}T${hh}:${min}`;
  };

  return (
    <div className="d-flex flex-column min-vh-100">
      {/* Navbar remains at the top */}
      <Navbar setIsLoginPage={() => false} />

      {/* Content centered in the remaining space */}
      <div className="container d-flex justify-content-center align-items-center flex-grow-1">
        <div className="row w-100 p-4 justify-content-center">
          <div className="col-12 col-md-12 col-lg-10 bg-white rounded shadow-lg p-4">
            {loading ? (
              <p>Loading calendar...</p>
            ) : (
              <div>
                {/* Dropdown to select a court */}
                <div className="mb-4">
                  <select
                    className="form-control mb-2"
                    value={selectedCourt ?? ''}
                    onChange={(e) => setSelectedCourt(Number(e.target.value))}
                  >
                    <option value="">Select Court</option>
                    {courts.map((court) => (
                      <option key={court.id} value={court.id}>
                        {court.location} ({court.courtType})
                      </option>
                    ))}
                  </select>
                </div>

                {/* Calendar */}
                <div className="mb-4">
                  <Calendar
                    onChange={(date) => setSelectedDate(date as Date)}
                    value={selectedDate}
                    className="react-calendar"
                  />
                </div>

                <div>
                  <h3 className="text-center mb-3">
                    Appointments for {selectedDate.toDateString()}
                  </h3>
                  {filteredEvents.length > 0 ? (
                    <ul className="list-group">
                      {filteredEvents.map((event) => (
                        <li key={event.id} className="list-group-item d-flex justify-content-between align-items-center">
                          <span
                            style={{
                              color: event.reserved ? 'red' : 'green',
                            }}
                          >
                            {event.title} - {event.reserved ? 'Reserved' : 'Available'}
                          </span>
                        </li>
                      ))}
                    </ul>
                  ) : (
                    <p>No appointments for this day.</p>
                  )}
                </div>

                {/* Show taken appointments for the selected day */}
                <div className="mt-4">
                  <h4>Reserved Appointments:</h4>
                  {filteredReservations.length > 0 ? (
                    <ul className="list-group">
                      {filteredReservations.map((reservation) => (
                        <li key={reservation.id} className="list-group-item d-flex justify-content-between align-items-center">
                          <span>
                            {`From: ${new Date(reservation.start).toLocaleTimeString()} To: ${new Date(reservation.end).toLocaleTimeString()}`}
                          </span>
                          {reservation.bookingUserId === Number(localStorage.getItem('userId')) && (
                            <button
                              className="btn btn-danger btn-sm"
                              onClick={() => handleDeleteReservation(reservation.id)}
                            >
                              Delete
                            </button>
                          )}
                        </li>
                      ))}
                    </ul>
                  ) : (
                    <p>No reserved appointments for this day.</p>
                  )}
                </div>

                {/* Booking form */}
                <div className="mt-4">
                  <h4>Book Court</h4>
                  <div className="mb-2">
                    <label>Start Time:</label>
                    <input
                      type="datetime-local"
                      className="form-control"
                      value={bookingTime.start ? formatDateToLocalInput(bookingTime.start) : formatDateToLocalInput(selectedDate)}
                      onChange={(e) => setBookingTime({ ...bookingTime, start: new Date(e.target.value) })}
                    />
                  </div>
                  <div className="mb-2">
                    <label>End Time:</label>
                    <input
                      type="datetime-local"
                      className="form-control"
                      value={bookingTime.end ? formatDateToLocalInput(bookingTime.end) : formatDateToLocalInput(selectedDate)}
                      onChange={(e) => setBookingTime({ ...bookingTime, end: new Date(e.target.value) })}
                    />
                  </div>
                  <button className="btn btn-danger w-100" onClick={handleBooking}>
                    Book Court
                  </button>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default HomePage;
