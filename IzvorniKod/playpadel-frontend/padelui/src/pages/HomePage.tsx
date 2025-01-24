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
  reserved: boolean;
  courtId?: number;
}

interface Court {
  id: number;
  location: string;
  courtType: string;
}

interface Reservation {
  id: number;
  start: string;
  end: string;
  bookingUserId: number;
}

function HomePage() {
  const [events, setEvents] = useState<Event[]>([]);
  const [courts, setCourts] = useState<Court[]>([]);
  const [reservations, setReservations] = useState<Reservation[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [selectedDate, setSelectedDate] = useState<Date>(new Date());
  const [selectedCourt, setSelectedCourt] = useState<number | null>(null);
  const [bookingTime, setBookingTime] = useState<{ start: Date | null; end: Date | null }>({
    start: null,
    end: null,
  });


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


  useEffect(() => {
    if (selectedCourt !== null) {
      fetchReservations();
    }
  }, [selectedCourt]);


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

      const payload = {
        bookingUserId: userId,
        startBookingTime: new Date(bookingTime.start).getTime(),
        endBookingTime: new Date(bookingTime.end).getTime(),
      };


      const response = await api.put(`/court/booking/${selectedCourt}`, payload);

      if (response.status === 200) {
        alert('Booking successful!');
        setReservations([]); 
        await fetchReservations();
        setSelectedDate(new Date(bookingTime.start));
      } else {
        alert('Failed to book the court.');
      }
    } catch (error) {
      console.error('Error booking the court:', error);
      alert('An error occurred while booking the court.');
    }
  };


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
        setReservations(reservations.filter((res) => res.id !== bookingId));
      } else {
        alert('Failed to delete the booking.');
      }
    } catch (error) {
      console.error('Error deleting the booking:', error);
      alert('An error occurred while deleting the booking.');
    }
  };

  const filteredEvents = events.filter(
    (event) =>
      new Date(event.start).toDateString() === selectedDate.toDateString()
  );


  const filteredReservations = reservations.filter((reservation) => {
    const reservationStartDate = new Date(reservation.start);
    return reservationStartDate.toDateString() === selectedDate.toDateString();
  });


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
