import React, { useState, useEffect } from "react";
import api from '../utils/axios';
import CreatePadelCourt from "../components/CreateCourt";
import Navbar from '../components/Navbar';

interface Court {
  id: number;
  location: string;
  courtType: string;
}

const CourtsPage = () => {
  const [courts, setCourts] = useState<Court[]>([]);
  const [error, setError] = useState<string | null>(null);

  const fetchCourts = async () => {
    try {
      const userId = localStorage.getItem("userId");
  if (!userId) {
    return;
  }
      const response = await api.get(`/court/all/${userId}`);
      setCourts(response.data);
    } catch (err) {
      setError("Failed to fetch courts. Please try again later.");
    }
  };

  const deleteCourt = async (courtId: number) => {
    try {
      await api.delete(`/court/${courtId}`);
      fetchCourts();
    } catch (err) {
      setError("Failed to delete court. Please try again.");
    }
  };

  useEffect(() => {
    fetchCourts();
  }, []);

  return (
    <>
      {}
      <Navbar setIsLoginPage={() => false} />

      {}
      <div className="container-fluid mt-5">
        <div className="row">
          {}
          <div className="col-md-6">
            <h3>Courts</h3>
            {error && <div className="alert alert-danger">{error}</div>}

            <ul className="list-group">
              {courts.map((court) => (
                <li
                  key={court.id}
                  className="list-group-item d-flex justify-content-between align-items-center"
                >
                  <div>
                    <strong>Location:</strong> {court.location} <br />
                    <strong>Type:</strong> {court.courtType}
                  </div>
                  <span className="badge bg-primary">{court.courtType}</span>

                  {/* Delete Button */}
                  <button
                    onClick={() => deleteCourt(court.id)}
                    className="btn btn-danger ms-2"
                  >
                    Delete
                  </button>
                </li>
              ))}
            </ul>
          </div>

          {}
          <div className="col-md-6">
            <CreatePadelCourt onCourtCreated={fetchCourts} />
          </div>
        </div>
      </div>
    </>
  );
};

export default CourtsPage;