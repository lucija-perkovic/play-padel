import React, { useState } from 'react';
import api from '../utils/axios';
import { useNavigate } from 'react-router-dom';

interface CreateCourtFormProps {
    onCourtCreated: () => void; // Callback function to refresh courts
  }

const CreatePadelCourt = ({ onCourtCreated }: CreateCourtFormProps) => {
  const [location, setLocation] = useState('');
  const [courtType, setCourtType] = useState('');
  const [openingTime, setOpeningTime] = useState('');
  const [closingTime, setClosingTime] = useState('');
  const [error, setError] = useState(null);

  const handleSubmit = async (e: any) => {
    e.preventDefault();

    // Get the userId from local storage
    const userId = localStorage.getItem("userId");
    if (!userId) {
      return;
    }

    // Convert opening and closing time to timestamps in milliseconds
    const openingTimeMs = new Date(openingTime).getTime();
    const closingTimeMs = new Date(closingTime).getTime();

    // Prepare the request payload
    const payload = {
      location,
      courtType: courtType.toUpperCase(),
      openingTime: openingTimeMs,
      closingTime: closingTimeMs,
    };

    try {
      // Send POST request to /court/{userId}
      const response = await api.post(`/court/${userId}`, payload);

      if (response.status === 200 || response.status === 201) {
        setError(null);
        // Optionally, clear the form
        setLocation("");
        setCourtType("");
        setOpeningTime("");
        setClosingTime("");

        onCourtCreated();
      }
    } catch (err) {
    }
  };

  return (
    <div className="container mt-5">
      <h2 className="mb-4">Create Padel Court</h2>
      <form onSubmit={handleSubmit}>
        {/* Location Field */}
        <div className="mb-3">
          <label htmlFor="location" className="form-label">
            Location
          </label>
          <input
            type="text"
            id="location"
            className="form-control"
            value={location}
            onChange={(e) => setLocation(e.target.value)}
            required
          />
        </div>

        {/* Court Type Field */}
        <div className="mb-3">
          <label htmlFor="courtType" className="form-label">
            Court Type
          </label>
          <select
            id="courtType"
            className="form-select"
            value={courtType}
            onChange={(e) => setCourtType(e.target.value)}
            required
          >
            <option value="">Select Court Type</option>
            <option value="INDOOR">Indoor</option>
            <option value="OUTDOOR">Outdoor</option>
          </select>
        </div>

        {/* Opening Time Field */}
        <div className="mb-3">
          <label htmlFor="openingTime" className="form-label">
            Opening Time
          </label>
          <input
            type="datetime-local"
            id="openingTime"
            className="form-control"
            value={openingTime}
            onChange={(e) => setOpeningTime(e.target.value)}
            required
          />
        </div>

        {/* Closing Time Field */}
        <div className="mb-3">
          <label htmlFor="closingTime" className="form-label">
            Closing Time
          </label>
          <input
            type="datetime-local"
            id="closingTime"
            className="form-control"
            value={closingTime}
            onChange={(e) => setClosingTime(e.target.value)}
            required
          />
        </div>

        {/* Submit Button */}
        <button type="submit" className="btn btn-danger">
          Create Court
        </button>
      </form>
    </div>
  );
};

export default CreatePadelCourt;