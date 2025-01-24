import React, { useState } from 'react';
import api from '../utils/axios';
import { useNavigate } from 'react-router-dom';

interface CreateCourtFormProps {
    onCourtCreated: () => void;
  }

const CreatePadelCourt = ({ onCourtCreated }: CreateCourtFormProps) => {
  const [location, setLocation] = useState('');
  const [courtType, setCourtType] = useState('');
  const [openingTime, setOpeningTime] = useState('');
  const [closingTime, setClosingTime] = useState('');
  const [error, setError] = useState(null);

  const handleSubmit = async (e: any) => {
    e.preventDefault();
    const userId = localStorage.getItem("userId");
    if (!userId) {
      return;
    }
    const openingTimeMs = new Date(openingTime).getTime();
    const closingTimeMs = new Date(closingTime).getTime();

    const payload = {
      location,
      courtType: courtType.toUpperCase(),
      openingTime: openingTimeMs,
      closingTime: closingTimeMs,
    };

    try {
      const response = await api.post(`/court/${userId}`, payload);

      if (response.status === 200 || response.status === 201) {
        setError(null);
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

        {}
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

        {}
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

        <button type="submit" className="btn btn-danger">
          Create Court
        </button>
      </form>
    </div>
  );
};

export default CreatePadelCourt;